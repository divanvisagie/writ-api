package com.writ.api.services

import javax.inject.{Inject, Singleton}

import com.swissguard.user.thriftscala.UserService
import com.twitter.util.Future
import com.writ.api.domain.http.Response.LoginResponse
import com.writ.api.domain.http.{LoginRequest, RegisterUserRequest}

@Singleton
class AuthenticationService @Inject()(client: UserService[Future]) {

  def createUser(user: RegisterUserRequest) = client.register(user.toThrift)

  def login(user: LoginRequest): Future[LoginResponse] =
    client.login(user.toThrift) map { token =>
      LoginResponse(token)
    }

}
