package works.weave.socks.queuemaster;

import static br.com.six2six.fixturefactory.Fixture.from;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.apache.commons.lang3.ClassUtils.getPackageName;
import static org.assertj.core.api.Assertions.assertThat;
import static works.weave.socks.queuemaster.template.DomainTemplateLoader.SHIPMENT;

import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import works.weave.socks.queuemaster.entities.Shipment;
import works.weave.socks.queuemaster.template.DomainTemplateLoader;

@SpringBootTest
@Testcontainers
public class ShippingTaskHandlerTests {

  private static String EXCHANGE_NAME = "shipping-task-exchange";
  private static String QUEUE_NAME = "shipping-task";

  @Container
  static RabbitMQContainer rabbitContainer =
      new RabbitMQContainer("rabbitmq:3.9.22-management-alpine");

  @DynamicPropertySource
  static void configure(DynamicPropertyRegistry registry) {
    registry.add("spring.rabbitmq.host", rabbitContainer::getHost);
    registry.add("spring.rabbitmq.port", rabbitContainer::getAmqpPort);
  }

  @BeforeAll
  static void loadTemplates() {
    FixtureFactoryLoader.loadTemplates(getPackageName(DomainTemplateLoader.class));
  }

  @Autowired private RabbitTemplate rabbitTemplate;

  @Autowired private ShippingTaskHandler shippingTaskHandler;

  @Test
  void shouldReceiveMessageWhenSendShipment() throws InterruptedException {
    Shipment shipment = from(Shipment.class).gimme(SHIPMENT);

    var receiver = shippingTaskHandler.getReceiver();

    assertThat(receiver.getMessage()).isNull();

    rabbitTemplate.convertAndSend(EXCHANGE_NAME, QUEUE_NAME, shipment);
    receiver.getLatch().await(500, MILLISECONDS);

    assertThat(receiver.getMessage()).isEqualTo(shipment);
  }
}
