package classes;

import java.io.FileNotFoundException;
// import java.io.BufferedWriter;
// import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;
import java.nio.charset.*;

public class Writer {
	public void writeTranslation(String fileName, SearchResult result){
		Path path = Paths.get(fileName);
		try {
			Files.write(path, new String(Files.readAllBytes(path), StandardCharsets.UTF_8).replace(result.getText(), result.getTranslation()).getBytes(StandardCharsets.UTF_8));
			System.out.println("Запись перевода в строке №" + result.getLineNumber() + " успешно выполнена");
		} catch(FileNotFoundException ex) {
			System.err.println("Unable to open file '" + fileName + "'. It probably doesn't exist or has a different name");
		} catch(IOException ex) {
            System.err.println("Error reading file '" + fileName + "'");
            System.err.println(ex.getMessage());
        }
	}
}
