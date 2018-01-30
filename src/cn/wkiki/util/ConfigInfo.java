package cn.wkiki.util;

/**
 * 系统运行数据配置类
 * @author yulongy
 *
 */
public class ConfigInfo
{
	//扫描可用socket时间间隔
	private long scanInterval;
	//与客户端通讯的监听端口
	private int listenPort;
	//线程池核心池大小
	private int corePoolSize;
	//线程池最大线程大小
	private int maxPoolSize;
	//销毁线程的keepAlive时间
	private int keepAliveTime;
	//任务等待队列大小
	private int workQueueSize;
	//数据库连接字符串
	private String sqlConnectionStr;
	//数据库用户名
	private String sqlUserName;
	//数据库用户密码
	private String sqlUserPwd;
	
	public String getSqlConnectionStr()
	{
		return sqlConnectionStr;
	}

	public void setSqlConnectionStr(String sqlConnectionStr)
	{
		this.sqlConnectionStr = sqlConnectionStr;
	}

	public String getSqlUserName()
	{
		return sqlUserName;
	}

	public void setSqlUserName(String sqlUserName)
	{
		this.sqlUserName = sqlUserName;
	}

	public String getSqlUserPwd()
	{
		return sqlUserPwd;
	}

	public void setSqlUserPwd(String sqlUserPwd)
	{
		this.sqlUserPwd = sqlUserPwd;
	}

	public int getCorePoolSize() {
		return corePoolSize;
	}

	public void setCorePoolSize(int corePoolSize) {
		this.corePoolSize = corePoolSize;
	}

	public int getMaxPoolSize() {
		return maxPoolSize;
	}

	public void setMaxPoolSize(int maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

	public int getKeepAliveTime() {
		return keepAliveTime;
	}

	public void setKeepAliveTime(int keepAliveTime) {
		this.keepAliveTime = keepAliveTime;
	}

	public int getWorkQueueSize() {
		return workQueueSize;
	}

	public void setWorkQueueSize(int workQueueSize) {
		this.workQueueSize = workQueueSize;
	}

	public int getListenPort()
	{
		return listenPort;
	}

	public void setListenPort(int listenPort)
	{
		this.listenPort = listenPort;
	}

	public long getScanInterval()
	{
		return scanInterval;
	}

	public void setScanInterval(long scanInterval)
	{
		this.scanInterval = scanInterval;
	}
}
