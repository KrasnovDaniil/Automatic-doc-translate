package ADT.services.implementations;

import ADT.services.interfaces.IValidatorService;
import java.io.InputStream;
import org.apache.commons.validator.routines.UrlValidator;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public class ValidatorUrl implements IValidatorService {

    @Override
    public boolean validate(String source) {
        boolean valid = false;
        UrlValidator urlValidator = new UrlValidator();
        if(urlValidator.isValid(source) && tryOpenStream(source))
            valid = true;
        return valid;
    }

    private boolean tryOpenStream(String source) {
        boolean valid = true;
        try(InputStream urlInputStream = new URL(source).openStream()) {
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            valid = false;
            e.printStackTrace();
        }
        return valid;
    }
}