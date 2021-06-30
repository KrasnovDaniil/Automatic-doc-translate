package zip.classes;

import zip.classes.interfeces.IWriterService;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.zip.*;

public class ZipWriter implements IWriterService {
    public static final String DESTINATION = ".\\newProject.zip";

    @Override
    public void Write(String source) {
        try {
            createZipDir(source);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createZipDir(String source) throws IOException {
        ZipFile zipSrc = new ZipFile(source);
        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(DESTINATION));
        Enumeration enumeration = zipSrc.entries();
        if(isJavaProject(enumeration)) {
            while (enumeration.hasMoreElements()) {
                ZipEntry entrySrc = (ZipEntry) enumeration.nextElement();
                if (!entrySrc.isDirectory()) {
                    createNewEntry(zos, zipSrc, entrySrc);
                }
            }
        }
        else {
            System.out.println("It's not java project");
        }

        zos.close();
    }

    private boolean isJavaProject(Enumeration enumeration) {
        while (enumeration.hasMoreElements()){
            ZipEntry entry = (ZipEntry) enumeration.nextElement();
            if(!entry.isDirectory() && entry.getName().contains(".java")) {
                return true;
            }
        }

        return false;
    }

    private void createNewEntry(ZipOutputStream zos, ZipFile zipSrc, ZipEntry entrySrc)
            throws IOException {
        ZipEntry entryNew = new ZipEntry(entrySrc.getName());
        zos.putNextEntry(entryNew);
        InputStream zis = zipSrc.getInputStream(entrySrc);
        if(!entrySrc.getName().contains(".java")){
            copyToNewEntry(zis, zos);
        }
        else {
            //ищем комментарии в джава файле
            zos.write("code".getBytes(StandardCharsets.UTF_8));
        }

        //zis.close();
    }

    private void copyToNewEntry(InputStream zis, ZipOutputStream zos) throws IOException {
        byte[] buffer = new byte[1024];
        int read = 0;

        while((read = zis.read(buffer, 0, 1024)) != -1){
            zos.write(buffer, 0, read);
        }
    }
}
