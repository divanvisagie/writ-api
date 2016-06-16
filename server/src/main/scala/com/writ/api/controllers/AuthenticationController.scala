package com.writ.api.controllers

import javax.inject.Inject

import com.twitter.finatra.http.Controller
import com.writ.api.domain.http.{LoginRequest, RegisterUserRequest}
import com.writ.api.services.UserService

class AuthenticationController @Inject()(userService: UserService)
  extends Controller {

  post("/register") { request: RegisterUserRequest =>
    userService createUser request
  }

  post("/login") { request: LoginRequest =>
    userService login request handle {
      case _: Exception => response.status(401)
    }
  }
}
