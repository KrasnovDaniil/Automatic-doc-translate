package ADT.services.interfaces;

public interface IGitService {
    void initRepoAndNewBranch(String source, String destination);
    void commitAndPush(String directory);
    String createPullRequest(String directory);
}
