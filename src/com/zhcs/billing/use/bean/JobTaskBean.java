package com.zhcs.billing.use.bean;

import java.sql.Timestamp;

/**
 * T_JOB_TASK ������Ӧ��Bean
 * 
 * @author yuqingchao
 */
public class JobTaskBean {

	// private long taskId = -1; // ���������
	// private String taskCode = ""; // ������� ���������������ʱ����Ϊqz���������
	// private String taskType = ""; // �������ͣ��������������ʱ����Ϊqz����ķ���
	// private String taskImplClass = ""; // �������
	// private String taskExpress = ""; // ����ִ�б��ʽ
	// private Timestamp stateDate = null; //
	// �������ʱ�䣬���������������У������Ҫʹ�����е������޸�������Ч����Ҫ������ֶε�ֵ���ô��ڵ�ǰʱ�䡣
	// private String state = ""; // ����״̬
	// private String parms = ""; // �����ʼ���������������е�ʱ�򣬿��Դ�JobDataMap�����л�ȡ���ֶε�ֵ��
	// private Timestamp createDate = null; // �������ڣ�û��ʲôʵ�����塣

	private long taskId; // ���������
	private String taskCode; // ������� ���������������ʱ����Ϊqz���������
	private String taskType; // �������ͣ��������������ʱ����Ϊqz����ķ���
	private String taskImplClass; // �������
	private String taskExpress; // ����ִ�б��ʽ
	private Timestamp stateDate; // �������ʱ�䣬���������������У������Ҫʹ�����е������޸�������Ч����Ҫ������ֶε�ֵ���ô��ڵ�ǰʱ�䡣
	private String state; // ����״̬
	private String parms; // �����ʼ���������������е�ʱ�򣬿��Դ�JobDataMap�����л�ȡ���ֶε�ֵ��
	private Timestamp createDate; // �������ڣ�û��ʲôʵ�����塣

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
