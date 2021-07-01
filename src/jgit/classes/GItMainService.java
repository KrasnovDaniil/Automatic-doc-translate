package jgit.classes;

import jgit.classes.interfaces.IGitService;
import zip.classes.interfeces.IValidatorService;
import zip.classes.interfeces.IWriterService;

public class GItMainService {
    private IGitService gitService;
    private IValidatorService validatorService;
    private IWriterService writerService;

    public GItMainService(IGitService gitService,
                          IValidatorService validatorService,
                          IWriterService writerService){
        this.gitService = gitService;
        this.validatorService = validatorService;
        this.writerService = writerService;
    }

    public void Run(String url, String directory){
        if(this.validatorService.validate(url)){
            this.gitService.initRepoAndNewBranch(url, directory);
            this.writerService.Write(directory);
            this.gitService.commitAndPush(directory);
        }
        else {
           System.out.println("Invalid url");
        }
    }
}
