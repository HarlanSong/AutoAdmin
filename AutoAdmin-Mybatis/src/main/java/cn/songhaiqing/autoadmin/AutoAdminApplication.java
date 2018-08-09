package cn.songhaiqing.autoadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("cn.songhaiqing.autoadmin.mapper")
public class AutoAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutoAdminApplication.class, args);
	}
}
