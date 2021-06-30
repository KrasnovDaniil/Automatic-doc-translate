package jgit.classes;

import jgit.classes.interfaces.IRepoCreatorService;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;

public class jGitRepoCreator implements IRepoCreatorService {
    public static final String DIRECTORY = "C:\\Users\\User\\NewDir";//пока что на диск С, потом на сервер

    @Override
    public void CreateRepo(String source) {
        try {
            Git.cloneRepository()
                    .setURI(source)
                    .setDirectory(new File(DIRECTORY))
                    .call();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
    }
}
