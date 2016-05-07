package cn.zhuqi.system;


import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Set;

public class SetStringUtil {

    public static String Set2Str(Set<String> set) {
        return StringUtils.join(set.toArray(), ";<br>");
    }

    public static Set<String> Str2Set(String str) {
        Set<String> set = new HashSet<String>();
        String s[] = str.split(";<br>");
        for (int i = 0; i < s.length; i++) {
            set.add(s[i]);
        }
        return set;
    }

}
