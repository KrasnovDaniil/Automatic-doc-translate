package classes;

import classes.interfaces.IDownloaderService;
import classes.interfaces.IValidatorService;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class ZipDownloader implements IDownloaderService {
    @Override
    public void Download(String source){
        ReadableByteChannel readableByteChannel = null;
        FileOutputStream fileOutputStream = null;
        InputStream urlInputStream = null;
        try {
            urlInputStream = new URL(source).openStream();
        } catch (IOException e) {
            System.out.println("Invalid url");
            e.printStackTrace();
        }
        try {
            readableByteChannel = Channels.newChannel(urlInputStream);
            fileOutputStream = new FileOutputStream("C:\\Users\\User\\Desktop\\project.zip");
            fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
            System.out.println("File downloaded!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                urlInputStream.close();
                readableByteChannel.close();
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
