package onl.tran;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OnlineTransactionAppApplication {

 public static void main(String[] args) {
  SpringApplication.run(OnlineTransactionAppApplication.class, args);
 }

}
