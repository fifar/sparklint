/*
 Copyright 2016 Groupon, Inc.
 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at
 http://www.apache.org/licenses/LICENSE-2.0
 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
*/
package com.groupon.sparklint.data.lossless

import com.groupon.sparklint.data._
import org.apache.spark.scheduler.SparkListenerApplicationStart
import org.apache.spark.scheduler.TaskLocality.TaskLocality

/**
  * @author rxue
  * @since 9/22/16.
  */
case class LosslessState(appStart: Option[SparkListenerApplicationStart],
                         coreUsage: Map[TaskLocality, LosslessMetricsSink],
                         executorInfo: Map[String, SparklintExecutorInfo],
                         stageMetrics: Map[StageIdentifier, LosslessStageMetrics],
                         stageIdLookup: Map[Int, StageIdentifier],
                         runningTasks: Map[Long, SparklintTaskInfo],
                         firstTaskAt: Option[Long],
                         applicationEndedAt: Option[Long],
                         lastUpdatedAt: Long) extends SparklintStateLike {

  def appId: Option[String] = appStart.flatMap(_.appId)

  def appName: Option[String] = appStart.map(_.appName)

  def user: Option[String] = appStart.map(_.sparkUser)

  def applicationLaunchedAt: Option[Long] = appStart.map(_.time)
}

object LosslessState {
  def empty: LosslessState = {
    new LosslessState(None, Map.empty, Map.empty, Map.empty, Map.empty, Map.empty, None, None, 0L)
  }
}