package cn.zhuqi.oa.test;

import java.util.HashSet;
import java.util.Set;

import cn.zhuqi.system.SetStringUtil;

import junit.framework.TestCase;

public class SetStringUtilTest extends TestCase {

	public void testSet2Str() {
		Set<String> set = new HashSet<String>();
		set.add("aaaaaaaaaa");
		set.add("vvvvv");
		System.out.println(SetStringUtil.Set2Str(set));
	}
}
