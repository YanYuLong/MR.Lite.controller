package cn.wkiki.mrc.protocol;

public interface IClientNetRawDataReciver
{
	/**
	 * 接收指定客户端socket的原始数据流
	 * @param remoteSocketInfo
	 * @return
	 * @throws Throwable
	 */
	public byte[] reciveRowData(RemoteSocketInfo remoteSocketInfo) throws Throwable;
}
