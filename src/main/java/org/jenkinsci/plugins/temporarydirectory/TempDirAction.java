package org.jenkinsci.plugins.temporarydirectory;

import hudson.model.InvisibleAction;

/**
 * Created by awpyv on 30/05/2015.
 */
public class TempDirAction extends InvisibleAction {

    private static final long serialVersionUID = 1L;

    private final String temp;

    public TempDirAction(String temp) {
        this.temp = temp;

    }
    public String getTemp() {
        return temp;
    }
}
