package org.jenkinsci.plugins.temporarydirectory;

import hudson.EnvVars;
import hudson.Extension;
import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.BuildListener;
import hudson.model.Run;
import hudson.tasks.BuildWrapper;
import hudson.tasks.BuildWrapperDescriptor;
import org.kohsuke.stapler.DataBoundConstructor;

import java.io.IOException;

/**
 * Created by awpyv on 30/05/2015.
 */
public class TempDirBuildWrapper extends BuildWrapper {

    @DataBoundConstructor
    public TempDirBuildWrapper() {}

    @Override
    public Environment setUp(AbstractBuild build, Launcher launcher, BuildListener listener) throws IOException, InterruptedException {

        return new Environment() {
            @Override
            public boolean tearDown(AbstractBuild build, BuildListener listener) throws IOException, InterruptedException {
                TempDirAction action = build.getAction(TempDirAction.class);
                build.getWorkspace().act(new CleanTemp(listener, action.getTemp()));
                build.getActions().remove(action);
                return true;
            }
        };
    }

    @Override
    public Launcher decorateLauncher(AbstractBuild build, Launcher launcher, BuildListener listener) throws IOException, InterruptedException, Run.RunnerAbortedException {
        final String temp = launcher.getChannel().call(new GetTemp("jenkins-"+ build.getExecutor().getNumber()));

        final TempDirAction action = new TempDirAction(temp);
        build.addAction(action);

        return new TempLauncher(launcher, action.getTemp());
    }

    @Extension
    public static class Descriptor extends BuildWrapperDescriptor {
        public Descriptor() {
        }

        @Override
        public boolean isApplicable(AbstractProject<?, ?> item) {
            return true;
        }

        public String getDisplayName() {
            return "Assign a temporary directory that is cleaned after the build";
        }
    }

    ;

}
