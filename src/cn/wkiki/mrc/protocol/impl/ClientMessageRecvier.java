package cn.wkiki.mrc.protocol.impl;

import cn.wkiki.mrc.protocol.ClientNetMessageResolver;
import cn.wkiki.mrc.protocol.IClientMessageReciver;
import cn.wkiki.mrc.protocol.IClientNetRawDataReciver;
import cn.wkiki.mrc.protocol.RemoteSocketInfo;

/**
 * 与客户端网络交互的接收器
 * 
 * @author yulongy
 *
 */
public class ClientMessageRecvier implements IClientMessageReciver
{
	private ClientNetMessageResolver resolver;
	private IClientNetRawDataReciver rawDataReciver;
	@Override
	public ClientNetMessageResolver getMessageResolver()
	{
		return resolver;
	}

	@Override
	public void setMessageResolver(ClientNetMessageResolver resolver)
	{
		this.resolver = resolver;
	}

	@Override
	public IClientNetRawDataReciver getRawDataReciver()
	{
		return  rawDataReciver;
	}

	@Override
	public void setRawDataReciver(IClientNetRawDataReciver reciver)
	{
		this.rawDataReciver = reciver;
	}
	
	
	public  ReciveData reciveClientMessage(RemoteSocketInfo socketInfo)
	{
		byte[] reciveData =null;
		try
		{
			reciveData= rawDataReciver.reciveRowData(socketInfo);
		}
		catch (Throwable e) {
		}
		ReciveData recive = new ReciveData(reciveData.length, reciveData); 
		return recive;
	}
}
