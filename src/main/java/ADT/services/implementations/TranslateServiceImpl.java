package ADT.services.implementations;

import ADT.services.interfaces.ITranslateService;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import org.springframework.stereotype.Service;

@Service
public class TranslateServiceImpl implements ITranslateService {

    private String insertedText;  // так как текста будет много, придётся переводить его в потоке
    private String translatedText;

    private String sourceLang = "ru";
    private String targetLang = "en";
    private Translate translate;

    public TranslateServiceImpl() {
    }

    public TranslateServiceImpl(String sourceLang, String targetLang) {
        this.sourceLang = sourceLang;
        this.targetLang = targetLang;
        translatedText = null;
        this.translate = TranslateOptions.getDefaultInstance().getService();
    }

    /** insert text from source code */
    @Override
    public void insertText() {
        // тут будет функционал Евгения для извлечения комментов, доков из исходников
    }

    /** translate inserted text using Google Cloud Translate API
     * @param text input text
     * */
    @Override
    public String translateText(String text) {
        text = escape2html(text, false);
        Translation translation = translate.translate(text,
                Translate.TranslateOption.sourceLanguage(sourceLang),
                Translate.TranslateOption.targetLanguage(targetLang),
//                Translate.TranslateOption.model("base"));
                Translate.TranslateOption.model("nmt"));
        translatedText = translation.getTranslatedText();
        translatedText = escape2html(translatedText, true);
        return translatedText;
    }

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
}
