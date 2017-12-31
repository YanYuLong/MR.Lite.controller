package cn.wkiki.mrc.protocol;

import java.net.Socket;
import java.sql.Date;
import java.util.UUID;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.catalina.valves.PersistentValve;

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
	// socket是否正在被通讯模块处理
	private boolean isDealNow;

	private ReadWriteLock lock = new ReentrantReadWriteLock();

	/**
	 * 返回当前客户端 socket是否已经分配给通讯模块执行
	 * @return
	 */
	public boolean getIsDealNow()
	{
		try
		{
			lock.readLock().lock();
			return isDealNow;
		}
		finally
		{
			lock.readLock().unlock();
		}
	}
	/**
	 * 设置当前客户端socket是否分配给通讯模块执行的状态
	 * @param isDealNow
	 */
	public void setIsDealNow(boolean isDealNow)
	{
		try
		{
			lock.writeLock().lock();
			this.isDealNow = isDealNow;
		}
		finally
		{
			lock.writeLock().unlock();
		}
	}

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