package cn.wkiki.mrc.model.version;

import java.sql.Date;
import java.util.UUID;

/**
 * 客户端组件的版本信息
 * 
 * @author yulongy
 *
 */
public abstract class ClientVersionInfo
{
	// 客户端版本的唯一ID
	private UUID versionSignature;
	// 客户端版本的字符串版本号
	private String version;
	// 客户端的升级时间
	private Date updateTime;

	public UUID getVersionSignature()
	{
		return versionSignature;
	}

	public void setVersionSignature(UUID versionSignature)
	{
		this.versionSignature = versionSignature;
	}

	public String getVersion()
	{
		return version;
	}

	public void setVersion(String version)
	{
		this.version = version;
	}

	public Date getUpdateTime()
	{
		return updateTime;
	}

	public void setUpdateTime(Date updateTime)
	{
		this.updateTime = updateTime;
	}

}
