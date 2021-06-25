package some_tests;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class TestSet {
    private Translate translate;

    @BeforeEach
    public void Setup(){
        translate = TranslateOptions.getDefaultInstance().getService();
    }

    @Test
    void testTranslation(){
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
    void tryTranslateSmth(){
        String sourceText = "Введите сюда текст для перевода";
        Translation translation =
                translate.translate(
                        sourceText,
                        Translate.TranslateOption.sourceLanguage("ru"),
                        Translate.TranslateOption.targetLanguage("en"),
                        Translate.TranslateOption.model("nmt"));

        System.out.println("Translated text: " + translation.getTranslatedText());
    }
}
