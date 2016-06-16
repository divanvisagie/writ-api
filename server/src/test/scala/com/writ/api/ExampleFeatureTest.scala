package com.writ.api

import com.twitter.finagle.http.Status.Ok
import com.twitter.finatra.http.test.EmbeddedHttpServer
import com.twitter.finatra.thrift.thriftscala.ClientErrorCause.BadRequest
import com.twitter.inject.Mockito
import com.twitter.inject.server.FeatureTest

import com.twitter.finatra.json.JsonDiff._

class ExampleFeatureTest extends FeatureTest {

  override val server = new EmbeddedHttpServer(new ExampleServer)

  "Server" should {
    "ping" in {
      server.httpGet(
        path = "/ping",
        andExpect = Ok,
        withBody = "pong")
    }
  }
}


class LoginFeatureTest extends FeatureTest with Mockito {
  override val server = new EmbeddedHttpServer(new ExampleServer)

  "/register" should {
    "return token" in {

      server.httpPost(
        path = "/login",
        postBody =
          """
          {
            "username": "bob",
            "password" : "bobby123",
            "email" : "joe@soap.com"
          }
          """,
        andExpect = Ok,
        withJsonBody =
          """
          {"id":1,"passthrough_fields":{},"token":"token-from-thrift","username":"bob"}
          """
      )

    }
  }
}