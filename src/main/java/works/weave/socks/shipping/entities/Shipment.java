package works.weave.socks.shipping.entities;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Shipment {
  private String id;
  private String name;

  public Shipment(String name) {
    this(UUID.randomUUID().toString(), name);
  }
}
