package com.codebranch.akka.proxy


import akka.actor.{ActorRef, Props, Actor}
import akka.util.Timeout
import concurrent.duration._
import akka.event.LoggingReceive



/**
 * User: alexey
 * Date: 6/18/13
 * Time: 5:00 PM
 */

class ClusterNode(val role: Option[String] = None)
		extends Node with RandomSelector
{

	implicit val timeout: Timeout = 100 seconds

	def member = random


	/**
	 * Try to get key from message
	 * @param msg
	 * @return
	 */
	protected def extractKey(msg: Any): Option[String] = msg match {
		case (key: String, _) => Some(key)
		case _ => None
	}

	/**
	 * Create actual worker for key
	 * @return
	 */
	protected def createWorker(key: String): ActorRef =
		context.actorOf(Props[SimpleWorker], key)

	//  val path: String = "ClusterNode"
}


case class Msg(id: String, task: String = "work") extends ActorId {
	def actorId = id
}


class SimpleWorker extends Actor {
	var messageCount = 0

	def receive: Receive = LoggingReceive {
		case Msg(id, task) => {
			if(task == "restart")
				throw new Exception("I'm died")
			else if(task == "terminate")
				context.stop(self)
			else {
				messageCount += 1
				sender ! messageCount
			}
		}
	}
}



