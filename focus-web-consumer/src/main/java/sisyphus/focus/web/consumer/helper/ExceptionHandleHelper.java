package sisyphus.focus.web.consumer.helper;

import sisyphus.focus.web.consumer.entity.Person;

import java.util.HashMap;
import java.util.Map;

public class ExceptionHandleHelper {

    public static Person getNPEException() {
        Person person = new Person(null, 18);
        // java.lang.NullPointerException
        person.getName().charAt(8);
        return person;
    }

    public static Person getIndexOutOfBoundsException() {
        Person sisyphus = new Person("sisyphus", 18);
        // java.lang.StringIndexOutOfBoundsException
        sisyphus.getName().charAt(8);
        return sisyphus;
    }

    public static Person getCastException() {
        Person person = new Person("sisyphus", 18);
        // java.lang.ClassCastException
        Map<String, Object> map = new HashMap<>();
        map.put("name", person.getName());
        person = (Person) map.get("name");
        return person;
    }

}
