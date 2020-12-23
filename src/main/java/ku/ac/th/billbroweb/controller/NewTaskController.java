package ku.ac.th.billbroweb.controller;

import javafx.concurrent.Task;
import ku.ac.th.billbroweb.model.Captain;
import ku.ac.th.billbroweb.model.TaskParty;
import ku.ac.th.billbroweb.service.CaptainService;
import ku.ac.th.billbroweb.service.TaskPartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/newtask")
public class NewTaskController {
    @Autowired
    private TaskPartyService taskPartyService;

    @Autowired
    private CaptainService captainService;

    @ModelAttribute("taskParty")
    public TaskParty getTaskparty(){
        return new TaskParty();
    }

    @GetMapping
    public String getNewTask(Authentication authentication){
        System.out.println(authentication.getName());
        return "NewTask";
    }


    @PostMapping
    public  String posttoAddTask(@RequestParam("famount") int a,@ModelAttribute TaskParty taskParty,Authentication authentication, RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("u", a);
        redirectAttributes.addFlashAttribute("captainobj", captainService.findCaptain(authentication.getName()));
        taskParty.setC_id(captainService.findCaptain(authentication.getName()).getC_id());
        redirectAttributes.addFlashAttribute("taskpartyobj", taskParty);
        taskParty.setT_state("a");
        System.out.println(taskParty);
        taskPartyService.openTaskParty(taskParty);
        return "redirect:/addcrewmate";
    }
}
