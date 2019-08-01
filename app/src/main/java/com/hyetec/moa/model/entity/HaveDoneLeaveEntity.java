package com.hyetec.moa.model.entity;

public class HaveDoneLeaveEntity {

    /**
     * assignee : 叶苏缘
     * bussinesId : 150
     * bussinesName : 请假申请-【叶苏缘】
     * id : 1555
     * processKey : LeaveApply
     * processName : 请假流程
     * startTime : 2019-07-30 13:52:13
     * taskKey :
     * taskName : 发起申请
     */

    private String assignee;
    private int bussinesId;
    private String bussinesName;
    private int id;
    private String processKey;
    private String processName;
    private String startTime;
    private String taskKey;
    private String taskName;

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public int getBussinesId() {
        return bussinesId;
    }

    public void setBussinesId(int bussinesId) {
        this.bussinesId = bussinesId;
    }

    public String getBussinesName() {
        return bussinesName;
    }

    public void setBussinesName(String bussinesName) {
        this.bussinesName = bussinesName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProcessKey() {
        return processKey;
    }

    public void setProcessKey(String processKey) {
        this.processKey = processKey;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getTaskKey() {
        return taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
}
