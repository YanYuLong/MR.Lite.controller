package cn.wkiki.mrc.service.net;

import java.util.LinkedList;
import java.util.List;

import cn.wkiki.mrc.model.RemoteSocketInfo;

/**
 * 控制单元持有的socket连接池
 * @author yulongy
 *
 */
public class SocketPool
{
	//远程客户端连接信息表
	List<RemoteSocketInfo> socketTable= new LinkedList<RemoteSocketInfo>();
}
