package org.springframework.samples.bossmonster.configuration;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
/*
@Controller
public class CustomErrorController {
    
    @ExceptionHandler
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
// get error status
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
// display specific error page
        if (statusCode == HttpStatus.NOT_FOUND.value()) {
            return "errors/error-404";
        } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
            return "errors/error-500";
        } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
            return "errors/error-403";
        }
        }
// display generic error
        return "error";
    }


}

*/