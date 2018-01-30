package cn.wkiki.util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * Javabean对应到数据库中数据表列名的注解
 * @author yulongy
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface DataColumn {
	/**
	 * 数据库中对应的列名
	 * @return
	 */
	String columnName() default "";
}
