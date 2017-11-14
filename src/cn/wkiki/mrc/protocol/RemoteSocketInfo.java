package cn.wkiki.mrc.protocol;

import java.net.Socket;
import java.rmi.Remote;
import java.sql.Date;
import java.util.UUID;

/**
 * 远程客户端的连接信息
 * 
 * @author yulongy
 *
 */
public class RemoteSocketInfo
{
	// 远程客户端的socket引用
	private Socket remoteSocket;
	// 连接开始的时间
	private Date connectedTime;
	// 标示一个远程客户端的一次连接的uuid
	private UUID uuid;

	public RemoteSocketInfo()
	{
		uuid = UUID.randomUUID();
	}

	public UUID getUuid()
	{
		return uuid;
	}

	public void setUuid(UUID uuid)
	{
		this.uuid = uuid;
	}

	public Socket getRemoteSocket()
	{
		return remoteSocket;
	}

	public void setRemoteSocket(Socket remoteSocket)
	{
		this.remoteSocket = remoteSocket;
	}

	public Date getConnectedTime()
	{
		return connectedTime;
	}

	public void setConnectedTime(Date connectedTime)
	{
		this.connectedTime = connectedTime;
	}
}