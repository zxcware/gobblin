package gobblin.net;

import lombok.Getter;
import lombok.Setter;


public class SimpleRequest<RQ> implements Request<RQ> {
  @Getter
  @Setter
  private RQ rawRequest;
}
