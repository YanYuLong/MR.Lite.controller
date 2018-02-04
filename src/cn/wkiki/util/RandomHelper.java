package cn.wkiki.util;

import java.util.Random;

public class RandomHelper
{
	static String charArr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890@#$%!^&*:<>?";
	
	static Random random =null;
	/**
	 * 获取指定长度的随机字符串
	 * @param len 要获取随机字符串的长度
	 * @return
	 */
	public static String getRandomString(int len)
	{
		String result ="";
		if(len<1)
		{
			throw new  RuntimeException("给定的长度非法");
		}
		else if(random==null) {
			random=new Random();
		}
		StringBuilder sBuilder = new StringBuilder();
		while(len>0)
		{
			int rand= random.nextInt(charArr.length());
			sBuilder.append(charArr.charAt(rand));
			len--;
		}
		result= sBuilder.toString();
		return result;
	}
}
