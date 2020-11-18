package homework.day09.work_2.component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AutowireByBean {

    public AutowireByBean() {
        log.info(this.getClass().getName() + "注解bean注入");
    }
}
