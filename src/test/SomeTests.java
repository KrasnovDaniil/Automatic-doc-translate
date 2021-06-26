package test;

import classes.MainService;
import classes.ZipDownloader;
import classes.ZipReader;

public class SomeTests {
    public static void main(String[] args) {
        new MainService(new ZipDownloader(), new ZipReader()).Run("https://github.com/KrasnovDaniil/Automatic-doc-translate/archive/refs/heads/feature-123.zip",
                "C:\\Users\\User\\Desktop\\project.zip");
    }
}
