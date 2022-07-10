package focus.spring.config;

import focus.spring.entity.Person;
import focus.spring.entity.Product;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;
import org.springframework.core.type.AnnotatedTypeMetadata;

// @ComponentScan(basePackages = {"focus.spring"})
// @ComponentScan(basePackages = {"focus.spring"}, excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Service.class})})
// @ComponentScan(basePackages = {"focus.spring"}, useDefaultFilters = false, includeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Service.class})})
@Configuration
@Import({Product.class})
public class ExcludeFilterConfig {

    static class MyCondition implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            return context.getEnvironment().getProperty("os.name").contains("Windows");
        }
    }

    @Bean
    // @Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    @Scope(scopeName = ConfigurableBeanFactory.SCOPE_SINGLETON)
    @Lazy(value = false)
    public Person person() {
        System.out.println("ioc start initialize person..........");
        return new Person("sisyphus", 32);
    }

    @Bean("uu")
    @Conditional({MyCondition.class})
    public Person person1() {
        return new Person("uu", 32);
    }

}
