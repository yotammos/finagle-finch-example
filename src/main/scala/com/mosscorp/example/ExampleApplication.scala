package com.mosscorp.example

import com.mosscorp.example.models.Message
import com.mosscorp.example.services.ExampleService
import com.twitter.app.Flag
import com.twitter.finagle.Http
import com.twitter.server.TwitterServer
import com.twitter.util.Await
import com.twitter.util.logging.Logging
import io.finch.syntax.{get, post}
import io.finch.{Application, Endpoint, InternalServerError, Ok, jsonBody}
import io.finch.circe._
import io.circe.generic.auto._

object ExampleApplication extends TwitterServer with Logging {
  private val port: Flag[Int] = flag("port", 8081, "TCP port for HTTP server")

  private val exampleService = new ExampleService

  def hello: Endpoint[Message] = get("hello") {
    exampleService.getMessage().map(Ok)
  }

  private def acceptedMessage: Endpoint[Message] = jsonBody[Message]

  def accept: Endpoint[Message] = post("accept" :: acceptedMessage) { incomingMessage: Message =>
    exampleService.acceptMessage(incomingMessage).map(Ok)
  }

  private val api = (hello :+: accept).handle {
    case e: Exception => InternalServerError(e)
  }

  def main(): Unit = {
     logger.info(s"Serving the application on port ${port()}")

    val server =
      Http.server
        .withStatsReceiver(statsReceiver)
        .serve(s":${port()}", api.toServiceAs[Application.Json])
    closeOnExit(server)

    Await.ready(adminHttpServer)
  }
}
