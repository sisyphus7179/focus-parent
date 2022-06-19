package sisyphus.focus.web.consumer.controller.exception.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class GlobalCCHandler implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        log.error(GlobalCCHandler.class.getSimpleName(), e);
        ModelAndView modelAndView = new ModelAndView();
        if (e instanceof ClassCastException) {
            modelAndView.setViewName("error");
            modelAndView.addObject("error", e.toString());
        }
        return modelAndView;
    }

}
