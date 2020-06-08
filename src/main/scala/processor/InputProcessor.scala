package processor

import grid.{Cell, Coordinates, Indivisible, Unavailable}

import scala.collection.mutable

case class InputFile(inputCells: Seq[Cell], width: Int, height: Int)

case class GridArea(id: Int, cells: mutable.Map[Coordinates, Cell]) {
  def activeSize: Int                    = cells.filter(cell => cell._2.state != Unavailable).keys.size
  def firstCoordinateInGrid: Coordinates = cells.keys.toList.min
  def lastCoordinateInGrid: Coordinates  = cells.keys.toList.max

  override def toString: String =
    cells.values.toList.sorted.toString()
}

class InputProcessor {

  def process(inputFile: InputFile, numberOfGrids: Int): Seq[GridArea] = {
    val grids = (1 to numberOfGrids).toList.map(id => GridArea(id, mutable.Map.empty))

    val gridWidth  = inputFile.width / numberOfGrids
    val gridHeight = inputFile.height / numberOfGrids

    inputFile
      .inputCells
      .foreach(cell => grids(cell.coordinates.x / gridWidth).cells.put(cell.coordinates, cell))

    for {
      (first, second) <- grids zip grids.drop(1)
    } balanceGrids(first, second)

    for {
      (first, second) <- grids zip grids.drop(1)
    } moveIndivisibleArea(first, second)

    grids.map(grid => GridArea(grid.id, grid.cells))
  }

  def balanceGrids(firstGrid: GridArea, secondGrid: GridArea) =
    if (firstGrid.activeSize < secondGrid.activeSize) {
      val diff = (secondGrid.activeSize - firstGrid.activeSize) / 2

      for (i <- 0 until diff) {
        val column = secondGrid.cells.filter(entry => entry._1.x == secondGrid.firstCoordinateInGrid.x + i)
        println(s"balance column: $column")

        secondGrid.cells --= column.keys
        firstGrid.cells ++= column
      }

    } else if (firstGrid.activeSize > secondGrid.activeSize) {
      val diff = (firstGrid.activeSize - secondGrid.activeSize) / 2

      for (i <- 0 until diff) {
        val column = firstGrid.cells.filter(entry => entry._1.x == firstGrid.lastCoordinateInGrid.x - i)
        println(s"column: $column")

        firstGrid.cells --= column.keys
        secondGrid.cells ++= column
      }

    }

  private def moveIndivisibleArea(firstGrid: GridArea, secondGrid: GridArea) = {
    val indivisibleInLastColumn  = findIndivisibleInColumn(firstGrid, firstGrid.lastCoordinateInGrid.x)
    val indivisibleInFirstColumn = findIndivisibleInColumn(secondGrid, secondGrid.firstCoordinateInGrid.x)

    if (indivisibleInLastColumn.nonEmpty && indivisibleInFirstColumn.nonEmpty) {

      val firstIndivisibleArea: mutable.Map[Coordinates, Cell]  = mutable.Map.empty
      val secondIndivisibleArea: mutable.Map[Coordinates, Cell] = mutable.Map.empty
      var firstStartColumn                                      = firstGrid.lastCoordinateInGrid.x
      var secondStartColumn                                     = secondGrid.firstCoordinateInGrid.x

      do {
        firstIndivisibleArea ++= findIndivisibleInColumn(firstGrid, firstStartColumn)
        firstStartColumn -= 1
      } while (firstStartColumn >= firstGrid.firstCoordinateInGrid.x && findIndivisibleInColumn(firstGrid, firstStartColumn).nonEmpty)

      println(s"first Indivisible Area: $firstIndivisibleArea")

      do {
        secondIndivisibleArea ++= findIndivisibleInColumn(secondGrid, secondStartColumn)
        secondStartColumn += 1
      } while (secondStartColumn <= secondGrid.lastCoordinateInGrid.x && findIndivisibleInColumn(secondGrid, secondStartColumn).nonEmpty)

      println(s"second Indivisible Area: $secondIndivisibleArea")

      if (firstIndivisibleArea.nonEmpty && secondIndivisibleArea.nonEmpty) {
        if (cellsWidth(firstIndivisibleArea) > cellsWidth(secondIndivisibleArea)) {

          val diff            = cellsWidth(secondIndivisibleArea)
          val firstCoordinate = secondGrid.firstCoordinateInGrid.x

          for (i <- 0 until diff) {
            val column = secondGrid.cells.filter(entry => entry._1.x == firstCoordinate + i)
            println(s"column: $column")

            secondGrid.cells --= column.keys
            firstGrid.cells ++= column
          }
        } else if (cellsWidth(firstIndivisibleArea) < cellsWidth(secondIndivisibleArea)) {

          val diff           = cellsWidth(firstIndivisibleArea)
          val lastCoordinate = firstGrid.lastCoordinateInGrid.x

          for (i <- 0 until diff) {
            val column = firstGrid.cells.filter(entry => entry._1.x == lastCoordinate - i)
            println(s"column: $column")

            firstGrid.cells --= column.keys
            secondGrid.cells ++= column
          }
        }
      }

    }
  }

  private def cellsWidth(cells: mutable.Map[Coordinates, Cell]) =
    cells.values.toList.groupBy(cell => cell.coordinates.x).size

  def balanceIndivisible(firstGrid: GridArea, secondGrid: GridArea) =
    if (firstGrid.activeSize < secondGrid.activeSize) {
      val diff = (secondGrid.activeSize - firstGrid.activeSize) / 2

      for (i <- 0 until diff) {
        val column = secondGrid.cells.filter(entry => entry._1.x == secondGrid.firstCoordinateInGrid.x + i)
        println(s"column: $column")

        secondGrid.cells --= column.keys
        firstGrid.cells ++= column
      }

    }

  private def findIndivisibleInColumn(grid: GridArea, column: Int): mutable.Map[Coordinates, Cell] =
    grid.cells.filter(cell => cell._1.x == column && cell._2.state == Indivisible)

}
