package sisyphus.focus.web.consumer.controller.exception.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * @ControllerAdvice and @ExceptionHandler will effect to all Exception.
 * only @ExceptionHandler in controller priority than @ControllerAdvice and @ExceptionHandler.
 * created by sisyphus on 2022/6/7
 */
@ControllerAdvice
public class GlobalNPEHandler {

    @ExceptionHandler({NullPointerException.class})
    public ModelAndView exceptionHandle(Exception ex) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error");
        modelAndView.addObject("error", ex.toString());
        System.out.println(ex.toString());
        System.out.println(GlobalNPEHandler.class.toString());
        return modelAndView;
    }

}
