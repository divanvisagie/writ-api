package com.writ.api.controllers

import javax.inject.Inject

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import com.writ.api.domain.http.RegisterUserRequest
import com.writ.api.services.{PingService, UserServiceClient}


class PingController @Inject()(pingService: PingService) extends Controller {

  get("/ping") { request: Request =>
  	info("ping")
    "pong"
  }
}

class AuthenticationController @Inject()(userServiceClient: UserServiceClient) extends Controller {
  post("/register") { request: RegisterUserRequest =>
    userServiceClient.client.createUser(
      request.toThrift
    )
  }
}
