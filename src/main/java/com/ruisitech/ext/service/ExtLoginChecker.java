//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.ext.service;

import com.ruisi.ext.engine.control.sys.LoginSecurityAdapter;
import com.ruisi.ext.engine.dao.DaoHelper;
import com.ruisi.ext.engine.wrapper.ExtRequest;
import com.ruisi.ext.engine.wrapper.ExtResponse;
import javax.servlet.ServletContext;

public class ExtLoginChecker implements LoginSecurityAdapter {
    public ExtLoginChecker() {
    }

    public boolean loginChk(ExtRequest req, ExtResponse arg1, ServletContext ctx, DaoHelper arg2) {
        return true;
    }
}
