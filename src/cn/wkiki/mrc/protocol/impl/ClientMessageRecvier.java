package cn.wkiki.mrc.protocol.impl;

import java.util.List;
import java.util.logging.LogManager;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
@Component
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
	@Autowired
	public void setMessageResolver(ClientNetMessageResolver resolver)
	{
		this.resolver = resolver;
	}

	@Override
	public IClientNetRawDataReciver getRawDataReciver()
	{
		return rawDataReciver;
	}

	@Override
	@Autowired
	public void setRawDataReciver(IClientNetRawDataReciver reciver)
	{
		this.rawDataReciver = reciver;
	}

	public ReciveData reciveClientMessage(RemoteSocketInfo socketInfo)
	{
		byte[] reciveData = null;
		try
		{
			reciveData = rawDataReciver.reciveRowData(socketInfo);
		} catch (Throwable e)
		{
		}
		ReciveData recive = null;
		if (reciveData != null)
		{
			recive = new ReciveData(reciveData.length, reciveData);
		}
		return recive;
	}
}
