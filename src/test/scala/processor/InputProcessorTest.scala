package processor

import grid.{Available, Cell, Coordinates, Indivisible, Unavailable}
import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.collection.mutable

class InputProcessorTest extends AnyFlatSpec with Matchers with BeforeAndAfterEach {

  val processor = new InputProcessor

  it should "process input file" in {
    val numberOfGrids = 2
    val inputWidth    = 20
    val inputHeight   = 1

//    val gridWidth = inputWidth / numberOfGrids

    val inputFile =
      InputFile(
        (0 until 2).map(x => (0 until inputHeight).map(y => Cell(Coordinates(x, y), Unavailable))).toList.flatten ++
        (2 until 8).map(x => (0 until inputHeight).map(y => Cell(Coordinates(x, y), Available))).toList.flatten ++
        (8 until 15).map(x => (0 until inputHeight).map(y => Cell(Coordinates(x, y), Indivisible))).toList.flatten ++
        (15 until inputWidth).map(x => (0 until inputHeight).map(y => Cell(Coordinates(x, y), Available))).toList.flatten,
        inputWidth,
        inputHeight
      )

    val expectedResult: Seq[GridArea] = Seq(
      GridArea(
        1,
        mutable.Map() ++
        (0 until 2).map(x => Cell(Coordinates(x, 0), Unavailable)).map(cell => cell.coordinates -> cell).toMap ++
        (2 until 8).map(x => Cell(Coordinates(x, 0), Available)).map(cell => cell.coordinates   -> cell).toMap
      ),
      GridArea(
        2,
        mutable.Map() ++
        (8 until 15).map(x => Cell(Coordinates(x, 0), Indivisible)).map(cell => cell.coordinates        -> cell).toMap ++
        (15 until inputWidth).map(x => Cell(Coordinates(x, 0), Available)).map(cell => cell.coordinates -> cell).toMap
      )
    )

    val result: Seq[GridArea] = processor.process(inputFile, numberOfGrids)

    println(s"result grid 1: ${result.head}")
    println(s"result grid 2: ${result(1)}")

    result.head.cells.values should contain theSameElementsAs expectedResult.head.cells.values
  }

}
