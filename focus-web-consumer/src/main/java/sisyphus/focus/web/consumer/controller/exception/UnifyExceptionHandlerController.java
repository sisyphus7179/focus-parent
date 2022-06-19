package sisyphus.focus.web.consumer.controller.exception;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sisyphus.focus.web.consumer.entity.Person;

import static sisyphus.focus.web.consumer.helper.ExceptionHandleHelper.getIndexOutOfBoundsException;

@RestController
public class UnifyExceptionHandlerController {

    @GetMapping("/unifyException")
    public Person findPersonById() {
        return getIndexOutOfBoundsException();
    }

}
