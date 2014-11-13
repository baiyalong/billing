package com.zhcs.billing.util;

public class DatabaseConfigBean {
	private String driverClassName;
	private String url;
	private String username;
	private String password;
	private String partitionCount;
	private String minConnectionsPerPartition;
	private String maxConnectionsPerPartition;
	private String acquireIncrement;
	private String acquireRetryDelay;
	private String closeConnectionWatch;
	public String getAcquireRetryDelay() {
		return acquireRetryDelay;
	}
	public void setAcquireRetryDelay(String acquireRetryDelay) {
		this.acquireRetryDelay = acquireRetryDelay;
	}
	public String getCloseConnectionWatch() {
		return closeConnectionWatch;
	}
	public void setCloseConnectionWatch(String closeConnectionWatch) {
		this.closeConnectionWatch = closeConnectionWatch;
	}
	public String getDriverClassName() {
		return driverClassName;
	}
	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPartitionCount() {
		return partitionCount;
	}
	public void setPartitionCount(String partitionCount) {
		this.partitionCount = partitionCount;
	}
	public String getMinConnectionsPerPartition() {
		return minConnectionsPerPartition;
	}
	public void setMinConnectionsPerPartition(String minConnectionsPerPartition) {
		this.minConnectionsPerPartition = minConnectionsPerPartition;
	}
	public String getMaxConnectionsPerPartition() {
		return maxConnectionsPerPartition;
	}
	public void setMaxConnectionsPerPartition(String maxConnectionsPerPartition) {
		this.maxConnectionsPerPartition = maxConnectionsPerPartition;
	}
	public String getAcquireIncrement() {
		return acquireIncrement;
	}
	public void setAcquireIncrement(String acquireIncrement) {
		this.acquireIncrement = acquireIncrement;
	}
}
