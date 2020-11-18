package homework.day09.work_2.component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AutowireByXml {

    public void init() {
        log.info(this.getClass().getName() + "xml扫描注入");
    }
}
