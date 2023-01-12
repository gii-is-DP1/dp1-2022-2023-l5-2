package org.springframework.samples.bossmonster.configuration;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorControler implements ErrorController{

    @GetMapping("/error")
    public ModelAndView handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        ModelAndView result = new ModelAndView();
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                result.setViewName("errors/error-404");
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                result.setViewName("errors/error-500");
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                result.setViewName("errors/error-403");
            } else{
                result.setViewName("errors/error");
            }
        }
        String referer = request.getHeader("Referer");
        result.addObject("previousUrl", referer);
        return result;
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
