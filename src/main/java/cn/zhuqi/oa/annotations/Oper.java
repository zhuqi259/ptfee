package cn.zhuqi.oa.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用来定义操作的注解
 * @author Zhuqi
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD) //只能定义在方法的前面
public @interface Oper {
	
	/**
	 * 操作的名称，如果不定义这个属性，将自动根据方法的命名自动赋予一个值，规则如下：
	 * add开头的方法，自动给予一个名称：添加
	 * update开头的方法，自动给予一个名称：更新
	 * del开头的方法，自动给予一个名称：删除
	 * 其它方法，自动给予一个名称：查询
	 */
	String name() default "";
	
	/**
	 * 操作的唯一标识，如果不定义这个属性，将自动根据方法的命名自动赋予一个值，规则如下：
	 * add开头的方法，自动给予一个唯一标识：CREATE
	 * update开头的方法，自动给予一个唯一标识：UPDATE
	 * del开头的方法，自动给予一个唯一标识：DELETE
	 * 其它方法，自动给予一个唯一标识：READ
	 */
	String sn() default "";
	
	/**
	 * 操作对应的索引值，如果不定义这个属性，将自动根据方法的命名自动赋予一个值，规则如下：
	 * add开头的方法，自动给予一个索引值：0
	 * update开头的方法，自动给予一个索引值：1
	 * del开头的方法，自动给予一个索引值：2
	 * 其它方法自动给予一个索引值：3
	 */
	int index() default -1;
}
