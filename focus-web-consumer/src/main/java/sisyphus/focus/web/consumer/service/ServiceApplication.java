package sisyphus.focus.web.consumer.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class ServiceApplication {
    public static void main(String[] args) {
        System.out.println("ServiceApplication running");
        SpringApplication.run(ServiceApplication.class);
    }
}
