package jgit.classes.interfaces;

import org.eclipse.jgit.api.Git;

public interface IGitService {
    void initRepoAndNewBranch(String source, String destination);
    void commitAndPush(String directory);
    String createPullRequest(String directory);
}
