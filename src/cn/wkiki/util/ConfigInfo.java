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
