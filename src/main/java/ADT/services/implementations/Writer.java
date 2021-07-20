package ADT.services.implementations;

import ADT.models.SearchResult;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Writer {
    public void writeTranslation(String fileName, SearchResult result){
        Path path = Paths.get(fileName);
        try {
            String whole_text = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
// if (longComment.equals (result.getText (). replaceAll (&quot;\ r \ n&quot;, &quot;\ n&quot;))) {
// System.out.println (&quot;WOOOOOW&quot;);
//}
            String from = result.getText().replaceAll("\r\n","\n");
            String to = result.getTranslation();

            String after_replace = whole_text.replace(from, to);

            Files.write(path, after_replace.getBytes(StandardCharsets.UTF_8));
            System.out.println("Запись перевода в строке №" + result.getLineNumber() + " успешно выполнена");
        } catch(FileNotFoundException ex) {
            System.err.println("Unable to open file '" + fileName + "'. It probably doesn't exist or has a different name");
        } catch(IOException ex) {
            System.err.println("Error reading file '" + fileName + "'");
            System.err.println(ex.getMessage());
        }
    }
    private String longComment = "/**\n" +
            " * Some class for proper work\n" +
            " * {@code SourceCode class} purposes {@code true} for store source code\n" +
            " * @author Daniiil\n" +
            " * @version 1.1\n" +
            " */";
}
