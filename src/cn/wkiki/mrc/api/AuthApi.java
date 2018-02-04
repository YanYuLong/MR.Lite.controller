package cn.wkiki.mrc.api;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.corba.se.impl.oa.poa.ActiveObjectMap.Key;

import cn.wkiki.mrc.dao.DevInfoRepository;
import cn.wkiki.mrc.model.user.DevInfo;
import cn.wkiki.util.ConfigInfo;
import cn.wkiki.util.MD5Helper;
import cn.wkiki.util.RandomHelper;

@Controller
@RequestMapping("/auth")
public class AuthApi
{
	private DevInfoRepository devInfoRepository;
	
	@Autowired ConfigInfo commonConfig;
	@Autowired
	public void setDevInfoRepository(DevInfoRepository repository)
	{
		this.devInfoRepository = repository;
	}
	/**
	 * 根据开发者ID及对应的密码验证开发者用户身份
	 * @param devId 开发者ID
	 * @param devPwd 开发者ID 密码
	 * @return
	 */
	@RequestMapping(value="/dev",method=RequestMethod.GET)
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

	/**
	 *添加指定用户为开发者用户 
	 * @param UserId
	 * @return
	 */
	@RequestMapping(value="/dev/{UserId}",method=RequestMethod.POST)
	@ResponseBody
	public Object AddDevUser(@PathVariable String UserId)
	{
		Object result =null;
		DevInfo devInfo= devInfoRepository.GetDevInfo(UserId);
		if(devInfo!=null)
		{
			result=getResultInfo("Error", 401, "用户以是开发者身份", null);
		}
		else
		{
			String randomPwd=RandomHelper.getRandomString(6);
			MD5Helper md5Helper = new MD5Helper();
			String md5Pwd= md5Helper.toMD5(randomPwd);
			UUID uuid= UUID.randomUUID();
			DevInfo newDev = new DevInfo(UserId, uuid.toString(), md5Pwd);
			boolean createResult = devInfoRepository.AddDevInfo(newDev);
			if(createResult)
			{
				result=new Object()
				{
					public String Status="Success";
					public String Message ="请求动作以完成";
					public int StatusCode=200;
					public Object value= new Object()
		    		{
						public String DevId = uuid.toString();
						public String DevPwd=randomPwd;
		    		};
				};
			}
		}
		return result;
	}

	/**
	 * 将指定的用户删除开发者用户身份
	 * @param UserId
	 * @param validRequest
	 * @return
	 */
	@RequestMapping(value="/dev/{UserId}",method=RequestMethod.DELETE)
	@ResponseBody
	public Object DelDevUser(@PathVariable("UserId") String userId)
	{
		Object result = null;
		DevInfo devInfo = devInfoRepository.GetDevInfo(userId);
		if(devInfo==null)
		{
			result= getResultInfo("Success", 200, "删除用户开发者身份成功", null);
		}
		else
		{
			boolean delResult = false;
			try
			{
				delResult = devInfoRepository.DelDevInfo(userId);
			}
			catch(Exception e)
			{
				Map<String , Object> map= new HashMap<String,Object>();
				map.put("ExceptionMessage", e.toString());
				result= getResultInfo("Fail",500,"删除用户开发者身份失败",map);
			}
			if(delResult)
			{
				result= getResultInfo("Success", 200, "删除用户开发者身份成功", null);
			}
		}
		return result;
	}

	/**
	 * 更新指定用户的开发者ID对应的密码
	 * @return
	 */
	@RequestMapping(value="/dev/update",method=RequestMethod.POST)
	@ResponseBody
	public Object UpdateDevPwd(@RequestBody DevInfo devInfo) 
	{
		Object result=null;
		String userId = devInfo.getUserId();
		if(userId.equals("")||userId.length()!=36)
		{
			result= getResultInfo("Fail",400,"用户名长度非法",null);
		}
		else {
			DevInfo oldDevInfo = devInfoRepository.GetDevInfo(userId);
			if(oldDevInfo==null)
			{
				result=getResultInfo("Fail",400,"用户名不是开发者用户",null);
			}
			else {
				if(devInfo.getDevPwd().equals("")||devInfo.getDevPwd().length()!=32)
				{
					result = getResultInfo("Fail", 401, "密码格式错误",null);
				}
			}
		}
		return result;
	}
	
	/**
	 * 获取API 返回json结果的方法
	 * @param status 返回结果的状态描述，通常是 success、fail、error之类有明确含义的词
	 * @param statusCode 操作结果状态码，通常使用类HTTP响应码类似的表示例如 200、400、401之类
	 * @param message 对操作结果的详细说明，具有丰富的操作语义解释
	 * @param param 额外的需要返回的数据(PS:此属性为根对象的属性域)
	 * @return
	 */
	private Object getResultInfo(String status,int statusCode,String message,Map<String, Object> param)
	{
		Object result = null;
		Map<String , Object> resultMap = new  HashMap<>();
		resultMap.put("Status", status);
		resultMap.put("StatusCode",statusCode);
		resultMap.put("Message", message);
		if(param!=null&&param.size()>0)
		{
			Set<String> keys= param.keySet();
			for(String key :keys)
			{
				Object value = param.get(key);
				resultMap.put(key, value);
			}
		}
		return result;
	}
}
