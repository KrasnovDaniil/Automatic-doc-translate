package ADT.controllers;

import ADT.services.implementations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("git")
public class GitURLController {

    private TranslateServiceImpl translateService;
    private Finder finder;

    @Autowired
    public GitURLController(TranslateServiceImpl translateService, Finder finder) {
        this.translateService = translateService;
        this.finder = finder;
    }

    @GetMapping("/{url}")
    public String func(@PathVariable(name = "url") String url){
        System.out.println("url");
        return "index";
    }

    @PostMapping("/FindAndTranslate")
    public String translateGitRepoDocs(Model model){
//        String fileName = "C:\\Users\\Daniil\\Desktop\\refactoredGUI\\src\\main\\java\\ADT\\textSamples";
//        Map<String, Integer> stats = new HashMap<String, Integer>();
        TranslateServiceImpl translateService = new TranslateServiceImpl("en", "ru");
        Finder finder = new Finder(translateService);

        new GitMainService(new GitEmulator("KrasnovDaniil", "HarrisTeeter12"),
                new ValidatorUrl(), new ProjectWriter(translateService, finder))
                .Run("https://github.com/KrasnovDaniil/Automatic-doc-translate", "C:\\Users\\Daniil\\Desktop\\test");
//        stats = finder.insertTranslation(fileName);
        System.out.println("Translation complete!");
        return "index";
    }




}
