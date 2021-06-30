package zip.tests;

import zip.classes.MainService;
import zip.classes.ValidatorUrl;
import zip.classes.ZipDownloader;
import zip.classes.ZipWriter;

public class SomeTests {
    public static void main(String[] args) {
        new MainService(new ZipDownloader(), new ValidatorUrl(), new ZipWriter()).Run(
                "https://github.com/Grifak/Automatic-doc-translate/archive/refs/heads/feature_zip.zip",
                ".\\project.zip");
    }
}
