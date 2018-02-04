package cn.wkiki.util;

import java.security.MessageDigest;

public class MD5Helper {

	public String toMD5(String plainText) {
		String result="";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			result= buf.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
