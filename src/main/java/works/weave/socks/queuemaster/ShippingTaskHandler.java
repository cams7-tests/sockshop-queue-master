package works.weave.socks.queuemaster;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import works.weave.socks.queuemaster.entities.Shipment;

@RequiredArgsConstructor
@Getter
@Component
public class ShippingTaskHandler {

  private final Receiver<Shipment> receiver;
  // private final DockerSpawner docker;

  @RabbitListener(queues = "${queuemaster.rabbitmq.queue}")
  public void handleMessage(@Payload Shipment shipment) {
    //  docker.init();
    //  docker.spawn();
    receiver.receiveMessage(shipment);
  }
}
