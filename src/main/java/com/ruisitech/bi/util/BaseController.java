//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.util;

import com.github.pagehelper.PageInfo;
import com.ruisitech.bi.entity.common.RequestStatus;
import com.ruisitech.bi.entity.common.Result;

public class BaseController {
    public BaseController() {
    }

    public Object buildSucces() {
        return new Result(RequestStatus.SUCCESS.getStatus(), "操作成功", (Object)null);
    }

    public Object buildSucces(Object datas) {
        return new Result(RequestStatus.SUCCESS.getStatus(), "操作成功", datas);
    }

    public Object buildSucces(PageInfo<?> page) {
        Long total = page.getTotal();
        Object datas = page.getList();
        return new Result(RequestStatus.SUCCESS.getStatus(), "操作成功", datas, total);
    }

    public Object buildError(String msg) {
        return new Result(RequestStatus.FAIL_FIELD.getStatus(), msg, (Object)null);
    }
}
