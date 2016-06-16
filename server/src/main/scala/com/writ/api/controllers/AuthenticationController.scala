package com.writ.api.controllers

import javax.inject.Inject

import com.twitter.finatra.http.Controller
import com.writ.api.domain.http.{LoginRequest, RegisterUserRequest}
import com.writ.api.services.UserServiceClient

class AuthenticationController @Inject()(userServiceClient: UserServiceClient)
  extends Controller {

  post("/register") { request: RegisterUserRequest =>
    userServiceClient createUser request
  }

  post("/login") { request: LoginRequest =>
    userServiceClient login request handle {
      case _: Exception => response.status(401)
    }
  }
}
