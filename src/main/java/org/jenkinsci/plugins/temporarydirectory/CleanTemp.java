package org.jenkinsci.plugins.temporarydirectory;

import hudson.FilePath;
import hudson.model.TaskListener;
import hudson.remoting.Callable;
import org.jenkinsci.remoting.RoleChecker;

import java.io.File;
import java.io.IOException;
import java.io.InterruptedIOException;

/**
 * Created by awpyv on 30/05/2015.
 */
class CleanTemp implements Callable<Void, IOException> {

    private static final long serialVersionUID = 1L;
    private final TaskListener listener;
    private final String temp;

    public CleanTemp(TaskListener listener, String temp) {
        this.listener = listener;
        this.temp = temp;
    }

    @Override
    public Void call() throws IOException {
        try {
            new FilePath(new File(temp)).deleteRecursive();
        } catch (InterruptedException e) {
            throw new InterruptedIOException();
        } catch (IOException e) {
            listener.getLogger().println("[temporary-directory-plugin] Failed to delete temporary directory. " + e.getMessage());
        }
        return null;
    }

    @Override
    public void checkRoles(RoleChecker checker) throws SecurityException {
    }
}
