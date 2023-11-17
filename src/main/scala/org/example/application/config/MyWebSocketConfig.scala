package org.example.application.config

import org.example.application.controller.MyWebSocketHandler
import org.springframework.beans.factory.BeanFactory
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.web.socket.config.annotation.{EnableWebSocket, WebSocketConfigurer, WebSocketHandlerRegistry}
import org.springframework.web.socket.handler.PerConnectionWebSocketHandler
import org.springframework.web.socket.server.standard.TomcatRequestUpgradeStrategy
import org.springframework.web.socket.server.support.{DefaultHandshakeHandler, HttpSessionHandshakeInterceptor}

@Configuration
@EnableWebSocket
class MyWebSocketConfig(beanFactory: BeanFactory) extends WebSocketConfigurer {
  override def registerWebSocketHandlers(registry: WebSocketHandlerRegistry): Unit = {

    val perConnectionWebSocketHandler = new PerConnectionWebSocketHandler(classOf[MyWebSocketHandler])
    perConnectionWebSocketHandler.setBeanFactory(beanFactory)
    registry
      .addHandler(perConnectionWebSocketHandler, "/demo/test/ws")
      .addInterceptors(new HttpSessionHandshakeInterceptor())
      .setHandshakeHandler(defaultHandshakeHandler())
  }

  @Bean
  private def defaultHandshakeHandler(): DefaultHandshakeHandler =
    new DefaultHandshakeHandler(new TomcatRequestUpgradeStrategy)
}
