package com.volka.dynamicbatch.batch;

import com.volka.dynamicbatch.batch.dto.Task;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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

}
