package controller

import org.example.application.controller.MyWebSocketHandler
import org.example.application.model.{MyRequest, MyResponse}
import org.example.application.service.MyService
import org.example.application.util.Util.mapper
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.{times, verify, when}
import org.scalatest.BeforeAndAfter
import org.scalatest.funspec.AnyFunSpec
import org.scalatestplus.mockito.MockitoSugar.mock
import org.springframework.web.socket.{BinaryMessage, TextMessage, WebSocketSession}

import java.net.{InetSocketAddress, URI}

class MyWebSocketHandlerTest extends AnyFunSpec with BeforeAndAfter {

  var myService: MyService = _
  var myWebSocketHandler: MyWebSocketHandler = _
  var session: WebSocketSession = _


  before {
    myService = mock[MyService]
    myWebSocketHandler = new MyWebSocketHandler(myService)
    session = mock[WebSocketSession]
  }


  describe("MyWebSocketHandler") {
    it("send a successful text message back when correct request received") {
      val request = MyRequest("anand", 1)
      val requestJson = mapper.writeValueAsString(request)
      when(myService.processRequest(request)).thenReturn(MyResponse(s"Request received from ${request.name}: ${request.id} "))
      myWebSocketHandler.handleTextMessage(session, new TextMessage(requestJson))
      verify(myService, times(1)).processRequest(request)
      val argumentCaptor: ArgumentCaptor[TextMessage] = ArgumentCaptor.forClass(classOf[TextMessage])
      verify(session).sendMessage(argumentCaptor.capture())
      assert(argumentCaptor.getValue.getPayload.contains(MyResponse(s"Request received from ${request.name}: ${request.id} ").response))
    }
    it("send a successful text message back when an incorrect request received") {
      val requestJson = mapper.writeValueAsString("request")
      myWebSocketHandler.handleTextMessage(session, new TextMessage(requestJson))
      verify(myService, times(0)).processRequest(any())
      val argumentCaptor: ArgumentCaptor[TextMessage] = ArgumentCaptor.forClass(classOf[TextMessage])
      verify(session).sendMessage(argumentCaptor.capture())
      assert(argumentCaptor.getValue.getPayload.contains(s"Failed to deserialize: Cannot construct instance of"))
    }
    it("logs connection establishment") {
      when(session.getRemoteAddress).thenReturn(mock[InetSocketAddress])
      when(session.getUri).thenReturn(mock[URI])
      myWebSocketHandler.afterConnectionEstablished(session)
    }
    it("sends text on receipt of binary message") {
      val request = MyRequest("anand", 1)
      val requestJson = mapper.writeValueAsBytes(request)
      myWebSocketHandler.handleBinaryMessage(session, new BinaryMessage(requestJson))
      verify(session).sendMessage(new TextMessage("Received binary message"))
    }
  }

}
