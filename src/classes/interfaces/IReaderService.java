package classes.interfaces;

import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public interface IReaderService {
    List<String> Read(String source);
}
