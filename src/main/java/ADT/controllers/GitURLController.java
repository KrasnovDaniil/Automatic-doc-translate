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
        TranslateServiceImpl translateService = new TranslateServiceImpl("en", "ru");
        Finder finder = new Finder(translateService);
        ProjectWriter projectWriter = new ProjectWriter(translateService, finder);
        GitEmulator gitEmulator = new GitEmulator(login, password);

        long start_time = System.currentTimeMillis();

        new GitMainService(gitEmulator,new ValidatorUrl(), projectWriter)
                .Run(githubUrl,"C:\\Users\\Daniil\\Desktop\\test");

        long finish_time = System.currentTimeMillis();
// статистика = finder.insertTranslation (fileName);
        System.out.println("Translation complete!");
        model.addAttribute("pr_link", gitEmulator.getBranchLink());
        model.addAttribute("time_spent", (finish_time-start_time)/1000);
        model.addAttribute("comments_amount", projectWriter.getTotal_comments());
        return "success";
    }




}
