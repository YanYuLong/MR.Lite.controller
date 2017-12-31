package cn.wkiki.mrc.protocol;

import java.net.ServerSocket;
import java.net.Socket;

import javax.servlet.ServletContext;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import cn.wkiki.mrc.protocol.RemoteSocketInfo;
import cn.wkiki.util.ConfigInfo;
import cn.wkiki.util.ContextUtils;

/**
 * 监听客户端数据接口的Listener
 * 
 * @author yulongy
 *
 */
@Component("clientListener")
public class ClientListener
{
	// 本地监听的端口
	private int port;
	// 当前的监听状态
	private volatile boolean listenStatus;
	// 监听客户端连接的socket
	private ServerSocket socket = null;
	// 客户端socket连接池
	private SocketPool socketPool;
	//监听线程
	private  Thread thread;
	//线程池
	private java.util.concurrent.ThreadPoolExecutor threadPool;
	
	@Autowired
	public void setThreadPool(java.util.concurrent.ThreadPoolExecutor threadPoolExecutor)
	{
		this.threadPool = threadPoolExecutor;
	}
	public ClientListener(ConfigInfo configInfo)
	{
		setPort(configInfo.getListenPort()); 
	}

	Logger logger = LogManager.getLogger(getClass());
	public SocketPool getSocketPool()
	{
		return socketPool;
	}

	@Autowired()
	public void setSocketPool(SocketPool socketPool)
	{
		this.socketPool = socketPool;
	}

	public boolean isListenStatus()
	{
		synchronized (this)
		{
			return listenStatus;
		}
	}

	public void setListenStatus(boolean listenStatus)
	{
		synchronized (logger)
		{
			this.listenStatus = listenStatus;
		}
	}

	public int getPort()
	{
		return port;
	}

	public void setPort(int port)
	{
		if (port > 0 && port < 65535)
		{
			this.port = port;
		}
		else
		{
			throw new RuntimeException("端口指定错误");
		}

	}

	/**
	 * 开始监听
	 * 
	 * @throws Exception
	 */
	public void startListen() throws Exception
	{
		if (!listenStatus)
		{
			if (port > 0 && port < 65535)
			{
				logger.info("启动客户端监听：监听端口"+port);
				try
				{
					socket = new ServerSocket(port);
				}
				catch (Exception e) {
					LogManager.getLogger(getClass()).error("创建serverSocket 错误：异常信息为"+e.getMessage());
					return;
				}
				thread= new Thread(() -> {
					while (listenStatus)
					{
						try
						{
							Socket clientSocket = socket.accept();
							cn.wkiki.mrc.protocol.RemoteSocketInfo socketInfo = new RemoteSocketInfo();
							socketInfo.setRemoteSocket(clientSocket);
							socketPool.add(socketInfo);
						}
						catch (Exception e)
						{
							LogManager.getLogger(getClass()).error("向socketpool中添加客户端socket时发生异常，异常信息为："+e.getMessage());
						}
					}
				});
				thread.setName("listenThread");
				thread.start();
				listenStatus = true;
			}
		}
		else
		{
			throw new Exception("当前程序正在监听监听的端口为："+port);
		}
	}

	/**
	 * 停止监听客户端发送过来的网络数据
	 */
	public void stopListen()
	{
		setListenStatus(false);
	}
	/**
	 * 扫描线程获取到有socket可读的事件处理函数
	 * @param canReadSocketInfo
	 */
	public void onSocketCanRead(RemoteSocketInfo canReadSocketInfo)
	{
		ClientNetSessionContext sessionContext = ContextUtils.getRootContext().getBean(cn.wkiki.mrc.protocol.ClientNetSessionContext.class);
		sessionContext.setRemoteSocketInfo(canReadSocketInfo);
		threadPool.execute(new  Runnable() {
			public void run() {
				sessionContext.onSocketCanRead();
			}
		});
		canReadSocketInfo.setIsDealNow(true);
	}
}
