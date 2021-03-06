package com.jetprobe.core.structure

import com.jetprobe.core.action.Action
import com.jetprobe.core.http.HttpDSL

/**
  * @author Shad.
  */
trait StructureBuilder[B <: StructureBuilder[B]]
    extends Execs[B]
    with Feeds[B]
    with Ingests[B]
    with HttpDSL[B]
    with Pauses[B]
with Validations[B]{

  private[jetprobe] def build(ctx: ScenarioContext,
                              chainNext: Action): Action = {
    actionBuilders.foldLeft(chainNext) { (next, actionBuilder) =>
      actionBuilder.build(ctx, next)
    }
  }

}
