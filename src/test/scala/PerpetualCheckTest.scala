package shogi

import Pos._
import format.forsyth.Sfen
import format.usi.Usi

class PerpetualCheckTest extends ShogiTest {

  "Perpetual check" should {
    val g = makeGame(shogi.variant.Standard).playMoves(
      (SQ5G, SQ5F, false),
      (SQ5C, SQ5D, false),
      (SQ2H, SQ5H, false),
      (SQ5A, SQ5B, false),
      (SQ5F, SQ5E, false),
      (SQ5B, SQ5C, false),
      (SQ5H, SQ5F, false),
      (SQ5C, SQ6D, false)
    )
    val m = List(
      (SQ5F, SQ6F, false),
      (SQ6D, SQ7D, false),
      (SQ6F, SQ7F, false),
      (SQ7D, SQ6D, false),
      (SQ7F, SQ6F, false),
      (SQ6D, SQ7D, false),
      (SQ6F, SQ7F, false),
      (SQ7D, SQ6D, false),
      (SQ7F, SQ6F, false),
      (SQ6D, SQ7D, false),
      (SQ6F, SQ7F, false),
      (SQ7D, SQ6D, false),
      (SQ7F, SQ6F, false)
    )
    val mi = List(
      (SQ5F, SQ6F, false),
      (SQ6D, SQ7D, false),
      (SQ6F, SQ7F, false),
      (SQ7D, SQ6D, false),
      (SQ7F, SQ6F, false),
      (SQ6D, SQ7D, false),
      (SQ6F, SQ7F, false),
      (SQ7D, SQ6D, false),
      (SQ7I, SQ6H, false),
      (SQ3A, SQ4B, false),
      (SQ6H, SQ7I, false),
      (SQ4B, SQ3A, false),
      (SQ7F, SQ6F, false),
      (SQ6D, SQ7D, false),
      (SQ6F, SQ7F, false),
      (SQ7D, SQ6D, false),
      (SQ7F, SQ6F, false)
    )
    "not trigger" in {
      "after 2 repetitions" in {
        g must beValid.like { case game =>
          game.playMoveList(m take 5) must beValid.like { case game2 =>
            game2.situation.draw must beFalse
            game2.situation.repetition must beFalse
            game2.situation.perpetualCheck must beFalse
            game2.situation.winner must beNone
          }
        }
      }
      "after 3 repetitions" in {
        g must beValid.like { case game =>
          game.playMoveList(m take 9) must beValid.like { case game2 =>
            game2.situation.draw must beFalse
            game2.situation.repetition must beFalse
            game2.situation.perpetualCheck must beFalse
            game2.situation.winner must beNone
          }
        }
      }
      "if the checks weren't consecutive" in {
        g must beValid.like { case game =>
          game.playMoveList(mi) must beValid.like { case game2 =>
            game2.situation.draw must beFalse
            game2.situation.repetition must beTrue
            game2.situation.perpetualCheck must beFalse
            game2.situation.winner must beNone
          }
        }
      }
      "5fPJUs7r" in {
        val g = sfenToGame(
          Sfen("ln1skg1nl/1r1sg2b1/ppp1GSppp/3p1p3/9/9/PPPPPPPPP/1B5R1/LN1GK2NL b Sp 1"),
          shogi.variant.Standard
        ).toOption.get
        val moves = List(
          "4c5b+", // 1st
          "6a5b",
          "S*4c",
          "S*6a",
          "4c5b+", // 2nd
          "6a5b",
          "S*4c",
          "S*6a",
          "4c5b+", // 3rd
          "6a5b",
          "S*4c",
          "S*6a",
          "4c5b+" // 4th
        ).map(u => Usi(u).get)
        g.playUsiMoveList(moves) must beValid.like { case game =>
          game.situation.draw must beFalse
          game.situation.repetition must beTrue
          game.situation.perpetualCheck must beFalse
          game.situation.winner must beNone
        }
      }
    }
    "trigger" in {
      def isPerpetualWith(g: Game, winner: Color): Boolean =
        !g.situation.draw &&
          !g.situation.repetition &&
          g.situation.perpetualCheck &&
          g.situation.winner == Some(winner)

      "after 4 repetitions" in {
        g must beValid.like { case game =>
          game.playMoveList(m) must beValid.like { case game2 =>
            isPerpetualWith(game2, Gote) must beTrue
          }
        }
      }
      "on my turn" in {
        val g = sfenToGame(Sfen("6k2/1+p7/9/9/9/9/9/1+P2R4/6K2 b - 1"), shogi.variant.Standard).toOption.get
        val moves = List(
          "5h3h",
          "3a2a",
          "3h2h",
          "2a3a",
          "2h3h",
          "3a2a",
          "3h2h",
          "2a3a",
          "2h3h",
          "3a2a",
          "3h2h",
          "2a3a",
          "2h3h"
        ).map(u => Usi(u).get)
        g.playUsiMoveList(moves) must beValid.like { case game =>
          isPerpetualWith(game, Gote) must beTrue
        }
      }
      "on opponent's turn" in {
        val g = sfenToGame(
          Sfen("l2g2B1l/1r2k2g1/p1nsppnp1/2pp4p/9/1pPS2s1P/P2PPPN1B/1PG6/LN2KG2L w S2Prp 66"),
          shogi.variant.Standard
        ).toOption.get
        val moves = List(
          "9c9d",
          "3a5c+",
          "5b5a", // 1st
          "5c6b",
          "5a4b",
          "6b5c",
          "4b5a", // 2nd
          "5c6b",
          "5a4b",
          "6b5c",
          "4b5a", // 3rd
          "5c6b",
          "5a4b",
          "6b5c",
          "4b5a" // 4th
        ).map(u => Usi(u).get)
        g.playUsiMoveList(moves) must beValid.like { case game =>
          isPerpetualWith(game, Gote) must beTrue
        }
      }
      "on opponent's turn 2" in {
        val g = sfenToGame(
          Sfen("l2gk3l/1r5g1/2ns+Bpnp1/p1pp4p/9/1pPS2s1P/P2PPPN1B/1PG6/LN2KG2L b S3Prp 69"),
          shogi.variant.Standard
        ).toOption.get
        val moves = List(
          "5c6b",
          "5a4b",
          "6b5c",
          "4b5a", // 2nd
          "5c6b",
          "5a4b",
          "6b5c",
          "4b5a", // 3rd
          "5c6b",
          "5a4b",
          "6b5c",
          "4b5a" // 4th
        ).map(u => Usi(u).get)
        g.playUsiMoveList(moves) must beValid.like { case game =>
          isPerpetualWith(game, Gote) must beTrue
        }
      }
      "on opponent's turn 3" in {
        val g = sfenToGame(Sfen("1+s4k2/5r3/9/9/9/9/9/9/1+N4K2 w - 1"), shogi.variant.Standard).toOption.get
        val moves = List(
          "4b3b",
          "3i4i",
          "3b4b",
          "4i3i",
          "4b3b",
          "3i4i",
          "3b4b",
          "4i3i",
          "4b3b",
          "3i4i",
          "3b4b",
          "4i3i"
        ).map(u => Usi(u).get)
        g.playUsiMoveList(moves) must beValid.like { case game =>
          game.situation.winner must_== Some(Sente)
        }
      }
      "Sente starts in check" in {
        val g = sfenToGame(Sfen("k5r2/+s8/9/9/9/9/9/9/S5K2 b - 1"), shogi.variant.Standard).toOption.get
        val moves = List(
          "3i2i",
          "3a2a",
          "2i3i",
          "2a3a",
          "3i2i",
          "3a2a",
          "2i3i",
          "2a3a",
          "3i2i",
          "3a2a",
          "2i3i",
          "2a3a"
        ).map(u => Usi(u).get)
        g.playUsiMoveList(moves) must beValid.like { case game =>
          game.situation.winner must_== Some(Sente)
        }
      }
      "Gote starts in check" in {
        val g = sfenToGame(Sfen("7k1/9/9/9/9/9/9/+N8/K6R1 w - 1"), shogi.variant.Standard).toOption.get
        val moves = List(
          "2a3a",
          "2i3i",
          "3a2a",
          "3i2i",
          "2a3a",
          "2i3i",
          "3a2a",
          "3i2i",
          "2a3a",
          "2i3i",
          "3a2a",
          "3i2i"
        ).map(u => Usi(u).get)
        g.playUsiMoveList(moves) must beValid.like { case game =>
          game.situation.winner must_== Some(Gote)
        }
      }
    }
  }

  "Chushogi perpetual check" should {
    "not trigger" in {
      val dGame = Game(shogi.variant.Chushogi)
      val dMoves = List(
        "4h4g",
        "9e9f",
        "4g4h",
        "9f9e", // first repetition
        "4h4g",
        "9e9f",
        "4g4h",
        "9f9e", // second repetition
        "4h4g",
        "9e9f",
        "4g4h",
        "9f9e", // third repetition
        "4h4g",
        "9e9f",
        "4g4h",
        "9f9e" // forth repetition
      ).map(Usi.Move(_).get)
      dGame.playMoveList(dMoves.map(u => (u.orig, u.dest, false))) must beValid.like { case game =>
        game.situation.draw must beFalse
        game.situation.repetition must beTrue
        game.situation.perpetualCheck must beFalse
        game.situation.winner must beNone
      }
    }
    "trigger" in {
      val aGame = Game(
        Some(
          Sfen(
            "lfcsgek1scfl/a1b1txot1b1a/mvrhdqndhrvm/pppppppppppp/8i3/3I8/3g8/8I3/PPPPPPPPPPPP/MVRHDNQDHRVM/A1B1TOXT1B1A/LFCSGKEGSCFL w"
          )
        ),
        shogi.variant.Chushogi
      )
      val aMoves = List(
        "1d1e",
        "9f9e", // start
        "4e4f",
        "9e9f",
        "4f4e",
        "9f9e", // 1st
        "4e4f",
        "9e9f",
        "4f4e",
        "9f9e", // 2nd
        "4e4f",
        "9e9f",
        "4f4e",
        "9f9e", // 3rd
        "4e4f",
        "9e9f",
        "4f4e",
        "9f9e"
      ).map(Usi.Move(_).get)
      aGame.playMoveList(aMoves.map(u => (u.orig, u.dest, false))) must beValid.like { case game =>
        game.situation.draw must beFalse
        game.situation.repetition must beFalse
        game.situation.perpetualCheck must beTrue
        game.situation.winner must_== Some(Gote)
      }
    }
  }
}
