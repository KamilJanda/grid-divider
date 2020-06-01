package processor

import grid.{Available, Cell, GridArea, Indivisible, Unavailable}

import scala.collection.mutable

case class InputFile(inputCells: Seq[Cell], width: Int, height: Int)

class InputProcessor {

  def process(inputFile: InputFile, numberOfGrids: Int): Seq[GridArea] = {
    val grids = (1 to numberOfGrids).toList.map(id => GridArea(id, mutable.Map.empty))

    val gridWidth  = inputFile.width / numberOfGrids
    val gridHeight = inputFile.height / numberOfGrids

    inputFile
      .inputCells
      .foreach(cell => grids(cell.coordinates.x / gridWidth).cells.put(cell.coordinates, cell))

    grids
  }

}
