package cn.zhuqi.oa.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用来定义资源的注解
 * @author Zhuqi
 *
 */
@Retention(RetentionPolicy.RUNTIME) //在运行的时候，可以通过反射机制获得这个注解的有关信息
@Target(ElementType.TYPE) //只能定义在类/接口的前面
public @interface Res {
	
	/**
	 * 资源的名称，必须定义
	 * 如：组织机构管理
	 */
	String name();
	
	/**
	 * 资源的唯一标识，必须定义
	 * 如：party
	 */
	String sn();
	
	/**
	 * 资源的排序号，主要目的是为了在界面上对资源进行管理，以及
	 * 将资源列出进行授权的时候，作排序用途
	 */
	int orderNumber() default 0;
	
	/**
	 * 父资源的唯一标识
	 */
	String parentSn() default "";
}
