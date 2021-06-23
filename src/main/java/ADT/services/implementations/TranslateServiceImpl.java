package ADT.services.implementations;

import ADT.services.interfaes.ITranslateService;
import org.springframework.stereotype.Service;

@Service
public class TranslateServiceImpl implements ITranslateService {

    private String insertedText;  // так как текста будет много, придётся переводить его в потоке

    /** insert text from source code */
    @Override
    public void insertText() {
        // тут будет функционал Евгения для извлечения комментов, доков из исходников

    }

    /** translate inserted text */
    @Override
    public void translateText() {
        // тут будет походу мой - для перевода текста будем использовать API GoogleTranslate

    }
}
