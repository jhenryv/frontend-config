package vuce2.mfeconfig;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(
        scanBasePackages = {
                "vuce2.library.config.swagger",
                "vuce2.library.config.mongo",
                //#################################//
                "vuce2.library.common.config.*",
                //#################################//
                "vuce2.mfeconfig"
        },
        exclude = {
                DataSourceAutoConfiguration.class
        }
)
@Slf4j
public class App {

    public static void main(String[] args) {
        try {
            SpringApplication.run(App.class, args);
            log.info("=================================================================");
            log.info("Application Started...");
            log.info("=================================================================");
        } catch (Exception ex) {
            log.error("Error occurred while starting Application");
        }
    }
}
