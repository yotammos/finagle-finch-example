package com.mosscorp.example

import java.nio.charset.StandardCharsets

import com.mosscorp.example.models.Message
import com.twitter.io.Buf
import io.finch.{Application, Input}
import org.scalatest.{FlatSpec, Matchers}

class ExampleSpec extends FlatSpec with Matchers {

  import com.mosscorp.example.ExampleApplication._

  behavior of "the hello endpoint"

  it should "get 'Hello, world!'" in {
    hello(Input.get("/hello")).awaitValueUnsafe() shouldBe Some(Message("Hello, world!"))
  }

  behavior of "the accept endpoint"

  it should "post our message" in {
    val input = Input.post("/accept")
      .withBody[Application.Json](Buf.Utf8("{\"message\":\"heres some post\"}"), Some(StandardCharsets.UTF_8))
    val res = accept(input)
    res.awaitValueUnsafe() shouldBe Some(Message("heres some post"))
  }
}
