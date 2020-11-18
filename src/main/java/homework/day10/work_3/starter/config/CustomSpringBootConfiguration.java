package homework.day10.work_3.starter.config;

import homework.day10.work_3.starter.dmo.Klass;
import homework.day10.work_3.starter.dmo.School;
import homework.day10.work_3.starter.dmo.Student;
import homework.day10.work_3.starter.props.StudentProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.ArrayList;

@Slf4j
@Configuration
@ComponentScan("homework.day10.work_3.starter")
@EnableConfigurationProperties(StudentProperties.class)
@ConditionalOnProperty(prefix = "spring.custom.student", name = "enabled", havingValue = "true", matchIfMissing = true)
@RequiredArgsConstructor
public class CustomSpringBootConfiguration {

    @Resource
    private StudentProperties studentProperties;

    @Bean("student100")
    @ConditionalOnMissingBean(name = "student100")
    public Student student() {
        Student student = new Student();
        student.setId(studentProperties.getId());
        student.setName(studentProperties.getName());
        log.info("----- init student : id : {}, name : {}", student.getId(), student.getName());
        return student;
    }

    @Bean
    @ConditionalOnBean(name = {"student100"})
    @ConditionalOnMissingBean(name = "klass")
    public Klass klass(Student student100) {
        ArrayList<Student> students = new ArrayList<>();
        students.add(student100);

        Klass klass = new Klass();
        log.info("----- init klass -----");
        klass.setStudents(students);
        log.info("----- klass accept students -----");
        return klass;
    }

    @Bean
    @ConditionalOnBean(name = {"klass"})
    @ConditionalOnMissingBean(name = "school")
    public School school(Klass klass) {
        School school = new School();
        log.info("----- init school -----");
        school.setClass1(klass);
        log.info("----- school accept klass -----");
        return school;
    }
}
