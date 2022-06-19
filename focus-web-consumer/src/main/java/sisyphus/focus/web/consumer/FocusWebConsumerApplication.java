package sisyphus.focus.web.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FocusWebConsumerApplication {
    public static void main(String[] args) {
        System.out.println("FocusWebConsumerApplication running");
        SpringApplication.run(FocusWebConsumerApplication.class);
    }
}
