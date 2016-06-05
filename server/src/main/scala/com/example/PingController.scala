package com.example

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import com.twitter.util.Future

import com.example.PingServiceClient


class PingController extends Controller {

  get("/ping") { request: Request =>
  	info("ping")
    val service = new PingServiceClient()
    service.svc.ping()
  }
}
