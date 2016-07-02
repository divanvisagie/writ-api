package com.writ.api.modules

import com.google.inject.{Provides, Singleton}
import com.swissguard.user.thriftscala.UserService
import com.twitter.finagle.ThriftMux
import com.twitter.finagle.stats.NullStatsReceiver
import com.twitter.finagle.tracing.NullTracer
import com.twitter.inject.TwitterModule
import com.twitter.util.Future

object UserServiceModule extends TwitterModule {

  val inet = sys.env.getOrElse("WRIT_API_SWISSGUARD", "localhost:8888")

  @Singleton @Provides
  def providesUserServiceClient(): UserService[Future] =
    ThriftMux.client
    .withTracer(NullTracer)
    .withStatsReceiver(NullStatsReceiver)
    .newIface[UserService.FutureIface](inet)
}
