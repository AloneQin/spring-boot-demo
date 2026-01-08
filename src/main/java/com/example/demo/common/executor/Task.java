package com.example.demo.common.executor;

import lombok.Data;

import java.util.Date;

/**
 * 任务实体类
 */
@Data
public class Task {

    /**
     * 任务ID
     */
    private final Integer taskId;

    /**
     * 任务状态
     */
    private Integer status = TaskStatusEnum.STATUS_PENDING.value;

    /**
     * 执行次数
     */
    private Integer executeCount = 0;

    /**
     * 最后一次错误信息
     */
    private String lastErrorMessage;

    /**
     * 任务创建时间
     */
    private Date createTime = new Date();

    /**
     * 最后一次执行时间
     */
    private Date lastExecuteTime;

    /**
     * 任务完成时间
     */
    private Date finishTime;

    public Task(int taskId) {
        this.taskId = taskId;
    }

    /**
     * 增加执行次数
     */
    public synchronized void incrementExecuteCount() {
        this.executeCount++;
    }
}
