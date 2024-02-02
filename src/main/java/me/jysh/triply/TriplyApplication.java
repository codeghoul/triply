package me.jysh.triply;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "me.jysh.triply.repository")
@EntityScan(basePackages = "me.jysh.triply.entity")
public class TriplyApplication {

  public static void main(String[] args) {
    SpringApplication.run(TriplyApplication.class, args);
  }

}
