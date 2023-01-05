package org.springframework.samples.bossmonster.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class AdminOptionsController {

    private static final String ADMIN_OPTIONS="/adminOptions";

    @GetMapping("/adminOptions")
    public ModelAndView adminOptionsView(){  
        return new ModelAndView(ADMIN_OPTIONS);
    }

}

