package cn.wkiki.mrc.protocol;

import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;


import cn.wkiki.mrc.protocol.RemoteSocketInfo;

/**
 * 控制单元持有的socket连接池
 * 
 * @author yulongy
 *
 */
public class SocketPool
{
	Logger logger = LogManager.getLogger(getClass());
	// 远程客户端连接信息表
	HashMap<UUID, RemoteSocketInfo> socketTable = new HashMap<UUID, RemoteSocketInfo>();
	// 扫描socket状态信息的后台线程
	Thread scanThread;
	// 读写锁
	private ReadWriteLock lock = new ReentrantReadWriteLock();
	//通知listener可读事件
	private ClientListener listener;
	
	private long interval;
	@Autowired
	public void setListener(ClientListener listener)
	{
		this.listener = listener;
	}
	/**
	 * Constructor
	 */
	public SocketPool(long interval)
	{
		this.interval = interval;
		scanThread = new Thread(() -> {
			ScanMethod();
		});
		scanThread.setName("scanThread");
		scanThread.start();
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
				Set<UUID> keys=  socketTable.keySet();
				for(UUID k:keys)
				{
					RemoteSocketInfo v = socketTable.get(k);
					if(v.getIsDealNow())
					{
						continue;
					}
					Socket socket = v.getRemoteSocket();
					// 客户端主动关闭了连接
					if (socket.isInputShutdown())
					{
						UUID uuid = v.getUuid();
						logger.info("uuid为"+uuid+"的远程客户端主动关闭了连接");
						try{
							socket.shutdownOutput();
							socket.close();
							socketTable.remove(k);
						}
						catch (Exception e) {
							logger.error("被动关闭uuid为"+uuid+"的客户端失败 异常信息为"+e.getMessage());
						}
					}
					else
					{
						try
						{
							//报告可读
							if (socket.getInputStream().available() > 0)
							{
								this.listener.onSocketCanRead(v);
							}
						}
						catch (Exception e)
						{
							logger.error("判断客户端"+k+"socket是否可读时发生异常，异常信息为"+e.getMessage());
						}
					}
				}
			}
			finally
			{
				lock.writeLock().unlock();
			}
			try
			{
				Thread.sleep(interval);
			}
			catch (InterruptedException e) {
				logger.info("扫描线程接收到了InterruptedException 信息为:"+e.getMessage());
			}
			
		}
	}
}
