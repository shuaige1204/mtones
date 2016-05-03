/*
+--------------------------------------------------------------------------
|   mtons [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package mblog.core.modules.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>扩展自 spring @Repository</p>
 * 
 * <p>使用此功能之前, 记得开启注解配置  &lt;context:annotation-config /&gt;
 * </p>
 * 
 * @author langhsu
 */
@Target(ElementType.TYPE)  
@Retention(RetentionPolicy.RUNTIME)  
@Documented  
@org.springframework.stereotype.Repository 
public @interface Repository {
	
	/**
     * Repository在spring容器中的实例名称
     * 
	 * @return string
	 */
    String value() default "";  
  
    /** 
     * Repository处理的实体类 
     *  
     * @return class
     */  
    Class<?> entity(); 
}
