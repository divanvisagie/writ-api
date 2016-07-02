package com.writ.api.modules

import com.google.inject.{Provides, Singleton}
import com.swissguard.user.thriftscala.UserService
import com.twitter.finagle.ThriftMux
import com.twitter.finagle.stats.DefaultStatsReceiver
import com.twitter.finagle.zipkin.thrift.ZipkinTracer
import com.twitter.inject.TwitterModule
import com.twitter.util.Future

object UserServiceModule extends TwitterModule {

  val inet = sys.env.getOrElse(
    "SWISSGUARD_HOST",
    "localhost:8888,localhost:7777"
  )

  val receiver = DefaultStatsReceiver.get

  val zipkinHost = sys.env.getOrElse("ZIPKIN_HOST","localhost")
  val zipkinPort = sys.env.getOrElse("ZIPKIN_PORT", "9410").toInt


  val tracer = ZipkinTracer.mk(
    host = zipkinHost,
    port = zipkinPort,
    statsReceiver = receiver,
  )
  @Singleton @Provides
  def providesUserServiceClient(): UserService[Future] =
    ThriftMux.client
    .withTracer(tracer)
    .withStatsReceiver(receiver)
    .newIface[UserService.FutureIface](inet)
}
