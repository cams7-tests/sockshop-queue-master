package works.weave.socks.queuemaster;

import java.util.concurrent.CountDownLatch;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Getter
@Log4j2
@Component
public class Receiver<T> {

  private T message;
  private CountDownLatch latch;

  public Receiver() {
    super();
    latch = new CountDownLatch(1);
  }

  public void receiveMessage(T message) {
    this.message = message;
    log.info("Received message: {}", message);

    latch.countDown();
  }
}
