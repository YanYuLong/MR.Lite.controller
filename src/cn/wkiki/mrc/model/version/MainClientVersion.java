package cn.wkiki.mrc.model.version;

import java.util.UUID;

/**
 * 代表一个客户端主程的版本信息
 */
public class MainClientVersion extends ClientVersionInfo
{
	public MainClientVersion()
	{
		super();
	}

	// 客户端Client的机器唯一签名
	private UUID machineSignature;

	public UUID getMachineSignature()
	{
		return machineSignature;
	}

	public void setMachineSignature(UUID machineSignature)
	{
		this.machineSignature = machineSignature;
	}
}
