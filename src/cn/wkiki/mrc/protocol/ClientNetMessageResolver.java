package cn.wkiki.mrc.protocol;

/**
 * 解析与客户端进行通讯的协议内容的接口
 * 
 * @author yulongy
 *
 */
public interface ClientNetMessageResolver
{
	Object resloverMessage(Object message);
}
