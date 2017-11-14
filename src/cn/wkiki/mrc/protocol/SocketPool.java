package cn.wkiki.mrc.protocol;

import java.net.InetAddress;
import java.net.Socket;
import java.rmi.Remote;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.springframework.stereotype.Component;

import com.sun.org.apache.xml.internal.resolver.helpers.PublicId;

import cn.wkiki.mrc.protocol.RemoteSocketInfo;
import sun.management.counter.Variability;

/**
 * 控制单元持有的socket连接池
 * 
 * @author yulongy
 *
 */
@Component
public class SocketPool
{
	// 远程客户端连接信息表
	HashMap<UUID, RemoteSocketInfo> socketTable = new HashMap<UUID, RemoteSocketInfo>();

	// 扫描socket状态信息的后台线程
	Thread scanThread;

	// 读写锁
	private ReadWriteLock lock = new ReentrantReadWriteLock();

	/**
	 * Constructor
	 */
	public SocketPool()
	{
		scanThread = new Thread(() -> {
			ScanMethod();
		});
	}

	/**
	 * 将新打开的客户端远程连接假如到维护的远程连接池中
	 * 
	 * @param remoteSocketInfo
	 *            要新添加的socketInfo
	 */
	public void add(RemoteSocketInfo remoteSocketInfo)
	{
		try
		{
			lock.readLock().lock();
			socketTable.put(remoteSocketInfo.getUuid(), remoteSocketInfo);
		}
		finally
		{
			lock.readLock().unlock();
		}
	}

	/**
	 * 将指定的远程客户端的连接信息从连接池中移除
	 * 
	 * @param remoteSocketInfo
	 *            要从池中移除的socketInfo
	 */
	public void remove(RemoteSocketInfo remoteSocketInfo)
	{
		try
		{
			lock.writeLock().lock();
			socketTable.remove(remoteSocketInfo.getUuid());
		}
		finally
		{
			lock.writeLock().unlock();
		}
	}

	/**
	 * 扫描的具体实现逻辑方法
	 */
	public void ScanMethod()
	{
		while (true)
		{
			try
			{
				lock.writeLock().lock();
				socketTable.forEach((k,v)->{
					Socket socket = v.getRemoteSocket();
				});
			}
			finally {
				lock.writeLock().unlock();
			}
			
		}
	}
}
