package com.bench.xtask.xxljob.enums;

import com.bench.common.enums.EnumBase;

/**
 * @className ScheduleTypeEnum
 * @autor cold
 * @DATE 2021/6/30 1:35
 **/
public enum ScheduleTypeEnum implements EnumBase {

    /**
     * schedule by cron
     */
    CRON("继续CRON表达式"),

    /**
     * schedule by fixed rate (in seconds)
     */
    FIX_RATE("基于固定时间"),
    ;
    private String message;
    ScheduleTypeEnum(String message){
        this.message = message;

    }

    @Override
    public String message() {
        return message;
    }

    @Override
    public Number value() {
        return null;
    }
}
