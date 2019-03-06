//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.service;

import com.ruisi.ext.engine.control.InputOption;
import com.ruisi.ext.engine.view.exception.ExtConfigException;

import java.io.IOException;

public interface Service {
    void execute(InputOption var1) throws IOException, ExtConfigException;
}
