package cn.wkiki.mrc.model.user;

/**
 * 开发人员信息
 * @author 严玉龙
 *
 */
public class DevInfo
{
	private  String userId;
	
	private String devId;
	
	private  String devPwd;

	public DevInfo() {};
	
	public DevInfo(String userId,String devId,String devPwd)
	{
		this.userId= userId;
		this.devId=devId;
		this.devPwd = devPwd;
	}
	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getDevId()
	{
		return devId;
	}

	public void setDevId(String devId)
	{
		this.devId = devId;
	}

	public String getDevPwd()
	{
		return devPwd;
	}

	public void setDevPwd(String devPwd)
	{
		this.devPwd = devPwd;
	}
}
