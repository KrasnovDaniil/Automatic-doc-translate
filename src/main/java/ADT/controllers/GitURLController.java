package ADT.controllers;

import ADT.services.implementations.TranslateServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class GitURLController {

    private TranslateServiceImpl translateService;

    @GetMapping("git_repo/{URL}")
    public String translateGitRepoDocs(@PathVariable(name = "URL") String repoURL){
        String link_PR = "";
        System.out.println("Hello world");

        return "mainMenu";
    }


}
