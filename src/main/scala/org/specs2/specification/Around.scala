package org.specs2
package specification

trait Around {
  def around[T <: Result](t: =>T): T
  def apply[T <: Result](a: =>T) = around(a)
}
