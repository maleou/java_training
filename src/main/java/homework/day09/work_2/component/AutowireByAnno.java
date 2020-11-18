package homework.day09.work_2.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Slf4j
@Configuration
@ComponentScan
public class AutowireByAnno {

    @PostConstruct
    public void init() {
        log.info(this.getClass().getName() + " ----- 启动");
    }
}
