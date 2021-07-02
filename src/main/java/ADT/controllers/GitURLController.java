package ADT.controllers;

import ADT.services.implementations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("")
public class GitURLController {

    private TranslateServiceImpl translateService;
    private Finder finder;

    @Autowired
    public GitURLController(TranslateServiceImpl translateService, Finder finder) {
        this.translateService = translateService;
        this.finder = finder;
    }

    @GetMapping("")
    public String func(){
        System.out.println("here");
        return "index";
    }

    @PostMapping("/FindAndTranslate")
        public String translateGitRepoDocs(@ModelAttribute("githubUrl") String githubUrl,
                                           @ModelAttribute("login") String login,
                                           @ModelAttribute("password") String password,
                                           Model model){
//        String fileName = "C:\\Users\\Daniil\\Desktop\\refactoredGUI\\src\\main\\java\\ADT\\textSamples";
//        Map<String, Integer> stats = new HashMap<String, Integer>();
        TranslateServiceImpl translateService = new TranslateServiceImpl("en", "ru");
        Finder finder = new Finder(translateService);

        new GitMainService(new GitEmulator(login, password),
                new ValidatorUrl(), new ProjectWriter(translateService, finder))
                .Run(githubUrl,"C:\\Users\\Daniil\\Desktop\\test");

//        stats = finder.insertTranslation(fileName);
        System.out.println("Translation complete!");
        model.addAttribute("pr_link", "https://github.com/KrasnovDaniil");
        model.addAttribute("time_spent", "");
        model.addAttribute("comments_amount", "");
        return "success";
    }




}
