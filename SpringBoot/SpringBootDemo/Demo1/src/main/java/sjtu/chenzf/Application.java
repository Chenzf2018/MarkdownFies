package sjtu.chenzf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication  // 组合注解：组合了@EnableAutoConfiguration和@ComponentScan

public class Application {
    public static void main(String[] args) {
        /**
         * SpringApplication为Spring的应用类，用来启动SpringBoot应用（部署到对应的web容器中）
         * 参数1：传入入口类的类对象；参数2为main函数的参数
         */
        SpringApplication.run(Application.class, args);
    }
}
