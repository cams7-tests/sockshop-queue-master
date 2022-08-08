package works.weave.socks.queuemaster;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import works.weave.socks.shipping.entities.Shipment;

@RequiredArgsConstructor
@Log4j2
@Component
public class ShippingTaskHandler {
  //  private final DockerSpawner docker;

  @RabbitListener(queues = "${queuemaster.rabbitmq.queue}")
  public void handleMessage(@Payload Shipment shipment) {
    log.info("Received shipment task: {}", shipment);
    //    docker.init();
    //    docker.spawn();
  }
}
