package gobblin.net;


public interface Request<RQ> {
  RQ getRawRequest();
}
