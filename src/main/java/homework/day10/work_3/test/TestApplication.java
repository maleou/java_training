package homework.day10.work_3.test;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
public class TestApplication implements CommandLineRunner {

    @Resource
    Starter starter;

    @Override
    public void run(String... args) {
        starter.run();
    }

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }
}
