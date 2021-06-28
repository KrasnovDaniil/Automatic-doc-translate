package classes;

import classes.interfaces.IDownloaderService;
import classes.interfaces.IReaderService;
import classes.interfaces.IValidatorService;
import classes.interfaces.IWriterService;

import java.util.List;

public class MainService {
    private IDownloaderService downloaderService;
    private IReaderService readerService;
    private IValidatorService validatorService;
    private IWriterService writerService;

    public MainService (IDownloaderService downloaderService, IReaderService readerService,
                        IValidatorService validatorService, IWriterService writerService){
        this.downloaderService = downloaderService;
        this.readerService = readerService;
        this.validatorService = validatorService;
        this.writerService = writerService;
    }

    public void Run(String url, String source){
        if(this.validatorService.validate(url)) {
            this.downloaderService.Download(url);
            StringBuffer code = this.readerService.Read(source);
            System.out.println(code);
            this.writerService.Write(source, "C:\\Users\\User\\Desktop\\newProject.zip");
        }
        else{
            System.out.println("Invalid url");
        }
    }
}
