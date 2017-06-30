package gobblin.r2;

import java.util.HashSet;
import java.util.Set;

import com.linkedin.r2.message.rest.RestRequest;
import com.linkedin.r2.message.rest.RestResponse;
import lombok.extern.slf4j.Slf4j;

import gobblin.net.Request;
import gobblin.net.Response;
import gobblin.http.ResponseHandler;
import gobblin.http.StatusType;
import gobblin.utils.HttpUtils;


/**
 * Basic logic to handle a {@link RestResponse} from a restli service
 *
 * <p>
 *   A more specific handler understands the content inside the response and is able to customize
 *   the behavior as needed. For example: parsing the entity from a get response, extracting data
 *   sent from the service for a post response, executing more detailed status code handling, etc.
 * </p>
 */
@Slf4j
public class R2RestResponseHandler implements ResponseHandler<RestRequest, RestResponse> {

  public static final String CONTENT_TYPE_HEADER = "Content-Type";
  protected final Set<String> errorCodeWhitelist;

  public R2RestResponseHandler() {
    this(new HashSet<>());
  }

  public R2RestResponseHandler(Set<String> errorCodeWhitelist) {
    this.errorCodeWhitelist = errorCodeWhitelist;
  }

  @Override
  public R2ResponseStatus handleResponse(Request<RestRequest> request, Response<RestResponse> response) {
    RestResponse rawResponse = response.getRawResponse();
    R2ResponseStatus status = new R2ResponseStatus(StatusType.OK);
    int statusCode = rawResponse.getStatus();
    status.setStatusCode(statusCode);

    HttpUtils.updateStatusType(status, statusCode, errorCodeWhitelist);

    if (status.getType() == StatusType.OK) {
      status.setContent(rawResponse.getEntity());
      status.setContentType(rawResponse.getHeader(CONTENT_TYPE_HEADER));
    } else {
      log.info("Receive an unsuccessful response with status code: " + statusCode);
    }

    return status;
  }
}
