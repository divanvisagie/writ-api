package com.writ.api.services


import com.swissguard.user.thriftscala.UserService
import com.twitter.finagle._
import com.twitter.finagle.stats.NullStatsReceiver
import com.twitter.finagle.tracing._
import com.twitter.util.Future


class UserServiceClient {
  val client: UserService[Future] = ThriftMux.client
    .withTracer(NullTracer)
    .withStatsReceiver(NullStatsReceiver)
    .newIface[UserService.FutureIface]("localhost:8888")
}
