package classes;

import classes.interfaces.IDownloaderService;
import classes.interfaces.IReaderService;
import classes.interfaces.IValidatorService;

import java.util.List;

public class MainService {
    private IDownloaderService downloaderService;
    private IReaderService readerService;
    private IValidatorService validatorService;

    public MainService (IDownloaderService downloaderService, IReaderService readerService,
                        IValidatorService validatorService){
        this.downloaderService = downloaderService;
        this.readerService = readerService;
        this.validatorService = validatorService;
    }

    public void Run(String url, String source){
        if(this.validatorService.validate(url)) {
            this.downloaderService.Download(url);
            StringBuffer code = this.readerService.Read(source);
            System.out.println(code);
        }
        else{
            System.out.println("Invalid url");
        }
    }
}
