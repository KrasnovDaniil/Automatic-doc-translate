package jgit.classes;

import jgit.classes.interfaces.IRepoCreatorService;
import zip.classes.interfeces.IValidatorService;
import zip.classes.interfeces.IWriterService;

public class jGItMainService {
    private IRepoCreatorService repoCreatorService;
    private IValidatorService validatorService;
    private IWriterService writerService;

    public jGItMainService(IRepoCreatorService repoCreatorService,
                           IValidatorService validatorService,
                           IWriterService writerService){
        this.repoCreatorService = repoCreatorService;
        this.validatorService = validatorService;
        this.writerService = writerService;
    }

    public void Run(String url, String source){
        if(this.validatorService.validate(url)){
            this.repoCreatorService.CreateRepo(url, source);
            this.writerService.Write(source);
        }
        else {
           System.out.println("Invalid url");
        }
    }
}
