package homework.day10.work_3.test;

import homework.day10.work_3.starter.dmo.ISchool;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class Starter {

    @Resource
    ISchool school;

    public void run() {
        school.ding();
    }
}
