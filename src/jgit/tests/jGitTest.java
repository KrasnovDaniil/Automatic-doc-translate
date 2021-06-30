package jgit.tests;

import jgit.classes.jGItMainService;
import jgit.classes.ProjectWriter;
import jgit.classes.jGitRepoCreator;
import zip.classes.ValidatorUrl;

public class jGitTest {
    public static void main(String[] args) {
        new jGItMainService(new jGitRepoCreator(), new ValidatorUrl(), new ProjectWriter())
                .Run("https://github.com/Grifak/SimbirSoft", "C:\\Users\\User\\IdeaProjects\\Automatic-doc-translate");
    }
}
