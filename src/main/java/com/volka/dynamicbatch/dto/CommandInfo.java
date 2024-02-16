package com.volka.dynamicbatch.dto;

import com.volka.dynamicbatch.batch.AbstactCommand;
import com.volka.dynamicbatch.entity.SchdJobCommandMapp;
import lombok.Getter;

/**
 * 커맨드 정보
 *
 * @author volka
 */
@Getter
public class CommandInfo {

    private final int schdCd;
    private final String jobCommand;
    private final int step;
    private final int retry;
    private final AbstactCommand command;

    private CommandInfo(SchdJobCommandMapp mapp, AbstactCommand command) {
        this.schdCd = mapp.getSchdCd();
        this.jobCommand = mapp.getJobCommand();
        this.step = mapp.getStep();
        this.retry = mapp.getRetry();
        this.command = command;
    }

    public static CommandInfo create(SchdJobCommandMapp mapp, AbstactCommand command) {
        return new CommandInfo(mapp, command);
    }
}
