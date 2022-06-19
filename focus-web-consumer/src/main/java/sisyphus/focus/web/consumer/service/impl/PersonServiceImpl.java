package sisyphus.focus.web.consumer.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sisyphus.focus.web.consumer.entity.Person;
import sisyphus.focus.web.consumer.service.IPersonService;

@Slf4j
@Service
public class PersonServiceImpl implements IPersonService {
    @Override
    public void savePerson(Person person) {
        log.info(person.toString());
    }
}
