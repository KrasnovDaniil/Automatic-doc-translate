package ADT.controllers;

import ADT.services.implementations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("git_repo")
public class GitURLController {

    private TranslateServiceImpl translateService;
    private Finder finder;

    @Autowired
    public GitURLController(TranslateServiceImpl translateService, Finder finder) {
        this.translateService = translateService;
        this.finder = finder;
    }

    @GetMapping("/{URL}")
    public String translateGitRepoDocs(@PathVariable(name = "URL") String repoURL){
//        String fileName = "C:\\Users\\Daniil\\Desktop\\refactoredGUI\\src\\main\\java\\ADT\\textSamples";
//        Map<String, Integer> stats = new HashMap<String, Integer>();
        TranslateServiceImpl translateService = new TranslateServiceImpl("en", "ru");
        Finder finder = new Finder(translateService);

//        new GitMainService(new GitEmulator("KrasnovDaniil", "HarrisTeeter12"),
//                new ValidatorUrl(), new ProjectWriter(translateService, finder))
//                .Run("https://github.com/KrasnovDaniil/Automatic-doc-translate", "C:\\Users\\Daniil\\Desktop\\test");
//        stats = finder.insertTranslation(fileName);
        System.out.println("Translation complete!");
//        return "/mainMenu.html";
        return "index";
    }

    @GetMapping("")
    public void someFunc(){
        System.out.println("here");
    }


}
