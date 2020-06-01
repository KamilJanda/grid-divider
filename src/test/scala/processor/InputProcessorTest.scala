package processor

import grid.{Available, Cell, Coordinates, GridArea}
import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.collection.mutable

class InputProcessorTest extends AnyFlatSpec with Matchers with BeforeAndAfterEach {

  val processor = new InputProcessor

  it should "process input file" in {
    val numberOfGrids = 2
    val inputWidth    = 10
    val inputHeight   = 1

    val gridWidth = inputWidth / numberOfGrids

    val inputFile =
      InputFile(
        (0 until inputWidth).map(x => (0 until inputHeight).map(y => Cell(Coordinates(x, y), Available))).toList.flatten,
        inputWidth,
        inputHeight
      )

    val expectedResult: Seq[GridArea] = Seq(
      GridArea(
        1,
        mutable.Map() ++ (0 until gridWidth).map(x => Cell(Coordinates(x, 0), Available)).map(cell => cell.coordinates -> cell).toMap
      ),
      GridArea(
        2,
        mutable.Map() ++ (0 until gridWidth).map(x => Cell(Coordinates(x, 0), Available)).map(cell => cell.coordinates -> cell).toMap
      )
    )

    val result: Seq[GridArea] = processor.process(inputFile, numberOfGrids)

    result.head.cells.values should contain theSameElementsAs expectedResult.head.cells.values
  }

}
