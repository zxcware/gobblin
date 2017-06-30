/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package gobblin.http;

import java.io.Closeable;
import java.io.IOException;

import gobblin.async.Callback;
import gobblin.net.Request;
import gobblin.net.Response;


/**
 * An interface to send a request
 *
 * @param <RQ> type of request
 * @param <RP> type of response
 */
public interface HttpClient<RQ, RP> extends Closeable {
  /**
   * Send request synchronously
   */
  Response<RP> sendRequest(Request<RQ> request) throws IOException;

  /**
   * Send request asynchronously
   */
  default void sendAsyncRequest(Request<RQ> request, Callback<Response<RP>> callback) throws IOException {
    throw new UnsupportedOperationException();
  }
}