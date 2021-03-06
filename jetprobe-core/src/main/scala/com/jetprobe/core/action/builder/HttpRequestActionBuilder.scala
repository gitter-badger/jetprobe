package com.jetprobe.core.action.builder

import com.jetprobe.core.action.{Action, HttpRequestAction}
import com.jetprobe.core.http.HttpRequestBuilder
import com.jetprobe.core.structure.ScenarioContext

/**
  * @author Shad.
  */
class HttpRequestActionBuilder(requestBuilder : HttpRequestBuilder) extends ActionBuilder{
  /**
    * @param ctx  the test context
    * @param next the action that will be chained with the Action build by this builder
    * @return the resulting action
    */
  override def build(ctx: ScenarioContext, next: Action): Action = new HttpRequestAction(requestBuilder,next)
}
