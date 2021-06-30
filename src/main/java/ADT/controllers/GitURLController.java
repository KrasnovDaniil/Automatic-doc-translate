package ADT.controllers;

import ADT.services.implementations.Finder;
import ADT.services.implementations.TranslateServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.Map;

@Controller
public class GitURLController {

    private TranslateServiceImpl translateService;
    private Finder finder;

    @Autowired
    public GitURLController(TranslateServiceImpl translateService, Finder finder) {
        this.translateService = translateService;
        this.finder = finder;
    }

    @GetMapping("git_repo/{URL}")
    public String translateGitRepoDocs(@PathVariable(name = "URL") String repoURL){
        String fileName = "C:\\Users\\Daniil\\Desktop\\refactoredGUI\\src\\main\\java\\ADT\\textSamples";
        Map<String, Integer> stats = new HashMap<String, Integer>();

        stats = finder.insertTranslation(fileName);
        System.out.println("Translation complete!");
        return "mainMenue";
    }


}
