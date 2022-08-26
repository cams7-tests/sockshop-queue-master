package works.weave.socks.queuemaster;

import static java.time.ZoneOffset.UTC;
import static java.util.TimeZone.getTimeZone;
import static java.util.TimeZone.setDefault;
import static org.springframework.boot.SpringApplication.run;

import javax.annotation.PostConstruct;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QueueMasterApplication {

  public static void main(String... args) {
    run(QueueMasterApplication.class, args);
  }

  @PostConstruct
  public void init() {
    setDefault(getTimeZone(UTC));
  }
}
