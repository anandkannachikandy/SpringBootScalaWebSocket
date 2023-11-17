package org.example.application.controller

import com.typesafe.scalalogging.LazyLogging
import org.example.application.model.{FailureResponse, MyRequest}
import org.example.application.service.MyService
import org.example.application.util.Util.mapper
import org.springframework.stereotype.Component
import org.springframework.web.socket.handler.AbstractWebSocketHandler
import org.springframework.web.socket.{BinaryMessage, CloseStatus, TextMessage, WebSocketSession}

import scala.util.{Failure, Success, Try}

@Component
class MyWebSocketHandler(service: MyService) extends AbstractWebSocketHandler with LazyLogging {

  override def afterConnectionEstablished(session: WebSocketSession): Unit = {
    logger.info(s"Connection established ${session.getRemoteAddress}:${session.getUri}")
  }

  override def handleBinaryMessage(session: WebSocketSession, message: BinaryMessage): Unit = {
    session.sendMessage(new TextMessage(s"Received binary message"))
  }

  override def handleTextMessage(session: WebSocketSession, message: TextMessage): Unit = {

    val request = Try(mapper.readValue[MyRequest](message.asBytes()))
    logger.info(s"Request received is: $request")
    request match {
      case Failure(exception) => logger.info(s"Failed to deserialize: ${exception.getMessage}")
        session.sendMessage(new TextMessage(mapper.writeValueAsString(FailureResponse(s"Failed to deserialize: ${exception.getMessage}"))))
      case Success(value) =>
        val response = service.processRequest(value)
        session.sendMessage(new TextMessage(mapper.writeValueAsString(response)))

    }
  }

  override def afterConnectionClosed(session: WebSocketSession, status: CloseStatus): Unit = {
    logger.info(s"Connection closed with ${status.getReason} for ${session.getRemoteAddress}:${session.getUri}")
  }
}
