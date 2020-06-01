package grid

import scala.collection.mutable

case class GridArea(id: Int, cells: mutable.Map[Coordinates, Cell])
