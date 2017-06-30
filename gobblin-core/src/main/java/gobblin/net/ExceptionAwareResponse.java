package gobblin.net;

import lombok.Getter;
import lombok.Setter;

import gobblin.net.SimpleResponse;


public class ExceptionAwareResponse<RP> extends SimpleResponse<RP> {
  @Getter
  @Setter
  private Throwable throwable;

  @Override
  public RP getRawResponse() {
    return null;
  }
}
