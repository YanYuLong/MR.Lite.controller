package cn.wkiki.mrc.protocol;


public interface IClientMessageReciver
{
	/**
	 * 	获取消息解释器
	 * @return
	 */
	public ClientNetMessageResolver getMessageResolver();
	
	/**
	 * 设置消息解释器
	 * @param resolver
	 */
	public void setMessageResolver(ClientNetMessageResolver resolver);
	
	/**
	 * 获取数据流接收器
	 * @return
	 */
	public IClientNetRawDataReciver getRawDataReciver();
	
	/**
	 * 设置数据流接收器
	 * 
	 */
	public void setRawDataReciver(IClientNetRawDataReciver reciver);
	
	/**
	 * 获取远程客户端发送过来的数据信息
	 * @param socketInfo
	 * @return
	 */
	public  ReciveData reciveClientMessage(RemoteSocketInfo socketInfo);
	
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
