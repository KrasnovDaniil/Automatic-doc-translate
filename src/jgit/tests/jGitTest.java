package jgit.tests;

import jgit.classes.MainService;
import jgit.classes.jGitRepoCreator;
import zip.classes.ValidatorUrl;

public class jGitTest {
    public static void main(String[] args) {
        new MainService(new jGitRepoCreator(), new ValidatorUrl())
                .Run("https://github.com/Grifak/SimbirSoft");
    }
}
