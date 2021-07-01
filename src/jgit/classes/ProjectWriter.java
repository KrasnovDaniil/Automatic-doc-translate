package jgit.classes;

import zip.classes.interfeces.IWriterService;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class ProjectWriter implements IWriterService {
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
                searchJavaFiles(file.listFiles(), path + file.getName()
                        + File.pathSeparator);
            }
            else{
                if(file.getName().contains(".java")){
                    translateComments(path + file.getName());
                }
            }
        }
    }

    private void translateComments(String path) {
        //первод комментов
    }
}
