package cn.wkiki.mrc.protocol;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.wkiki.mrc.protocol.ClientNetMessageResolver;
import cn.wkiki.mrc.protocol.RemoteSocketInfo;
import jdk.nashorn.internal.runtime.ECMAErrors;

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
				socket = new ServerSocket(port);
				Thread thread = new Thread(() -> {
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
