package jgit.classes;

import jgit.classes.interfaces.IRepoCreatorService;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;
import java.io.IOException;

public class jGitRepoCreator implements IRepoCreatorService {

    private String login;
    private String password;

    @Override
    public void CreateRepo(String source, String destination) {
        try {
            File dir = new File(destination);

            CloneCommand clone = Git.cloneRepository();
            clone.setDirectory(dir);
            clone.setURI(source);
            clone.setCredentialsProvider(new UsernamePasswordCredentialsProvider(login,
                    password));
            clone.call();

            Git git = Git.open(dir);

            git.checkout().setCreateBranch(true).setName("translate_project").call();

            git.commit().setMessage("Initial commit").call();

            git.add().addFilepattern(".").call();

            git.commit().setMessage("add jGit_tester").call();

            //git.push().call();


        } catch (TransportException e){
            e.printStackTrace();
            System.out.println("Username or password incorrect");
        } catch (GitAPIException | IOException e) {
            e.printStackTrace();
        }
    }
}
