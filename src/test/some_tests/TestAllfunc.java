package some_tests;

import ADT.services.implementations.*;
import org.junit.jupiter.api.Test;

public class TestAllfunc {


    @Test
    public void testAll(){
        TranslateServiceImpl translateService = new TranslateServiceImpl("en", "ru");
        Finder finder = new Finder(translateService);
        new GitMainService(new GitEmulator("свой_ник_гита", "пароль"),
                new ValidatorUrl(), new ProjectWriter(translateService, finder))
                .Run("ссылка на любой репо с комментами", "путь куда склонировать");
    }
}
