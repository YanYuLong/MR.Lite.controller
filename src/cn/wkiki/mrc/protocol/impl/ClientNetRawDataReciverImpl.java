package cn.wkiki.mrc.protocol.impl;

import java.io.InputStream;
import java.net.Socket;
import org.springframework.beans.factory.annotation.Autowired;

import cn.wkiki.mrc.protocol.ClientNetMessageResolver;
import cn.wkiki.mrc.protocol.IClientNetRawDataReciver;
import cn.wkiki.mrc.protocol.RemoteSocketInfo;


/**
 * 客户端网络原始数据流接收器(此处会对原始数据流的格式做约定)
 *
 * @author yulongy
 *
 */
public class ClientNetRawDataReciverImpl implements IClientNetRawDataReciver
{
	/**
	 * 从远程客户端socket中读取此次发送来的原始数据信息
	 * 
	 * @return 返回从该socket接收到的字节信息，如果该socket上面没有可读的数据则返回null；
	 */
	@Override
	public byte[] reciveRowData(RemoteSocketInfo remoteSocketInfo) throws Throwable
	{
		byte[] messageHeadBuff = new byte[10];
		byte[] result = null;
		Socket remoteSocket = remoteSocketInfo.getRemoteSocket();
		try
		{
			if (remoteSocket.getInputStream().available() > 0)
			{
				InputStream remoteInputStream = remoteSocket.getInputStream();
				int realRecivedCount = remoteInputStream.read(messageHeadBuff);
				// 接收报文头正常
				if (realRecivedCount == messageHeadBuff.length)
				{
					// 分隔符正常
					if (messageHeadBuff[9] == 0x0A && messageHeadBuff[8] == 0x0D)
					{
						String contentLengthStr = new String(messageHeadBuff, 0, 8);
						try
						{
							int contentLength = Integer.parseInt(contentLengthStr);
							byte[] contentBuff = new byte[contentLength];
							// 阻塞IO
							int realContentReciveCount = remoteInputStream.read(contentBuff);
							// 所有数据接收成功
							if (realContentReciveCount == contentBuff.length)
							{
								result = contentBuff;
							}
							else
							{
								throw new RuntimeException("接收报文内容数据时出现报文长度校验失败异常：客户端报告长度:" + contentLengthStr
										+ "实际接收到的长度:" + realContentReciveCount);
							}
						}
						catch (NumberFormatException e)
						{
							throw new RuntimeException("接受客户端报文时发生异常，异常类型：报文负载长度字符转换类型失败：失败字符：" + contentLengthStr
									+ remoteSocketInfo.toString());
						}
					}
					else
					{
						throw new RuntimeException("接收到客户端报文时发生异常，异常类型：报文负载分割符异常！" + remoteSocket.toString());
					}
				}
				else
				{
					throw new RuntimeException("接收客户端报文时发生报文读取错误，错误类型：报文头字段读取长度非法！" + remoteSocketInfo.toString());
				}
			}
		}
		catch (Exception e)
		{
			// TODO: handle exception
		}
		return result;
	}

	
	
}
