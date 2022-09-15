package cn.dayutec.pigeon;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = {"cn.dayutec.pigeon.**.mapper"})
public class CasesApplication {

    public static void main(String[] args) {
        SpringApplication.run(CasesApplication.class, args);
    }

}
