package shogi

import Pos._

class PlayOneMoveTest extends ShogiTest {

  "playing a move" should {
    "only process things once" in {
      makeGame(shogi.variant.Standard).playMoves((SQ7G, SQ7F, false)) must beValid
    }
  }
}
