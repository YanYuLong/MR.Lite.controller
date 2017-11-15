package cn.wkiki.mrc.protocol;

import java.io.InputStream;
import java.net.Socket;
import java.nio.charset.Charset;

import org.springframework.beans.factory.annotation.Autowired;

import com.sun.corba.se.impl.ior.ByteBuffer;

/**
 * 客户端网络原始数据流接收器(此处会对原始数据流的格式做约定)
 *
 * @author yulongy
 *
 */
public class ClientNetRawDataReciver
{
	/**
	 * 解析客户端发送来的数据的消息
	 */
	private ClientNetRecivedMessageResolver messageResolver;

	public ClientNetRecivedMessageResolver getMessageResolver()
	{
		return messageResolver;
	}

	@Autowired
	public void setMessageResolver(ClientNetRecivedMessageResolver messageResolver)
	{
		this.messageResolver = messageResolver;
	}

	/**
	 * 从远程客户端socket中读取此次发送来的原始数据信息
	 * 
	 * @return 返回从该socket接收到的字节信息，如果该socket上面没有可读的数据则返回null；
	 */
	public byte[] reciveRowData(RemoteSocketInfo remoteSocketInfo)
	{
		byte[] messageHeadBuff = new byte[10];
		byte[] result = null;
		Socket remoteSocket = remoteSocketInfo.getRemoteSocket();
		try
		{
			if (remoteSocket.getInputStream().available() > 0)
			{
				InputStream remoteInputStream = remoteSocket.getInputStream();
				remoteInputStream.read(messageHeadBuff);
			}
		}
		catch (Exception e)
		{
			// TODO: handle exception
		}

	}
}
