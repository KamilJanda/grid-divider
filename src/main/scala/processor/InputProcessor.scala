package processor

import grid.{Available, Cell, GridArea, Indivisible, Unavailable}

case class InputFile(inputCells: Seq[Cell], width: Int, height: Int)

class InputProcessor {

  val numberOfGrids = 4

  def process(inputFile: InputFile): Seq[GridArea] = {
    val grids = (1 to numberOfGrids).toList.map(id => GridArea(id, Map.empty))

    val gridWidth  = inputFile.width / numberOfGrids
    val gridHeight = inputFile.height / numberOfGrids

    inputFile.inputCells.foreach { cell => }

  }

}
