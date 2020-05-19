package processor

import grid.{Available, Cell, Coordinates, GridArea}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.BeforeAndAfterEach
import org.scalatest.matchers.should.Matchers

class InputProcessorTest extends AnyFlatSpec with Matchers with BeforeAndAfterEach {

  val processor = new InputProcessor

  it should "process input file" in {
    val inputFile = List(Cell(Coordinates(1, 1), Available))

    val result = processor.process(???)

    result shouldBe List(GridArea("1", Map(Coordinates(1, 1) -> Cell(Coordinates(1, 1), Available))))
  }

}
