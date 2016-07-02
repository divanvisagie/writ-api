package com.writ.api.controllers

import javax.inject.Inject

import com.twitter.finatra.http.Controller
import com.writ.api.domain.http.{LoginRequest, RegisterUserRequest}
import com.writ.api.services.AuthenticationService

class AuthenticationController @Inject()(authenticationService: AuthenticationService)
  extends Controller {

  post("/register") { request: RegisterUserRequest =>
    authenticationService.createUser(request) handle {
      case _: Exception => response.conflict("User already exists")
    }
  }

  post("/login") { request: LoginRequest =>
    authenticationService.login(request) handle {
      case _: Exception => response.unauthorized("Invalid login details")
    }
  }
}
