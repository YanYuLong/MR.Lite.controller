package cn.wkiki.mrc.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.org.apache.xml.internal.resolver.helpers.PublicId;

import cn.wkiki.mrc.dao.DevInfoRepository;
import cn.wkiki.mrc.model.user.DevInfo;

@Controller
@RequestMapping("/auth")
public class AuthApi
{
	private DevInfoRepository devInfoRepository;
	
	@Autowired
	public void setDevInfoRepository(DevInfoRepository repository)
	{
		this.devInfoRepository = repository;
	}
	@RequestMapping("/dev")
	@ResponseBody
	public Object AuthDevUser(@RequestParam("DevId") String devId,@RequestParam("DevPwd") String devPwd)
	{
		Object result = null;
		DevInfo devInfo = devInfoRepository.GetDevInfo(devId, devPwd);
		if(devInfo!=null)
		{
			result = new Object(){
				public String Stataus="ok";
				public String Message="AuthSuccess";
				public String statusCode ="200";
			};
		}
		else
		{
			result = new Object(){
				public String Stataus="ok";
				public String Message="AuthFail";
				public String statusCode ="400";
			};
		}
		return result;
	}
}
