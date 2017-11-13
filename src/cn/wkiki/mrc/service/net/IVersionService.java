package cn.wkiki.mrc.service.net;

import cn.wkiki.mrc.model.version.MainClientVersion;

public interface IVersionService extends IRemoteService
{
	/**
	 * 检查给定的客户端主程版本信息
	 * 
	 * @param clientVersion
	 *            给定客户端的主程版本
	 */
	void checkClientUpdate(MainClientVersion clientVersion);

	/**
	 * 检查当前主程运行的任务模块的版本信息
	 * 
	 */
	void checkTaskUpdate();
}
