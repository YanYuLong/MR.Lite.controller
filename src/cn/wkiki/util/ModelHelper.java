package cn.wkiki.util;

import java.util.*;

import java.lang.reflect.*;
import java.sql.*;

public class ModelHelper
{
	public static <T extends Object> List<T> getModelsByResultSet(Class<T> t, ResultSet resultSet) throws Exception
	{
		List<T> result = new LinkedList<T>();
		Field[] fields = t.getDeclaredFields();
		while (resultSet.next())
		{
			T newModel = t.newInstance();
			for (Field field : fields)
			{
				String columnName = "";
				DataColumn columnAnnotation = field.<DataColumn>getAnnotation(DataColumn.class);
				if (columnAnnotation == null)
				{
					columnName = field.getName();
				} else
				{
					columnName = columnAnnotation.columnName();
				}
				Object value = null;
				try
				{
					value = resultSet.getObject(columnName);
				} catch (Exception e)
				{
					e.printStackTrace();
					throw e;
				}
				field.setAccessible(true);
				if (field.getType().isEnum() == true)
				{
					Object[] arr = field.getType().getEnumConstants();
					for (Object e : arr)
					{
						Enum<?> en = (Enum<?>) e;
						Integer integer = new Integer(value.toString());
						if (en.ordinal() == integer)
						{
							field.set(newModel, en);
							break;
						}
					}
				} else
				{
					field.set(newModel, value);
				}
			}
			result.add(newModel);
		}
		return result;
	}
}
