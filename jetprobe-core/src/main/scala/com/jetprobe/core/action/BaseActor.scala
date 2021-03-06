package com.jetprobe.core.action

import akka.actor.{Actor, PoisonPill, Terminated}
import com.jetprobe.core.session.Session
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.duration.Duration

/**
  * @author Shad.
  */

abstract class BaseActor extends Actor with LazyLogging {

  implicit def system = context.system
  def scheduler = system.scheduler
  implicit def dispatcher = system.dispatcher

  override def preStart(): Unit = context.setReceiveTimeout(Duration.Undefined)

  override def preRestart(reason: Throwable, message: Option[Any]): Unit =
    logger.error(s"Actor $this crashed on message $message", reason)


  override def unhandled(message: Any): Unit =
    message match {
      case Terminated(dead) => super.unhandled(message)
      case unknown          => throw new IllegalArgumentException(s"Actor $this doesn't support message $unknown")
    }

  override def postStop(): Unit = logger.debug(s"Stopping the actor ${self.path.name}")
}

class ActionDelegatorActor(next : Action, session : Session) extends BaseActor {
  override def receive: Receive = {
    case ExecuteCommand => next ! session
  }
}
case object ExecuteCommand