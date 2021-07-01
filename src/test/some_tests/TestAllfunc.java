package some_tests;

import ADT.services.implementations.*;
import org.junit.jupiter.api.Test;

public class TestAllfunc {


    @Test
    public void testAll(){
        TranslateServiceImpl translateService = new TranslateServiceImpl("en", "ru");
        Finder finder = new Finder(translateService);
        new GitMainService(new GitEmulator("KrasnovDaniil", "HarrisTeeter12"),
                new ValidatorUrl(), new ProjectWriter(translateService, finder))
                .Run("https://github.com/KrasnovDaniil/Automatic-doc-translate", "C:\\Users\\Daniil\\Desktop\\test");
    }
}
