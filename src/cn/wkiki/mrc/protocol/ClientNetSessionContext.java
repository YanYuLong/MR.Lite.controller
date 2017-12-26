package cn.wkiki.mrc.protocol;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import cn.wkiki.mrc.protocol.impl.ClientMessageRecvier;

/**
 * 与一个客户端连接的一次上下文
 * 
 * @author yulongy
 *
 */
@Component("sessionContext")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ClientNetSessionContext
{
	// 数据发送器
	private IClientMessageReciver reciver;

	// 数据接收器
	private IClientMessageSender sender;

	// 客户端的远程连接
	private RemoteSocketInfo remoteSocketInfo;

	// Constructor
	@Autowired
	public ClientNetSessionContext(IClientMessageReciver reciver,IClientMessageSender sender)
	{
		this.reciver = reciver;
		this.sender = sender;
	}

	public RemoteSocketInfo getRemoteSocketInfo()
	{
		return remoteSocketInfo;
	}

	public void setRemoteSocketInfo(RemoteSocketInfo remoteSocketInfo)
	{
		this.remoteSocketInfo = remoteSocketInfo;
	}
	
	/**
	 * 触发可读逻辑
	 */
	public  void onSocketCanRead()
	{
	}
}
