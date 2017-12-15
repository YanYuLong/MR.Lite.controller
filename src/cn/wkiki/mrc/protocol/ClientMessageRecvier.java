package cn.wkiki.mrc.protocol;

/**
 * 与客户端网络交互的接收器
 * 
 * @author yulongy
 *
 */
public class ClientMessageRecvier
{
	private ClientNetRawDataReciver reciver;

	private ClientNetRecivedMessageResolver resolver;

	public ClientMessageRecvier(ClientNetRawDataReciver rawDataReciver,
			ClientNetRecivedMessageResolver recivedMessageResolver)
	{
		this.reciver = rawDataReciver;
		this.resolver = recivedMessageResolver;
	}
	
	public  ReciveData reciveClientMessage(RemoteSocketInfo socketInfo)
	{
		byte[] reciveData =null;
		try
		{
			reciveData= reciver.reciveRowData(socketInfo);
		}
		catch (Throwable e) {
		}
		ReciveData recive = new ReciveData(reciveData.length, reciveData); 
		return recive;
	}

	/**
	 * 表示接收到的一个客户端发送过来的信息数据
	 * @author yulongy
	 *
	 */
	public class ReciveData
	{
		private int dataLength;
		private byte[] dataContent;
		public ReciveData(int dataLenth,byte[] dataContent)
		{
			this.dataLength = dataLenth;
			this.dataContent = dataContent;
		}
		/**
		 * 获取该客户端消息的有效信息长度
		 * @return
		 */
		public int getDataLength()
		{
			return dataLength;
		}
		/**
		 * 获取该客户端消息的负载字节
		 * @return
		 */
		public byte[] getDataContent()
		{
			return dataContent;
		}
	}
}
