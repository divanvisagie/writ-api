package com.example

import com.example.ping.thriftscala.PingService
import com.twitter.finagle._
import com.twitter.finagle.param.{Tracer => PTracer}
import com.twitter.finagle.stats.NullStatsReceiver
import com.twitter.finagle.tracing._
import com.twitter.util.{Await, Future}

import scala.language.reflectiveCalls


class PingServiceClient {

  val svc: PingService[Future] = ThriftMux.client
      .configured(param.Tracer(NullTracer))
      .configured(param.Stats(NullStatsReceiver))
      .newIface[PingService.FutureIface]("localhost:8888")
}
