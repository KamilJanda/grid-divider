package grid

import scala.math.Ordered.orderingToOrdered

case class Coordinates(x: Int, y: Int) extends Ordered[Coordinates] {

  override def compare(that: Coordinates): Int = (this.x, this.y) compare (that.x, that.y)

  override def toString: String =
    s"($x,$y)"
}

case class Cell(coordinates: Coordinates, state: CellState) extends Ordered[Cell] {
  override def compare(that: Cell): Int = this.coordinates compare that.coordinates
}

sealed trait CellState

case object Available   extends CellState
case object Unavailable extends CellState
case object Indivisible extends CellState
