package sjtu.chenzf.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.Calendar;

@Configuration
public class BeanConfigs {

    /**
     * 将该方法的返回值交给Spring Boot管理
     * 在工厂中默认标识：类名首字母小写
     */
    @Bean  // (name = "calendar")
    @Scope("prototype")  // 将对象构建成多例。默认是singleton，以单例模式创建
    public Calendar getCalendar() {
        // 实例化
        return Calendar.getInstance();
    }
}
