package org.jenkinsci.plugins.temporarydirectory;

import hudson.Launcher;
import hudson.Proc;

import java.io.IOException;
import java.io.InterruptedIOException;

/**
 * Created by awpyv on 30/05/2015.
 */
class TempLauncher extends Launcher.DecoratedLauncher {

    private final String tempDir;

    public TempLauncher(Launcher inner, String tempDir) {
        super(inner);
        this.tempDir = tempDir;
    }

    @Override
    public Proc launch(ProcStarter starter) throws IOException {
        String[] envs = starter.envs();
        for (int i = 0 ; i < envs.length; i++) {
            if (envs[i] != null && envs[i].startsWith("TEMP=")) {
                envs[i] = "TEMP=" + tempDir;
            }
            if (envs[i] != null && envs[i].startsWith("TMP=")) {
                envs[i] = "TMP=" + tempDir;
            }
        }
        starter.envs(envs);
        return super.launch(starter);
    }


}
