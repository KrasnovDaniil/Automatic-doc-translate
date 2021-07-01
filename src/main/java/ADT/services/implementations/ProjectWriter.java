package ADT.services.implementations;

import ADT.services.interfaces.IWriterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class ProjectWriter implements IWriterService {
    @Autowired
    private TranslateServiceImpl translateService;
    @Autowired
    private Finder finder;


    public ProjectWriter() {
    }

    public ProjectWriter(TranslateServiceImpl translateService, Finder finder) {
        this.translateService = translateService;
        this.finder = finder;
    }

    @Override
    public void Write(String source) {
        try {
            searchJavaFiles(new File(source).listFiles(), "");
        } catch (IOException e){
            e.printStackTrace();
            System.out.println("File not found");
        }
    }

    private void searchJavaFiles(File[] listFiles, String path) throws IOException {
        for(File file:listFiles){
            if(file.isDirectory()){
                String pppp = file.getPath();
                searchJavaFiles(file.listFiles(), file.getAbsolutePath()
                        + File.pathSeparator);
            }
            else{
                if(file.getName().contains(".java")){
                    translateComments(file.getPath());
                }
            }
        }
    }

    private void translateComments(String path) {
        //первод комментов
        Map<String, Integer> stats = new HashMap<String, Integer>();
        stats = this.finder.insertTranslation(path);
        System.out.println();
    }
}