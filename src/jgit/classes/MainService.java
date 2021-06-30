package jgit.classes;

import jgit.classes.interfaces.IRepoCreatorService;
import zip.classes.interfeces.IValidatorService;

public class MainService {
    private IRepoCreatorService repoCreatorService;
    private IValidatorService validatorService;

    public MainService(IRepoCreatorService repoCreatorService,
                       IValidatorService validatorService){
        this.repoCreatorService = repoCreatorService;
        this.validatorService = validatorService;
    }

    public void Run(String url){
        if(this.validatorService.validate(url)){
            this.repoCreatorService.CreateRepo(url);
        }
        else {
            System.out.println("Invalid url");
        }
    }
}
