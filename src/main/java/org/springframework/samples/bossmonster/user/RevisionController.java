/*

package org.springframework.samples.bossmonster.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.bossmonster.util.AuditDTO;
import org.springframework.samples.bossmonster.util.UserRevisionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class RevisionController {


    UserRevisionService userRevisionService;
    UserService userService;

    private static final String AUDIT_VIEW="/users/auditUsers";

    @Autowired
    public RevisionController(UserRevisionService userRevisionService, UserService userService){
        this.userRevisionService = userRevisionService;
        this.userService = userService;
    }


    @GetMapping("/admin/audit")
    public ModelAndView showAuditions(){
        ModelAndView result = new ModelAndView(AUDIT_VIEW);
        List<User> usuarios = userService.findAllUsers();
        List<AuditDTO> revision = new ArrayList<>();
        for (User usuario: usuarios){
            revision.addAll(userRevisionService.getRevisionHistory(usuario.getUsername()));
        }
        result.addObject("revision", revision);
        return result;
    }
}

*/
