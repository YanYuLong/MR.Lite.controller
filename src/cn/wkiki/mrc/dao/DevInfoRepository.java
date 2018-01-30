package cn.wkiki.mrc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
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
	
	public DevInfo GetDevInfo(String DevId,String DevPwd)
	{
		String sql= "select * from DevUserInfo where DevId=:DevId and DevPwd=:DevPwd";
		Map<String, String> paramMap= new HashMap<>();
		paramMap.put("DevId", DevId);
		paramMap.put("DevPwd", DevPwd);
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
}
