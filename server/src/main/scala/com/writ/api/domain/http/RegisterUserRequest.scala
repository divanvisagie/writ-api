package com.writ.api.domain.http

import com.swissguard.user.thriftscala.UserRequest
import com.twitter.finatra.validation.Size


case class RegisterUserRequest(
  @Size(min = 2, max = 100) username: String,
  email: String,
  password: String) {

  def toThrift = {
    UserRequest(
      username = username,
      password = password
    )
  }
}

case class LoginRequest(
  @Size(min = 2, max = 100) username: String,
  password: String
) {
  def toThrift = {
    UserRequest(
      username = username,
      password = password
    )
  }
}
