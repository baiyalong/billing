package com.zhcs.billing.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

public class DbUtil {
	private static LoggerUtil log = LoggerUtil.getLogger(DbUtil.class);
	private static BoneCP connectionPoolEst = null;
	private static BoneCP connectionPoolBusiness = null;
	private static BoneCP connectionPoolCul = null;

	// 业务管理平台的数据库连接
	public synchronized static void initBuisness() {
		if (connectionPoolBusiness != null) {
			return;
		}
		try {
			DatabaseConfigBean bean = BusinessConfigBean();
			Class.forName(bean.getDriverClassName());
			BoneCPConfig config = new BoneCPConfig();
			config.setJdbcUrl(bean.getUrl());
			config.setUsername(bean.getUsername());
			config.setPassword(bean.getPassword());
			config.setMinConnectionsPerPartition(Integer.parseInt(bean
					.getMinConnectionsPerPartition()));
			config.setMaxConnectionsPerPartition(Integer.parseInt(bean
					.getMaxConnectionsPerPartition()));
			config.setPartitionCount(Integer.parseInt(bean.getPartitionCount()));
			config.setAcquireIncrement(Integer.parseInt(bean
					.getAcquireIncrement()));
			config.setAcquireRetryAttempts(Integer.parseInt(bean
					.getAcquireRetryDelay()));
			config.setCloseConnectionWatch(Boolean.parseBoolean(bean
					.getCloseConnectionWatch()));
			connectionPoolBusiness = new BoneCP(config);
			log.info("===KDSW BoneCP init success===");
		} catch (Exception e) {
			log.error("===BoneCP init failed===", e);
		}
	}

	// 计费子系统的数据库连接
	public synchronized static void initCalculate() {
		if (connectionPoolCul != null) {
			return;
		}
		try {
			DatabaseConfigBean bean = CalculateConfigBean();
			Class.forName(bean.getDriverClassName());
			BoneCPConfig config = new BoneCPConfig();
			config.setJdbcUrl(bean.getUrl());
			config.setUsername(bean.getUsername());
			config.setPassword(bean.getPassword());
			config.setMinConnectionsPerPartition(Integer.parseInt(bean
					.getMinConnectionsPerPartition()));
			config.setMaxConnectionsPerPartition(Integer.parseInt(bean
					.getMaxConnectionsPerPartition()));
			config.setPartitionCount(Integer.parseInt(bean.getPartitionCount()));
			config.setAcquireIncrement(Integer.parseInt(bean
					.getAcquireIncrement()));
			config.setAcquireRetryAttempts(Integer.parseInt(bean
					.getAcquireRetryDelay()));
			config.setCloseConnectionWatch(Boolean.parseBoolean(bean
					.getCloseConnectionWatch()));
			connectionPoolCul = new BoneCP(config);
			log.info("===KDSWBILLING BoneCP init success===");
		} catch (Exception e) {
			log.error("===BoneCP init failed===", e);
		}
	}

	// 计量的数据库连接
	public synchronized static void initEstimate() {
		if (connectionPoolEst != null) {
			return;
		}
		try {
			DatabaseConfigBean bean = EstimateConfigBean();
			Class.forName(bean.getDriverClassName());
			BoneCPConfig config = new BoneCPConfig();
			config.setJdbcUrl(bean.getUrl());
			config.setUsername(bean.getUsername());
			config.setPassword(bean.getPassword());
			config.setMinConnectionsPerPartition(Integer.parseInt(bean
					.getMinConnectionsPerPartition()));
			config.setMaxConnectionsPerPartition(Integer.parseInt(bean
					.getMaxConnectionsPerPartition()));
			config.setPartitionCount(Integer.parseInt(bean.getPartitionCount()));
			config.setAcquireIncrement(Integer.parseInt(bean
					.getAcquireIncrement()));
			config.setAcquireRetryAttempts(Integer.parseInt(bean
					.getAcquireRetryDelay()));
			config.setCloseConnectionWatch(Boolean.parseBoolean(bean
					.getCloseConnectionWatch()));
			connectionPoolEst = new BoneCP(config);
			log.info("===KDSWBILLING BoneCP init success===");
		} catch (Exception e) {
			log.error("===BoneCP init failed===", e);
		}
	}

	// 业务系统的数据库配置
	public static DatabaseConfigBean BusinessConfigBean() {
		DatabaseConfigBean bean = new DatabaseConfigBean();
		Properties props = new PropertieUtils().read("jdbc.properties");
		bean.setDriverClassName(props.getProperty("driverClassName"));
		bean.setUrl(props.getProperty("url"));
		bean.setUsername(props.getProperty("username"));
		bean.setPassword(props.getProperty("password"));
		bean.setPartitionCount(props.getProperty("partitionCount"));
		bean.setMinConnectionsPerPartition(props
				.getProperty("minConnectionsPerPartition"));
		bean.setMaxConnectionsPerPartition(props
				.getProperty("maxConnectionsPerPartition"));
		bean.setAcquireIncrement(props.getProperty("acquireIncrement"));
		bean.setAcquireRetryDelay(props.getProperty("acquireRetryDelay"));
		bean.setCloseConnectionWatch(props.getProperty("closeConnectionWatch"));
		return bean;
	}

	/**
	 * 计费系统的数据库配置
	 * 
	 * @return
	 */
	public static DatabaseConfigBean CalculateConfigBean() {
		DatabaseConfigBean bean = new DatabaseConfigBean();
		Properties props = new PropertieUtils().read("jdbc.properties");
		bean.setDriverClassName(props.getProperty("driverClassName1"));
		bean.setUrl(props.getProperty("url1"));
		bean.setUsername(props.getProperty("username1"));
		bean.setPassword(props.getProperty("password1"));
		bean.setPartitionCount(props.getProperty("partitionCount1"));
		bean.setMinConnectionsPerPartition(props
				.getProperty("minConnectionsPerPartition1"));
		bean.setMaxConnectionsPerPartition(props
				.getProperty("maxConnectionsPerPartition1"));
		bean.setAcquireIncrement(props.getProperty("acquireIncrement1"));
		bean.setAcquireRetryDelay(props.getProperty("acquireRetryDelay1"));
		bean.setCloseConnectionWatch(props.getProperty("closeConnectionWatch1"));
		return bean;
	}

	/**
	 * 计量系统的数据库配置
	 * 
	 * @return
	 */
	public static DatabaseConfigBean EstimateConfigBean() {
		DatabaseConfigBean bean = new DatabaseConfigBean();
		Properties props = new PropertieUtils().read("jdbc.properties");
		bean.setDriverClassName(props.getProperty("driverClassName2"));
		bean.setUrl(props.getProperty("url2"));
		bean.setUsername(props.getProperty("username2"));
		bean.setPassword(props.getProperty("password2"));
		bean.setPartitionCount(props.getProperty("partitionCount2"));
		bean.setMinConnectionsPerPartition(props
				.getProperty("minConnectionsPerPartition2"));
		bean.setMaxConnectionsPerPartition(props
				.getProperty("maxConnectionsPerPartition2"));
		bean.setAcquireIncrement(props.getProperty("acquireIncrement2"));
		bean.setAcquireRetryDelay(props.getProperty("acquireRetryDelay2"));
		bean.setCloseConnectionWatch(props.getProperty("closeConnectionWatch2"));
		return bean;
	}

	public static Connection getConnection() {
		if (connectionPoolBusiness == null) {
			initBuisness();
		}
		Connection conn = null;
		try {
			if (connectionPoolBusiness != null) {
				conn = connectionPoolBusiness.getConnection();
			}
		} catch (SQLException e) {
			// connectionPool.close();
			log.error("===get database connection failed===", e);
		}
		return conn;
	}

	public static Connection getCalculateConnection() {
		if (connectionPoolCul == null) {
			DbUtil.initCalculate();
		}
		Connection conn = null;
		try {
			if (connectionPoolCul != null) {
				conn = connectionPoolCul.getConnection();
			}
		} catch (SQLException e) {
			// connectionPoolBilling.close();
			log.error("===get database connection failed===", e);
		}
		return conn;
	}

	public static Connection getEstimateConnection() {
		if (connectionPoolEst == null) {
			DbUtil.initEstimate();
		}
		Connection conn = null;
		try {
			if (connectionPoolEst != null) {
				conn = connectionPoolEst.getConnection();
			}
		} catch (SQLException e) {
			// connectionPoolBilling.close();
			log.error("===get database connection failed===", e);
		}
		return conn;
	}
}
