package com.volka.dynamicbatch.batch;

import com.volka.dynamicbatch.core.context.ApplicationContextProvider;
import com.volka.dynamicbatch.core.config.exception.BizException;
import com.volka.dynamicbatch.core.constant.DomainCode;
import com.volka.dynamicbatch.core.util.code.CodeUtil;
import com.volka.dynamicbatch.dto.CommandInfo;
import com.volka.dynamicbatch.dto.Task;
import com.volka.dynamicbatch.entity.JobRunHst;
import com.volka.dynamicbatch.entity.Schd;
import com.volka.dynamicbatch.entity.SchdJobCommandMapp;
import com.volka.dynamicbatch.mapper.job.JobRunHstMapper;
import com.volka.dynamicbatch.mapper.job.SchdJobCommandMappMapper;
import com.volka.dynamicbatch.mapper.job.SchdMapper;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * @author : volka <volka5091@gmail.com>
 * description    : 동적 배치 관리 클래스
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class DynamicBatch {

    private static final List<Task> TASK_LIST = new ArrayList<>();
    private static ThreadPoolTaskScheduler taskScheduler;

    private final ApplicationContextProvider applicationContextProvider;
    //    private final BatchErrorHandler batchErrorHandler;
    private final CodeUtil codeUtil;

    private final SchdMapper schdMapper;
    private final SchdJobCommandMappMapper schdJobCommandMappMapper;
    private final JobRunHstMapper jobRunHstMapper;


    @PostConstruct
    private void init() {
        try {
            ApplicationContext appContext = applicationContextProvider.getAppContext();

            List<Schd> taskList = Optional.ofNullable(schdMapper.selectAll()).orElseThrow(() -> new BizException("BTH0001"));
            List<SchdJobCommandMapp> mappList = Optional.ofNullable(schdJobCommandMappMapper.selectAll()).orElseThrow(() -> new BizException("BTH0001"));

            for (Schd schd : taskList) {
                List<CommandInfo> jobList = new ArrayList<>();

                Task.TaskBuilder taskBuilder = Task.builder()
                        .schdCd(schd.getSchdCd())
                        .schdCron(schd.getSchdCron())
                        .schdNm(schd.getSchdNm())
                        ;

                for (SchdJobCommandMapp mapp : mappList) {
                    if (mapp.getSchdCd() == schd.getSchdCd()) {
                        AbstactCommand command = appContext.getBean(mapp.getJobCommand(), AbstactCommand.class);
                        command.maxRetry = mapp.getRetry();
                        jobList.add(CommandInfo.create(mapp, command));
                    }
                }

                jobList.sort(Comparator.comparingInt(CommandInfo::getStep));

                TASK_LIST.add(taskBuilder.jobList(jobList).build());
            }

            startBatch();

        } catch (BizException e) {
            log.error("dynamic batch init failed :: {} :: {}", e.getErrCd(), e.getErrMsg());
        } catch (Exception e) {
            log.error("dynamic batch init failed :: {} :: {}", e.getLocalizedMessage(), e.toString());
        }
    }

    @PreDestroy
    private void batchDestroy() {
        stopBatch();
    }

    /**
     * job 변경 후 초기화 :: 외부 호출
     */
    public void batchInit() {
        batchDestroy();
        init();
    }

    /**
     * 배치 시작
     */
    private void startBatch() {
        try {
            if (TASK_LIST.isEmpty()) {
                log.info("=== no available job ====");
            } else {
                log.info("=== batch start ===");
                if (taskScheduler != null) throw new BizException("BTH00001");

                taskScheduler = new ThreadPoolTaskScheduler();
                taskScheduler.setPoolSize(TASK_LIST.size());
                taskScheduler.setDaemon(true);
//                taskScheduler.setErrorHandler(batchErrorHandler);
                taskScheduler.initialize();

                for (Task task : TASK_LIST) {
                    taskScheduler.setThreadNamePrefix(String.format("[BATCH TASK] :: (%d) %s", task.getSchdCd(), task.getSchdNm()));
                    taskScheduler.schedule(createRunnable(task.getJobList()), createTrigger(task.getSchdCron()));
//                    ScheduledFuture<?> result = taskScheduler.schedule(createRunnable(task.getJobList()), createTrigger(task.getSchdCron()));
                }
            }

        } catch (BizException e) {
            log.error("startBatch error :: {} :: {}", e.getErrCd(), e.getErrMsg());
        } catch (Exception e) {
            log.error("startBatch error :: {} :: {}", e.getLocalizedMessage(), e.toString());
        }
    }

    /**
     * 배치 종료
     */
    public void stopBatch() {
        try {
            if(!TASK_LIST.isEmpty()) TASK_LIST.clear();

            if(taskScheduler != null) {
                taskScheduler.shutdown();
                taskScheduler.destroy();
            }

        } catch (BizException e) {
            log.error("batch error :: {} :: {}", e.getErrCd(), e.getErrMsg());
        } catch (Exception e) {
            log.error("batch error :: {} :: {}", e.getLocalizedMessage(), e.toString());
        }
    }

    private Runnable createRunnable(List<CommandInfo> jobList) throws InterruptedException, BizException, Exception {
        return () -> {
            for (CommandInfo commandInfo : jobList) {
                log.info("[RUN TASK] :: {} : {} : {}", commandInfo.getSchdCd(), commandInfo.getJobCommand(), commandInfo.getStep());
                startJob(commandInfo);
            }
        };
    }

    private Trigger createTrigger(String cronTime) {
        return new CronTrigger(cronTime);
    }


    private void startJob(CommandInfo job) {
        JobRunHst jobRunHst = new JobRunHst();

        try {
            jobRunHst.setJobRunHstId(codeUtil.makeSysId(DomainCode.RUN_HST));
            jobRunHst.setJobCommand(job.getJobCommand());
            jobRunHstMapper.insert(jobRunHst);

            CommandInvoker.invoke(job.getCommand());

            jobRunHst.setSuccYn("Y");
            jobRunHstMapper.update(jobRunHst);

        } catch (BizException e) {
            log.error("startJob ERROR :: {} :: {}", e.getErrCd(), e.getErrMsg());
            updateRunHst(jobRunHst, e);
        } catch (Exception e) {
            log.error("startJob ERROR :: {} :: {}", e.getLocalizedMessage(), e.toString());
            updateRunHst(jobRunHst, e);
        }
    }

    private void updateRunHst(JobRunHst jobRunHst, Exception exception) {
        try {

            jobRunHst.setSuccYn("N");
            if (exception instanceof BizException) {
                jobRunHst.setErrCd(((BizException) exception).getErrCd());
            } else {
                jobRunHst.setErrCd("BTE00000"); //배치실패
            }
            jobRunHst.setErrMsg(exception.getLocalizedMessage());
        } catch (Exception e) {
            log.error("TASK RUN HST UPDATE FAILED :: {} : {} : {}", jobRunHst.getJobCommand(), e.getLocalizedMessage(), e.toString());
        }
    }
}
