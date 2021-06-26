package test;

import classes.MainService;
import classes.ZipDownloader;
import classes.ZipReader;
import org.apache.commons.validator.routines.UrlValidator;

public class SomeTests {
    public static void main(String[] args) {
        //new MainService(new ZipDownloader(), new ZipReader()).Run("https://github.com/KrasnovDaniil/Automatic-doc-translate/archive/refs/heads/feature-123.zip",
        //        "C:\\Users\\User\\Desktop\\project.zip");
        String[] schemes = {"http", "https", "github.com"};
        UrlValidator urlValidator = new UrlValidator(schemes);
        if(urlValidator.isValid("https://github.com/")){
            System.out.println("valid");
        }
        else{
            System.out.println("invalid");
        }
    }
}
