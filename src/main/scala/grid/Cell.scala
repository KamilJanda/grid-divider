package grid

case class Coordinates(x: Int, y: Int)

case class Cell(coordinates: Coordinates, state: CellState)

sealed trait CellState

case object Available   extends CellState
case object Unavailable extends CellState
case object Indivisible extends CellState
