package sisyphus.focus.web.consumer.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import sisyphus.focus.web.consumer.entity.Person;
import sisyphus.focus.web.consumer.service.IPersonService;
import sisyphus.focus.web.consumer.service.ServiceApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {ServiceApplication.class})
public class PersonServiceImplTest {

    @Autowired
    public IPersonService personService;

    @Autowired StudentServiceImpl studentService;

    @Test
    public void savePerson() {
        personService.savePerson(new Person("sisyphus", 18));
    }

    @Test
    public void saveStudent() {
        studentService.saveStudent(new Student("sisyphus"));
    }

}