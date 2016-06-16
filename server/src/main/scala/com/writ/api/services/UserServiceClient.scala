package com.writ.api.services

import javax.inject.Singleton

import com.swissguard.user.thriftscala.UserService
import com.twitter.finagle._
import com.twitter.finagle.stats.NullStatsReceiver
import com.twitter.finagle.tracing._
import com.twitter.util.Future
import com.writ.api.domain.http.Response.LoginResponse
import com.writ.api.domain.http.{LoginRequest, RegisterUserRequest}

@Singleton
class UserServiceClient {
  private val client: UserService[Future] = ThriftMux.client
    .withTracer(NullTracer)
    .withStatsReceiver(NullStatsReceiver)
    .newIface[UserService.FutureIface]("localhost:8888")

  def createUser(user: RegisterUserRequest) = client.createUser(user.toThrift)

  def login(user: LoginRequest): Future[LoginResponse] =
    client.login(user.toThrift) map { userResponse =>
      LoginResponse fromThrift userResponse
    }

}
