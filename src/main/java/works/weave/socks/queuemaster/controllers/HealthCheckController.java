package works.weave.socks.queuemaster.controllers;

import com.rabbitmq.client.Channel;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.ChannelCallback;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import works.weave.socks.queuemaster.entities.HealthCheck;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping
public class HealthCheckController {

  private static final String STATUS_OK = "OK";
  private static final String QUEUE_MASTER = "queue-master";
  private static final String QUEUE_MASTER_RABBITMQ = "queue-master-rabbitmq";
  private static final String VERSION = "version";
  private static final String STATUS_ERROR = "err";
  private static final String HEALTH = "health";

  private final RabbitTemplate rabbitTemplate;

  @GetMapping(value = "/health")
  Map<String, List<HealthCheck>> getHealth() {
    Map<String, List<HealthCheck>> map = new HashMap<String, List<HealthCheck>>();
    List<HealthCheck> healthChecks = new ArrayList<HealthCheck>();
    Date dateNow = Calendar.getInstance().getTime();

    HealthCheck app = new HealthCheck(QUEUE_MASTER, STATUS_OK, dateNow);
    HealthCheck rabbitmq = new HealthCheck(QUEUE_MASTER_RABBITMQ, STATUS_OK, dateNow);

    try {
      this.rabbitTemplate.execute(
          new ChannelCallback<String>() {
            @Override
            public String doInRabbit(Channel channel) throws Exception {
              Map<String, Object> serverProperties = channel.getConnection().getServerProperties();
              return (String) serverProperties.get(VERSION);
            }
          });
    } catch (AmqpException e) {
      rabbitmq.setStatus(STATUS_ERROR);
      log.error(e.getMessage());
    }

    healthChecks.add(app);
    healthChecks.add(rabbitmq);

    map.put(HEALTH, healthChecks);
    return map;
  }
}
