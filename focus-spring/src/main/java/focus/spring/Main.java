package focus.spring;

import focus.spring.config.ExcludeFilterConfig;
import focus.spring.entity.Person;
import focus.spring.entity.Product;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ExcludeFilterConfig.class);
//        for (String beanDefinitionName : applicationContext.getBeanDefinitionNames()) {
//            System.out.println(beanDefinitionName);
//        }

//        System.out.println("ioc initialize start success");
//        Person bean1 = applicationContext.getBean(Person.class);
//        Person bean2 = applicationContext.getBean(Person.class);
//        System.out.println(bean1);
//        System.out.println(bean2);
//        Map<String, Person> beansOfType = applicationContext.getBeansOfType(Person.class);
//        beansOfType.forEach((k, v) -> {
//            System.out.println(k + ":" + v);
//        });
        Product product = applicationContext.getBean(Product.class);
        System.out.println(product);
        ExcludeFilterConfig ex = applicationContext.getBean(ExcludeFilterConfig.class);
        System.out.println(ex);
    }

}
