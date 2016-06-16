package com.writ.api.controllers

import javax.inject.Inject

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import com.writ.api.services.PingService


class PingController @Inject()(pingService: PingService) extends Controller {

  get("/ping") { request: Request =>
  	info("ping")
    "pong"
  }
}
