package com.example

import sttp.tapir.server.ziohttp.{ZioHttpInterpreter, ZioHttpServerOptions}
import zio.{Console, LogLevel, Scope, ZIO, ZIOAppArgs, ZIOAppDefault, ZLayer}
import zio.http.{HttpApp, Server}
import zio.logging.LogFormat
import zio.logging.backend.SLF4J

object Main extends ZIOAppDefault {

  override val bootstrap: ZLayer[ZIOAppArgs, Any, Any] = SLF4J.slf4j(LogLevel.Debug, LogFormat.default)

  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = {
    val serverOptions: ZioHttpServerOptions[Any] =
      ZioHttpServerOptions.customiseInterceptors
        .metricsInterceptor(Endpoints.prometheusMetrics.metricsInterceptor())
        .options

    val app: HttpApp[Any, Throwable] = ZioHttpInterpreter(serverOptions).toHttp(Endpoints.all)

    val port = sys.env.get("HTTP_PORT").flatMap(_.toIntOption).getOrElse(8080)

    (
      for {
        actualPort <- Server.install(app.withDefaultErrorResponse)
        _          <- Console.printLine(s"Go to http://localhost:$actualPort/docs to open SwaggerUI. Press ENTER key to exit.")
        _          <- Console.readLine
      } yield ()
    ).provide(
      Server.defaultWithPort(port)
    ).exitCode
  }
}
