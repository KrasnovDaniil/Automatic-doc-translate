package some_tests;

import ADT.services.implementations.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestAllfunc {

    private String longComment = "/**\n" +
            " * Some class for proper work\n" +
            " * {@code SourceCode class} purposes {@code true} for store source code\n" +
            " * @author Daniiil\n" +
            " * @version 1.1\n" +
            " */";
    private String translated = " /**\n" +
            "* Класс для правильной работы\n" +
            " * {@code SourceCode class} цели {@code true} для исходного кода магазина\n" +
            " * @author Daniiil\n" +
            " * @ версия 1.1\n" +
            "*/";

    @Test
    public void testAll(){
        TranslateServiceImpl translateService = new TranslateServiceImpl("en", "ru");
        Finder finder = new Finder(translateService);
        new GitMainService(new GitEmulator("свой_ник_гита", "пароль"),
                new ValidatorUrl(), new ProjectWriter(translateService, finder))
                .Run("ссылка на любой репо с комментами", "путь куда склонировать");
    }

    /**
* Тестирование без клонирования репо, а просто с передачей пути к нужным файлам
*/
    @Test
    public void testWithoutCloneRepo(){
        TranslateServiceImpl translateService = new TranslateServiceImpl("en", "ru");
        Finder finder = new Finder(translateService);
        new GitMainService(new GitEmulator("KrasnovDaniil", "HarrisTeeter12"),
                new ValidatorUrl(), new ProjectWriter(translateService, finder))
                .Run("https://github.com/KrasnovDaniil/Automatic-doc-translate", "C:\\Users\\Daniil\\Desktop\\test");
// статистика = finder.insertTranslation (fileName);
// новый GitMainService (новый GitEmulator (&quot;свой_ник_гита&quot;, &quot;пароль&quot;),
// новый ValidatorUrl (), новый ProjectWriter (translateService, finder))
// .Run (&quot;ссылка на любой репо с комментариями&quot;, &quot;C: \\ Users \\ Daniil \\ Desktop \\ test&quot;);
    }

    @Test
    public void testReplace(){
        String text = "package ADT.models;\n" +
                "\n" +
                "/**\n" +
                " * Some class for proper work\n" +
                " * {@code SourceCode class} purposes {@code true} for store source code\n" +
                " * @author Daniiil\n" +
                " * @version 1.1\n" +
                " */\n" +
                "public class SourceCode {\n" +
                "// однострочный комментарий\n" +
                "    /**\n" +
                "     * New method\n" +
                "     * @param text input text\n" +
                "     * @return output string {@code new String()}\n" +
                "     */\n" +
                "    public String method1(String text){\n" +
                "        System.out.println();\n" +
                "        return null;\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * Second method\n" +
                "     * {@literal Literal something}\n" +
                "     */\n" +
                "    public void method2(){\n" +
                "        System.out.println();\n" +
                "    }\n" +
                "\n" +
                "}";

        String res = text.replace(longComment, translated);
        System.out.println(res);
    }

    @Test
    public void testStream() throws IOException {
        String fileName = "C:\\Users\\Daniil\\Desktop\\test\\src\\main\\java\\ADT\\models\\SourceCode.java";
        Path path = Paths.get(fileName);
        String whole_text = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
        String after_replace = whole_text.replace(longComment, translated);
        System.out.println(after_replace);
    }
}
