package ADT;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.google.cloud.translate.*;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
//        SpringApplication.run(Application.class, args);
        // TODO(developer): Uncomment these lines.
        System.setProperty("GOOGLE_API_KEY", "AIzaSyCR596gXL2VWvzI7Zt1ldChy0_cvOc4pvE");
 Translate translate = TranslateOptions.getDefaultInstance().getService();

//        Translation translation = translate.translate("¡Hola Mundo!");
//        System.out.printf("Translated Text:\n\t%s\n", translation.getTranslatedText());
        Translation translation =
                translate.translate(
                        "Ну наконец то заработало а то сколько можно уже!",
                        Translate.TranslateOption.sourceLanguage("ru"),
                        Translate.TranslateOption.targetLanguage("en"),
                        // Use "base" for standard edition, "nmt" for the premium model.
                        Translate.TranslateOption.model("nmt"));

        System.out.printf("TranslatedText:\nText: %s\n", translation.getTranslatedText());
    }
}
