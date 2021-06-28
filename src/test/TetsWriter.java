package test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class TetsWriter {

    public static final String FILE = "C:\\Users\\User\\Desktop\\newProject.zip";

    public static void main(String[] args) throws IOException {
        ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(FILE));
        Enumeration enumeration = new ZipFile("C:\\Users\\User\\Desktop\\project.zip").entries();
        createZipDir(zipOutputStream, enumeration);
        zipOutputStream.close();
    }

    private static void createZipDir(ZipOutputStream zipOutputStream, Enumeration enumeration) throws IOException {
        while (enumeration.hasMoreElements()){
            ZipEntry entry = (ZipEntry) enumeration.nextElement();
            if(!entry.isDirectory()) {
                ZipEntry newEntry = new ZipEntry(entry.getName());
                zipOutputStream.putNextEntry(newEntry);
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        new ZipFile("C:\\Users\\User\\Desktop\\project.zip").getInputStream(entry)));
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        line += "\n";
                        zipOutputStream.write(line.getBytes(StandardCharsets.UTF_8));
                    }
                }
            }
        }
    }
