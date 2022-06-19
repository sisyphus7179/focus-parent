package sisyphus.focus.web.consumer.service.impl;

import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl {

    private void saveStudent(Student student) {
        System.out.println("saveStudent start...");
        actualSaveStudent(student);
        System.out.println("saveStudent end...");
    }

    private void actualSaveStudent(Student student) {
        System.out.println(student);
        System.out.println("actualSaveStudent executing...");
    }

}

class Student {

    private String name;

    public Student(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                '}';
    }

}
