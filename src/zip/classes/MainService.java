package zip.classes;

import zip.classes.interfeces.IDownloaderService;
import zip.classes.interfeces.IValidatorService;
import zip.classes.interfeces.IWriterService;

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
