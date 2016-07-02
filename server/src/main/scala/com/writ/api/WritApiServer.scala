package com.writ.api

import com.twitter.finagle.http.{Request, Response}
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.filters.{CommonFilters, LoggingMDCFilter, TraceIdMDCFilter}
import com.twitter.finatra.http.routing.HttpRouter
import com.writ.api.controllers.{AuthenticationController, PingController}
import com.writ.api.modules.UserServiceModule

object WritApiServerMain extends WritApiServer

class WritApiServer extends HttpServer {

  override def defaultFinatraHttpPort = ":9999"

  override def modules = Seq(UserServiceModule)

  override def configureHttp(router: HttpRouter) {
    router
      .filter[LoggingMDCFilter[Request, Response]]
      .filter[TraceIdMDCFilter[Request, Response]]
      .filter[CommonFilters]
      .add[PingController]
      .add[AuthenticationController]
  }

}
