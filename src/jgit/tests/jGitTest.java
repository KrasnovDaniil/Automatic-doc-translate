package jgit.tests;

import jgit.classes.GItMainService;
import jgit.classes.ProjectWriter;
import jgit.classes.gitEmulator;
import zip.classes.ValidatorUrl;

public class jGitTest {
    public static void main(String[] args) {
        new GItMainService(new gitEmulator("Grifak", "Dmitrii01017813052001"),
                new ValidatorUrl(), new ProjectWriter())
                .Run("https://github.com/Grifak/SimbirSoft", "C:\\Users\\User\\NewDir");
    }
}
