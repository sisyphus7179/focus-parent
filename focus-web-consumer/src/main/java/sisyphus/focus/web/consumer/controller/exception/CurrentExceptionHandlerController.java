package sisyphus.focus.web.consumer.controller.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import sisyphus.focus.web.consumer.entity.Person;
import sisyphus.focus.web.consumer.helper.ExceptionHandleHelper;

@RestController
public class CurrentExceptionHandlerController {

    @GetMapping("/npe")
    public Person findPersonById() {
        return ExceptionHandleHelper.getNPEException();
    }

    @ExceptionHandler({NullPointerException.class})
    public ModelAndView exceptionHandle(Exception ex) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error");
        modelAndView.addObject("error", ex.toString());
        System.out.println(ex.toString());
        System.out.println(CurrentExceptionHandlerController.class.toString());
        return modelAndView;
    }

}
