package cn.wkiki.mrc.model;

import java.net.Socket;
import java.sql.Date;

/**
 * 远程连接池的信息
 * @author yulongy
 *
 */
public class RemoteSocketInfo
{
	//远程客户端的socket引用
	private Socket remoteSocket;
	//连接开始的时间
	private Date connectedTime;
}