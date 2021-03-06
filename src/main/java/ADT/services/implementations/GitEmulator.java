package ADT.services.implementations;

import ADT.services.interfaces.IGitService;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

public class GitEmulator implements IGitService {

    private String login;
    private String password;
    private String repoUrl;

    public GitEmulator(String login, String password){
        this.login = login;
        this.password = password;
    }

    @Override
    public void initRepoAndNewBranch(String source, String destination) {
        repoUrl = source;
        try {
            File dir = new File(destination);

            CloneCommand clone = Git.cloneRepository();
            clone.setDirectory(dir);
            clone.setURI(source);
            clone.call();

            Git git = Git.open(dir);

            git.checkout().setCreateBranch(true).setName("translated_project").call();

            git.commit().setMessage("Initial commit").call();

        } catch (GitAPIException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void commitAndPush(String directory) {
        try {
            Git git = Git.open(new File(directory));
            git.add().addFilepattern(".").call();

            git.commit().setMessage("comments have been translated").call();

            PushCommand push = git.push();
            push.setCredentialsProvider(new UsernamePasswordCredentialsProvider(login,
                    password));
            push.call();
        } catch (TransportException e){
            e.printStackTrace();
            System.out.println("Invalid username or password");
        } catch (GitAPIException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String createPullRequest(String directory) {
        String url = null;
        return url;
    }

    public String getBranchLink(){
        return repoUrl + "/tree/translated_project";
    }
}
