package classes;

import classes.interfaces.IReaderService;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipReader implements IReaderService {

    @Override
    public StringBuffer Read(String source){
        StringBuffer code = new StringBuffer();
        ZipFile zipFile = null;
        try{
            zipFile = new ZipFile(source);
            searchCodeFile(zipFile, code);
        }catch (IOException e){
            e.printStackTrace();
        }
        return code;
    }

    private void searchCodeFile(ZipFile zipFile, StringBuffer code) throws IOException {
        Enumeration enumeration = zipFile.entries();
        while (enumeration.hasMoreElements()) {
            ZipEntry entry = (ZipEntry) enumeration.nextElement();
            if (!entry.isDirectory() && entry.getName().contains(".java")) {
                readAllLines(zipFile, entry, code);
            }
        }
    }

    private void readAllLines(ZipFile zipFile, ZipEntry entry, StringBuffer code) throws IOException {
        String line = null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(zipFile.getInputStream(entry)));
        while ((line = reader.readLine()) != null) {
            code.append(line).append("\n");
        }
    }
}
