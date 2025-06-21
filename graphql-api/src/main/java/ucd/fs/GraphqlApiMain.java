package ucd.fs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "ucd.fs.client")
public class GraphqlApiMain {
    public static void main(String[] args) {
        SpringApplication.run(GraphqlApiMain.class, args);
    }
}