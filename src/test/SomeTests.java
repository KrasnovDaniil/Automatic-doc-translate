package test;

import classes.*;
import org.apache.commons.validator.routines.UrlValidator;

public class SomeTests {
    public static void main(String[] args) {
        new MainService(new ZipDownloader(), new ZipReader(), new ValidatorUrl(), new ZipWriter()).Run(
                "https://github.com/Grifak/Automatic-doc-translate/archive/refs/heads/feature_zip.zip",
                "C:\\Users\\User\\Desktop\\project.zip");
    }
}
