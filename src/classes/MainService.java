package classes;

import classes.interfaces.IDownloaderService;
import classes.interfaces.IReaderService;

import java.util.List;

public class MainService {
    private IDownloaderService downloaderService;
    private IReaderService readerService;

    public MainService (IDownloaderService downloaderService, IReaderService readerService){
        this.downloaderService = downloaderService;
        this.readerService = readerService;
    }

    public void Run(String url, String source){
        this.downloaderService.Download(url);
        List<String> code = this.readerService.Read(source);
        for (String line: code){
            System.out.println(line);
        }
    }
}
