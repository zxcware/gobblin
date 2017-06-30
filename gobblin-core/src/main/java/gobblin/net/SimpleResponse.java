package gobblin.net;

import lombok.Getter;
import lombok.Setter;


public class SimpleResponse<RP> implements Response<RP> {
  @Getter
  @Setter
  private RP rawResponse;
}
