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

	public long getScanInterval()
	{
		return scanInterval;
	}

	public void setScanInterval(long scanInterval)
	{
		this.scanInterval = scanInterval;
	}
}
