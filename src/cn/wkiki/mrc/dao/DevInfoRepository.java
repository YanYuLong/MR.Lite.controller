package cn.wkiki.mrc.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

import cn.wkiki.mrc.model.user.DevInfo;
import cn.wkiki.util.ModelHelper;

/**
 * 开发人员信息相关Dao 类
 * @author 严玉龙
 *
 */
@Component
public class DevInfoRepository
{
	JdbcOperations operations;
	NamedParameterJdbcTemplate namedOperations;
	@Autowired
	public void setOperations(JdbcOperations operations)
	{
		this.operations = operations;
	}
	@Autowired
	public void setNamedOperations(NamedParameterJdbcTemplate template)
	{
		this.namedOperations = template;
	}
	/**
	 * 根据开发者ID及开发者密码获取开发者信息
	 * @param DevId 开发者ID
	 * @param DevPwd 开发者ID对应的密码
	 * @return
	 */
	public DevInfo GetDevInfo(String devId,String devPwd)
	{
		String sql= "select * from DevUserInfo where DevId=:DevId and DevPwd=:DevPwd";
		Map<String, String> paramMap= new HashMap<>();
		paramMap.put("DevId", devId);
		paramMap.put("DevPwd", devPwd);
		DevInfo result =null;
		result =namedOperations.query(sql, paramMap, new ResultSetExtractor<DevInfo>()
		{
			@Override
			public DevInfo extractData(ResultSet rs) throws SQLException, DataAccessException
			{
				DevInfo result =null;
				try
				{
					java.util.List<DevInfo> sqlResult =ModelHelper.getModelsByResultSet(DevInfo.class, rs);
					if(sqlResult!=null&&sqlResult.size()>0)
					{
						result= sqlResult.get(0);
					}
				}
				catch (Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return result;
			}
		});
		return result;
	}

	/**
	 * 根据用户ID 获取用户的开发者信息
	 * @param UserId 用户ID
	 * @return
	 */
	public DevInfo GetDevInfo(String userId)
	{
		DevInfo result =null;
		String sql = "select * from devuserinfo where UserId=:UserId";
		Map<String , String> param =new HashMap<>();
		param.put("UserId",userId);
		result = namedOperations.query(sql,param,new ResultSetExtractor<DevInfo>()
		{
			@Override
			public DevInfo extractData(ResultSet rs) throws SQLException, DataAccessException
			{
				DevInfo res = null;
				try
				{
					java.util.List<DevInfo> sqlResult = ModelHelper.getModelsByResultSet(DevInfo.class, rs);
					if(sqlResult.size()>0)
					{
						res = sqlResult.get(0);
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				return res;
			}
		});
		return result;
	}

	/**
	 * 添加指定的开发者信息
	 * @param devInfo 要添加到系统中的开发者信息
	 * @return
	 */
	public boolean AddDevInfo(DevInfo devInfo)
	{
		boolean result = false;
		String sql= "insert into devuserinfo(DevId,DevPwd,UserId) values(:DevId,:DevPwd,:UserId)";
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("DevId", devInfo.getDevId());
		paramMap.put("DevPwd", devInfo.getDevPwd());
		paramMap.put("UserId", devInfo.getUserId());
		int effectRows = namedOperations.update(sql, paramMap);
		if(effectRows==1)
		{
			result=true;
		}
		return  result;
	}

	/**
	 * 删除指定用户ID的开发者身份
	 * @param userId
	 * @return
	 */
	public boolean DelDevInfo(String userId)
	{
		boolean result =false;
		String sql="delete from devuserinfo where UserId =:UserId ";
		Map<String , String > paramMap = new  HashMap<>();
		paramMap.put("UserId", userId);
		int effectRows = namedOperations.update(sql, paramMap);
		if(effectRows==1)
		{
			result = true;
		}
		return result;
	}

	/**
	 * 修改开发者账户的密码
	 * @param devInfo
	 * @return
	 */
	public boolean UpdateDevPwd(DevInfo devInfo)
	{
		boolean result = false;
		String sql = "update devuserinfo set DevPwd=:DevPwd where DevId=:DevId and UserId=:UserId";
		Map<String , String> paramMap= new HashMap<>();
		paramMap.put("DevPwd", devInfo.getDevPwd());
		paramMap.put("DevId", devInfo.getDevId());
		paramMap.put("UserId", devInfo.getUserId());
		int effectRows = namedOperations.update(sql, paramMap);
		if(effectRows==1) 
		{
			result= true;
		}
		return result;
	}
}
