package org.example.application.util

import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.scala.{ClassTagExtensions, DefaultScalaModule}

object Util {

  val mapper: JsonMapper with ClassTagExtensions = JsonMapper.builder().addModule(DefaultScalaModule).addModule(new JavaTimeModule).build() :: ClassTagExtensions

}
