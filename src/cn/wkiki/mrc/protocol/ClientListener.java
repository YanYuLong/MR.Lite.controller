package cn.wkiki.mrc.protocol;

import java.net.ServerSocket;
import java.net.Socket;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import cn.wkiki.mrc.protocol.ClientNetMessageResolver;
import cn.wkiki.mrc.protocol.RemoteSocketInfo;

/**
 * 监听客户端数据接口的Listener
 * 
 * @author yulongy
 *
 */
@Component
public class ClientListener
{
	// 本地监听的端口
	private int port;
	// 客户端网络消息的的解析器
	private ClientNetMessageResolver resolver;
	// 当前的监听状态
	private boolean listenStatus;
	// 监听客户端连接的socket
	private ServerSocket socket = null;
	// 客户端socket连接池
	private SocketPool socketPool;
	//监听线程
	private  Thread thread ;

	Logger logger = LogManager.getLogger(getClass());
	public SocketPool getSocketPool()
	{
		return socketPool;
	}

	@Autowired
	public void setSocketPool(SocketPool socketPool)
	{
		this.socketPool = socketPool;
	}

	public boolean isListenStatus()
	{
		return listenStatus;
	}

	public void setListenStatus(boolean listenStatus)
	{
		this.listenStatus = listenStatus;
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

	public ClientNetMessageResolver getResolver()
	{
		return resolver;
	}

	public void setResolver(ClientNetMessageResolver resolver)
	{
		this.resolver = resolver;
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
				socket = new ServerSocket(port);
				thread= new Thread(() -> {
					while (true)
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

						}
					}
				});
				listenStatus = true;
			}
		}
	}
}
