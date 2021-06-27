package test;

import classes.MainService;
import classes.ValidatorUrl;
import classes.ZipDownloader;
import classes.ZipReader;
import org.apache.commons.validator.routines.UrlValidator;

public class SomeTests {
    public static void main(String[] args) {
        new MainService(new ZipDownloader(), new ZipReader(), new ValidatorUrl()).Run("https://github.com/Krasnov/Automatic-doc-translate/archive/refs/heads/feature-123.zip",
                "C:\\Users\\User\\Desktop\\project.zip");
    }
}
