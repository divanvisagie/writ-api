package com.writ.api.domain.http.Response

import com.swissguard.user.thriftscala.UserResponse

object LoginResponse {
  def fromThrift(user: UserResponse) =
    LoginResponse(token = user.token)

}
case class LoginResponse (token: String)
