//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.util;

import com.ruisitech.bi.entity.common.BaseEntity;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;
import org.apache.commons.beanutils.PropertyUtils;

public class SortService implements Comparator<BaseEntity> {
    private String sort;
    private String order;

    public SortService(String sort, String order) {
        this.sort = sort;
        this.order = order;
    }

    public int compare(BaseEntity o1, BaseEntity o2) {
        Object val1;
        Object val2;
        try {
            val1 = PropertyUtils.getProperty(o1, this.sort);
            val2 = PropertyUtils.getProperty(o2, this.sort);
        } catch (Exception var6) {
            val1 = null;
            val2 = null;
            var6.printStackTrace();
        }

        if (val1 != null && val2 != null) {
            int ret = 0;
            if (val1 instanceof String) {
                ret = ((String)val1).compareTo((String)val2);
            } else if (val1 instanceof Long) {
                ret = ((Long)val1).compareTo((Long)val2);
            } else if (val1 instanceof Integer) {
                ret = ((Integer)val1).compareTo((Integer)val2);
            } else if (val1 instanceof BigDecimal) {
                ret = ((BigDecimal)val1).compareTo((BigDecimal)val2);
            } else if (val1 instanceof Date) {
                ret = ((Date)val1).compareTo((Date)val2);
            }

            if ("desc".equalsIgnoreCase(this.order)) {
                ret = -ret;
            }

            return ret;
        } else {
            return 0;
        }
    }
}
