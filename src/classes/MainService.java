package classes;

import classes.interfaces.IDownloaderService;
import classes.interfaces.IValidatorService;
import classes.interfaces.IWriterService;

public class MainService {
    private IDownloaderService downloaderService;
    private IValidatorService validatorService;
    private IWriterService writerService;

    public MainService (IDownloaderService downloaderService,
                        IValidatorService validatorService, IWriterService writerService){
        this.downloaderService = downloaderService;
        this.validatorService = validatorService;
        this.writerService = writerService;
    }

    public void Run(String url, String source){
        if(this.validatorService.validate(url)) {
            this.downloaderService.Download(url);
            this.writerService.Write(source, ".\\newProject.zip");
        }
        else{
            System.out.println("Invalid url");
        }
    }
}
