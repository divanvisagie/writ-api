package com.writ.api

import com.google.inject.testing.fieldbinder.Bind
import com.twitter.finagle.http.Status._
import com.twitter.finatra.http.test.EmbeddedHttpServer
import com.twitter.inject.Mockito
import com.twitter.inject.server.FeatureTest
import com.twitter.util.Future
import com.writ.api.domain.http.LoginRequest
import com.writ.api.domain.http.Response.LoginResponse
import com.writ.api.services.UserService

class LoginFeatureTest extends FeatureTest with Mockito {
  override val server = new EmbeddedHttpServer(new ExampleServer)

  @Bind val userService = smartMock[UserService]

  val loginResponse = LoginResponse("token-from-mock")


  "/login" should {
    "return fail if wrong password" in {
      userService.login(LoginRequest("jane","wrongpass")) returns
        Future.exception(new Exception("Invalid Password"))

      server.httpPost(
        path = "/login",
        postBody =
          """
          {
            "username": "jane",
            "password" : "wrongpass"
          }
          """,
        andExpect = Unauthorized
      )

    }
  }


  "/login with correct password" should {
    "return token" in {

      userService.login(LoginRequest("bob","bobby123")) returns
        Future(loginResponse)


      server.httpPost(
        path = "/login",
        postBody =
          """
          {
            "username": "bob",
            "password" : "bobby123"
          }
          """,
        andExpect = Ok,
        withJsonBody =
          """
          {"token":"token-from-mock"}
          """
      )

    }
  }
}
