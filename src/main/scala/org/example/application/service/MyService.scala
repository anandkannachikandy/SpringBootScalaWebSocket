package org.example.application.service

import org.example.application.model.{MyRequest, MyResponse}


class MyService {
  def processRequest(request: MyRequest): MyResponse = {
    MyResponse(s"Request received from ${request.name}: ${request.id} ")
  }
}
