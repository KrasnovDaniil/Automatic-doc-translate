package ADT.services.implementations;

import ADT.services.interfaces.IGitService;
import ADT.services.interfaces.IValidatorService;
import ADT.services.interfaces.IWriterService;

public class GitMainService {
    private IGitService gitService;
    private IValidatorService validatorService;
    private IWriterService writerService;

    public GitMainService(IGitService gitService,
                          IValidatorService validatorService,
                          IWriterService writerService) {
        this.gitService = gitService;
        this.validatorService = validatorService;
        this.writerService = writerService;
    }

    public void Run(String url, String directory) {
        if (this.validatorService.validate(url)) {
            this.gitService.initRepoAndNewBranch(url, directory);
            this.writerService.Write(directory);
            this.gitService.commitAndPush(directory);
        } else {
            System.out.println("Invalid url");
        }
    }
}
