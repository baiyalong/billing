package com.zhcs.billing.use.bean;

import java.sql.Timestamp;

/**
 * T_JOB_TASK 任务表对应的Bean
 * 
 * @author yuqingchao
 */
public class JobTaskBean {

	// private long taskId = -1; // 任务的主键
	// private String taskCode = ""; // 任务编码 ，这里启动任务的时候，作为qz任务的名称
	// private String taskType = ""; // 任务类型，这里启动任务的时候，作为qz任务的分组
	// private String taskImplClass = ""; // 任务的类
	// private String taskExpress = ""; // 任务执行表达式
	// private Timestamp stateDate = null; //
	// 任务更新时间，这里在任务运行中，如果需要使运行中的任务修改立马生效，需要把这个字段的值设置大于当前时间。
	// private String state = ""; // 任务状态
	// private String parms = ""; // 任务初始化参数，任务运行的时候，可以从JobDataMap对象中获取该字段的值。
	// private Timestamp createDate = null; // 创建日期，没有什么实际意义。

	private long taskId; // 任务的主键
	private String taskCode; // 任务编码 ，这里启动任务的时候，作为qz任务的名称
	private String taskType; // 任务类型，这里启动任务的时候，作为qz任务的分组
	private String taskImplClass; // 任务的类
	private String taskExpress; // 任务执行表达式
	private Timestamp stateDate; // 任务更新时间，这里在任务运行中，如果需要使运行中的任务修改立马生效，需要把这个字段的值设置大于当前时间。
	private String state; // 任务状态
	private String parms; // 任务初始化参数，任务运行的时候，可以从JobDataMap对象中获取该字段的值。
	private Timestamp createDate; // 创建日期，没有什么实际意义。

	public Timestamp getCreateDate() {
		return createDate;
	}

	public String getParms() {
		return parms;
	}

	public String getState() {
		return state;
	}

	public Timestamp getStateDate() {
		return stateDate;
	}

	public String getTaskCode() {
		return taskCode;
	}

	public String getTaskExpress() {
		return taskExpress;
	}

	public long getTaskId() {
		return taskId;
	}

	public String getTaskImplClass() {
		return taskImplClass;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public void setParms(String parms) {
		this.parms = parms;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setStateDate(Timestamp stateDate) {
		this.stateDate = stateDate;
	}

	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}

	public void setTaskExpress(String taskExpress) {
		this.taskExpress = taskExpress;
	}

	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}

	public void setTaskImplClass(String taskImplClass) {
		this.taskImplClass = taskImplClass;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
}
