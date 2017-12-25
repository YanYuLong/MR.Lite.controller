package cn.wkiki.mrc.protocol;

import cn.wkiki.mrc.protocol.impl.ClientMessageRecvier;

/**
 * 与一个客户端连接的一次上下文
 * 
 * @author yulongy
 *
 */
public class ClientNetSessionContext
{
	// 数据发送器
	private IClientMessageReciver reciver;

	// 数据接收器
	private ClientMessageSender sender;

	// 客户端的远程连接
	private RemoteSocketInfo remoteSocketInfo;

	// Constructor
	public ClientNetSessionContext(ClientMessageRecvier reciver,ClientMessageSender sender)
	{
		this.reciver = reciver;
		this.sender = sender;
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
