package cn.wkiki.mrc.protocol;

/**
 * 与一个客户端连接的一次上下文
 * 
 * @author yulongy
 *
 */
public class ClientNetSessionContext
{
	// 数据发送器
	private ClientNetRawDataReciver reciver;

	// 数据接收器
	private ClientNetRawDataSender sender;

	// 客户端的远程连接
	private RemoteSocketInfo remoteSocketInfo;

	// Constructor
	public ClientNetSessionContext()
	{
	}

	/**
	 * 远程客户端socket可读事件处理器
	 * 
	 * @param remoteSocketInfo
	 */
	public void onSocketReadable(RemoteSocketInfo remoteSocketInfo)
	{
		this.remoteSocketInfo = remoteSocketInfo;
	}
}
