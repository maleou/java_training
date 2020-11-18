package homework.day10.work_3.starter.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "spring.custom.student")
public class StudentProperties {
    private Integer id;
    private String name;
}
