package com.volka.dynamicbatch.batch;

import com.volka.dynamicbatch.batch.command.CommandInvoker;
import com.volka.dynamicbatch.batch.dto.Task;
import com.volka.dynamicbatch.core.config.exception.BizException;
import com.volka.dynamicbatch.core.constant.DomainCode;
import com.volka.dynamicbatch.core.util.code.CodeUtil;
import com.volka.dynamicbatch.entity.RunHst;
import com.volka.dynamicbatch.entity.Schd;
import com.volka.dynamicbatch.entity.SchdJobCmndMapp;
import com.volka.dynamicbatch.entity.compositekey.SchdJobMappKey;
import com.volka.dynamicbatch.repository.JobCmndRepository;
import com.volka.dynamicbatch.repository.RunHstRepository;
import com.volka.dynamicbatch.repository.SchdJobCmndMappRepository;
import com.volka.dynamicbatch.repository.SchdRepository;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import org.springframework.util.ErrorHandler;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ScheduledFuture;

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


    private final CodeUtil codeUtil;

    private final JobCmndRepository jobCmndRepository;
    private final SchdJobCmndMappRepository mappRepository;
    private final SchdRepository schdRepository;

    private final RunHstRepository runHstRepository;

    @PostConstruct
    private void init() {
        try {
            List<Schd> schdList = Optional.of(schdRepository.findAll()).orElseThrow(() -> new BizException(""));
            List<SchdJobCmndMapp> mappList = Optional.of(mappRepository.findAll()).orElseThrow(() -> new BizException(""));

            for (Schd schd : schdList) {

                List<SchdJobCmndMapp> jobList = new ArrayList<>();

                Task.TaskBuilder taskBuilder = Task.builder()
                        .schdCd(schd.getSchdCd())
                        .schdCron(schd.getSchdCron())
                        .schdNm(schd.getSchdNm())
                        ;

                for (SchdJobCmndMapp mapp : mappList) {
                    if (mapp.getMappKey().getSchdCd() == schd.getSchdCd()) {
                        jobList.add(mapp);
                    }
                }

                jobList.sort(Comparator.comparingInt(SchdJobCmndMapp::getStep));

                TASK_LIST.add(taskBuilder.jobList(jobList).build());
            }

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

                if (taskScheduler != null && taskScheduler.isRunning()) throw new BizException("");

                taskScheduler = new ThreadPoolTaskScheduler();
                taskScheduler.setPoolSize(TASK_LIST.size());
                taskScheduler.setDaemon(true);
                taskScheduler.initialize();

                for (Task task : TASK_LIST) {
//                    taskScheduler.setErrorHandler(); TODO :: 배치 전용 핸들러 만들기. (retry 처리 여기서)
                    taskScheduler.setThreadNamePrefix(String.format("[BATCH TASK] :: (%d) %s", task.getSchdCd(), task.getSchdNm()));
                    taskScheduler.schedule(makeRunnable(task.getJobList()), makeTrigger(task.getSchdCron()));
//                    ScheduledFuture<?> result = taskScheduler.schedule(makeRunnable(task.getJobList()), makeTrigger(task.getSchdCron()));
                }
            }

        } catch (BizException e) {
            log.error("batch error :: {} :: {}", e.getErrCd(), e.getErrMsg());
        } catch (Exception e) {
            log.error("batch error :: {} :: {}", e.getLocalizedMessage(), e.toString());
        }
    }

    /**
     * 배치 종료
     */
    public void stopBatch() {
        try {
            if(!TASK_LIST.isEmpty()) TASK_LIST.clear();

            taskScheduler.shutdown();
            taskScheduler.destroy();

        } catch (BizException e) {
            log.error("batch error :: {} :: {}", e.getErrCd(), e.getErrMsg());
        } catch (Exception e) {
            log.error("batch error :: {} :: {}", e.getLocalizedMessage(), e.toString());
        }
    }

    private Runnable makeRunnable(List<SchdJobCmndMapp> jobList) throws InterruptedException, BizException, Exception {
        return () -> {
            for (SchdJobCmndMapp job : jobList) {
                log.info("[RUN TASK] :: {} : {} : {}", job.getMappKey().getSchdCd(), job.getMappKey().getJobCmnd(), job.getStep());
                startJob(job);
            }
        };
    }

    private Trigger makeTrigger(String cronTime) {
        return new CronTrigger(cronTime);
    }

    private void startJob(SchdJobCmndMapp job) {
        String commandNm = job.getMappKey().getJobCmnd();

        try {
            //TODO : 실행이력은 로그로만 쌓고, 실패 이력만 적재
            RunHst runHst = new RunHst();
            runHst.setRunHstId(codeUtil.makeSysId(DomainCode.RUN_HST));
            runHst.setJobCmnd(commandNm);
            runHstRepository.save(runHst);

            CommandInvoker.invoke(commandNm);

        } catch (BizException e) {
            log.error("startJob error :: {} :: {}", e.getErrCd(), e.getErrMsg());
        } catch (Exception e) {
            log.error("startJob error :: {} :: {}", e.getLocalizedMessage(), e.toString());
        }
    }
}
