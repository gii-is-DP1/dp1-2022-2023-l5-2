package org.springframework.samples.bossmonster.configuration;

/**
 * This advice is necessary because MockMvc is not a real servlet environment, therefore it does not redirect error
 * responses to [ErrorController], which produces validation response. So we need to fake it in tests.
 * It's not ideal, but at least we can use classic MockMvc tests for testing error response + document it.
 */
//@ControllerAdvice
public class ExceptionHandlerConfiguration
{
	//@Autowired
	//private BasicErrorController errorController;
    // add any exceptions/validations/binding problems

   /*@ExceptionHandler(Exception.class)
   public String defaultErrorHandler(HttpServletRequest request,  Exception ex)  {
        request.setAttribute("javax.servlet.error.request_uri", request.getPathInfo());
        request.setAttribute("javax.servlet.error.status_code", 400);
        request.setAttribute("exeption", ex);
        return "exception";
    }
 
    @ExceptionHandler(Exception.class)
    public String defaultErrorHandler(HttpServletRequest request,  Exception ex)  {
        if (ex instanceof NoHandlerFoundException) {
            return "errors/error-404";
        } else  {
            return "errors/error-500";
        }
    }
*/
}
