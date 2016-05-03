package mblog.base.utils;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

/**
 * @author langhsu on 2015/8/31.
 */
public class NumberUtils {

    public static int changeToInt(Object obj) {
        int i = 0;
        if (obj != null && StringUtils.isNumeric(obj.toString())) {
            i = ((Number) obj).intValue();
        }
        return i;
    }

    public static Set<Long> toList(String ids) {
        Set<Long> ret = new HashSet<Long>();
        if (StringUtils.isNotBlank(ids)) {
            for (String s : ids.split(",")) {
                long id = org.apache.commons.lang.math.NumberUtils.toLong(s);
                if (id > 0) {
                    ret.add(id);
                }
            }
        }
        return ret;
    }
}
