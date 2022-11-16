package org.springframework.samples.bossmonster.statistics;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/statistics/s")
public class Controller {

    private Service service;

    private final String S_LISTING_VIEW="/s/sListing";
    private final String S_FORM="/s/createOrUpdateForm";

    @Autowired
    public Controller(Service s){
        this.service=s;
    }

    @GetMapping("/")
    public ModelAndView show(){
        ModelAndView result= new ModelAndView(S_LISTING_VIEW);
        result.addObject("s", service.getAlls());
        return result;
    }

    @GetMapping("/{id}/delete")
    public ModelAndView delete(@PathVariable int Id){
        service.deleteById(Id);
        // ModelAndView result= new ModelAndView(S_LISTING_VIEW);
        // result.addObject("message", "El logro se ha borrado con éxito");
        return show();
    }

    @GetMapping("/{id}/edit")
    public ModelAndView edit(@PathVariable int Id){
        service.getById(Id);
        ModelAndView result= new ModelAndView(S_FORM);
        result.addObject("", );
        // result.addObject("message", "El logro se ha actualizado con éxito");
        return result;
    }
    @PostMapping("/{id}/edit")
    public ModelAndView save(@PathVariable int id,  ){
         ToBeUpdated= service.getById(id);
        BeanUtils.copyProperties(, ToBeUpdated,"id");
        service.save(ToBeUpdated);
        return show();
    }


}
