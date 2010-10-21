package org.specs2
package reporter
import specification._

trait ExecutionStrategy {
  val execute: List[List[Fragment]] => List[ExecutedFragment] 
}
trait DefaultExecutionStrategy extends ExecutionStrategy with FragmentExecution {
  val execute = (fragments: List[List[Fragment]]) => {
    import scalaz._; import Scalaz._; import concurrent._
    import java.util.concurrent.Executors
    import Strategy.Executor
    implicit val executor = Executors.newFixedThreadPool(4)

    val result = fragments.map(l => l.map(f => promise(executeFragment(f))).sequence.get).flatten
    executor.shutdown()
    result
  }
  
}
trait SequentialExecutionStrategy extends ExecutionStrategy with FragmentExecution {
  val execute = (fragments: List[List[Fragment]]) => {
    fragments.map(l => l.map(f => executeFragment(f))).flatten
  }
  
}