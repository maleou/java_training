package homework.day09.work_2.config;

import homework.day09.work_2.component.AutowireByBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource(locations = "classpath:spring/application.xml")
public class AutowireConfig {

    @Bean
    public AutowireByBean getAutowireByBean() {
        return new AutowireByBean();
    }
}
