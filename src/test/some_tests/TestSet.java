package some_tests;

import ADT.services.implementations.Finder;
import ADT.services.implementations.ProjectWriter;
import ADT.services.implementations.TranslateServiceImpl;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import org.apache.commons.text.StringEscapeUtils;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.sun.javadoc.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

class TestSet {
    private Translate translate;
//    @Autowired
//    private TranslateServiceImpl translateService;

    private String escape2html(String input, boolean isHtml){
        if (isHtml) {
            input = input.replaceAll("(<nnn>)", "\n");
            input = input.replaceAll("(<rrr>)", "\r");
            input = input.replaceAll("(<docBeg>)", "/**");
            input = input.replaceAll("(<docEnd>)", "*/");

        }
        else{
            input = input.replaceAll("\\n", "<nnn>");
            input = input.replaceAll("\\r", "<rrr>");
            input = input.replaceAll("/\\*\\*", "<docBeg>");
            input = input.replaceAll("\\*/", "<docEnd>");

        }
        return input;
    }

    @Test
    public void testReplace(){
        String normInput = "/** Просто документа\n ция на русском\n Да! */";
        String input = "/ ** Пример 3 файла кода \\ r <br> * Какой-то класс для правильной работы \\ r <br> * {@code SourceCode class} ";
//        String input = "/ ** Пример 3 файла кода \\ r \\ n * Какой-то класс для правильной работы \\ r \\ n * {@code SourceCode class} ";
        System.out.println(normInput);
        normInput = escape2html(normInput, false);
        Translation translation =
                translate.translate(
                        normInput,
                        Translate.TranslateOption.sourceLanguage("ru"),
                        Translate.TranslateOption.targetLanguage("en"),
                        Translate.TranslateOption.model("nmt"));
        String ans = translation.getTranslatedText();
        ans = escape2html(ans, true);
        System.out.println(ans);
    }

    @BeforeEach
    public void Setup() {
        translate = TranslateOptions.getDefaultInstance().getService();

    }

    @Test
    public void testEscapeConseq() {
        String text = "/**\r\n * Some class for proper work\r\n * {@code SourceCode class} purposes {@code true} for store source code\r\n * @author Daniiil\r\n * @version 1.1\r\n */";
        String translation = "/ ** \\ r \\ n * Какой-то класс для правильной работы \\ r \\ n * {@code SourceCode class} цели {@code true} для исходного кода магазина \\ r \\ n * @author Daniiil \\ r \\ n * @version 1.1 \\ r \\ n * /";
        String translation1 = "/ ** \\ r <br> * Какой-то класс для правильной работы \\ r <br> * {@code SourceCode class} цели {@code true} для исходного кода магазина \\ r <br> * @author Daniiil \\ r <br> * @version 1.1 \\ r <br> * /";
        String unEscapeTrans = "/ **  r  n * Какой-то класс для правильной работы  r  n * {@code SourceCode class} цели {@code true} для исходного кода магазина  r  n * @author Daniiil  r  n * @version 1.1  r  n * /";
        String html_escp = StringEscapeUtils.escapeHtml3(translation1);
        System.out.println(html_escp);

        System.out.println(text);
        String text1 = StringEscapeUtils.escapeJava(text);
        System.out.println(text1);
        String text2 = StringEscapeUtils.unescapeJava(text1);
        System.out.println(text2);

    }

    @Test
    void testTranslateDirectories() {
        TranslateServiceImpl translateService = new TranslateServiceImpl("en", "ru");
        Finder finder = new Finder(translateService);
        String folder_path = "C:\\Users\\Daniil\\Desktop\\refactoredGUI\\src\\test\\some_tests\\test_folder";
        Map<String, Integer> stats = new HashMap<String, Integer>();
        new ProjectWriter(translateService, finder).Write(folder_path);
//        stats = finder.insertTranslation(fileName);
        System.out.println("Translation complete!");
    }


    @Test
    void testFindAndTranslate() {
        TranslateServiceImpl translateService = new TranslateServiceImpl("en", "ru");
        Finder finder = new Finder(translateService);
        String fileName = "C:\\Users\\Daniil\\Desktop\\refactoredGUI\\src\\main\\java\\ADT\\textSamples";
        Map<String, Integer> stats = new HashMap<String, Integer>();

        stats = finder.insertTranslation(fileName);
        System.out.println("Translation complete!");
    }

    @Test
    void testTranslation() {
        String sourceText = "простой тест на перевод";
        String answer = "simple translation test";
        Translation translation =
                translate.translate(
                        sourceText,
                        Translate.TranslateOption.sourceLanguage("ru"),
                        Translate.TranslateOption.targetLanguage("en"),
                        Translate.TranslateOption.model("nmt"));

        Assert.assertEquals(answer, translation.getTranslatedText());
    }

    @Test
    void tryTranslateSmth() {
        String sourceText = "Введите сюда текст для перевода<br> вторая строка<br> плюс ещё одна<br>";
        System.out.println(sourceText);
        Translation translation =
                translate.translate(
                        sourceText,
                        Translate.TranslateOption.sourceLanguage("ru"),
                        Translate.TranslateOption.targetLanguage("en"),
                        Translate.TranslateOption.model("nmt"));
        String result = translation.getTranslatedText();
        System.out.println("Translated text: " + result);
        System.out.println();
    }
}
