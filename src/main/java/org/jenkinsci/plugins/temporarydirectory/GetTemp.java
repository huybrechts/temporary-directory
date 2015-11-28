package org.jenkinsci.plugins.temporarydirectory;

import hudson.remoting.Callable;
import org.jenkinsci.remoting.RoleChecker;

import java.io.File;

/**
 * Created by awpyv on 30/05/2015.
 */
class GetTemp implements Callable<String, RuntimeException> {

    private static final long serialVersionUID = 1L;

    private final String name;

    GetTemp(String name) {
        this.name = name;
    }


    @Override
    public String call() throws RuntimeException {
        File temp = new File(System.getProperty("java.io.tmpdir"), name);
        if (!temp.exists()) {
            temp.mkdirs();
        }
        return temp.getAbsolutePath();
    }

    @Override
    public void checkRoles(RoleChecker checker) throws SecurityException {
    }
}
