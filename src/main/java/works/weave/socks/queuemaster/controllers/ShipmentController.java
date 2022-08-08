package works.weave.socks.queuemaster.controllers;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import works.weave.socks.shipping.entities.Shipment;

@RequiredArgsConstructor
@RestController
@RequestMapping
public class ShipmentController {

  @Value("${queuemaster.rabbitmq.topicexchange}")
  private String exchangeName;

  @Value("${queuemaster.rabbitmq.queue}")
  private String queueName;

  private final RabbitTemplate rabbitTemplate;

  @PostMapping(value = "/shipment", consumes = APPLICATION_JSON_VALUE)
  String producer(@RequestBody Shipment shipment) {
    rabbitTemplate.convertAndSend(exchangeName, queueName, shipment);

    return "Message sent to the RabbitMQ Successfully";
  }
}
