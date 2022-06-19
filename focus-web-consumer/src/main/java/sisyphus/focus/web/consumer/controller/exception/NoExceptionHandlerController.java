package sisyphus.focus.web.consumer.controller.exception;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sisyphus.focus.web.consumer.entity.Person;
import sisyphus.focus.web.consumer.helper.ExceptionHandleHelper;

@RestController
public class NoExceptionHandlerController {

    @GetMapping("/noNPE")
    public Person findPersonById() {
        return ExceptionHandleHelper.getNPEException();
    }

    @GetMapping("/cc")
    public Person findPersonByName() {
        // System.out.println("test ClassCastException");
        return ExceptionHandleHelper.getCastException();
    }

}
