package shogi
package format

import cats.data.NonEmptyList
import cats.data.Validated
import cats.data.Validated.valid

import shogi.format.usi.Usi
import shogi.format.forsyth.Sfen

final case class ParsedNotation(
    parsedMoves: ParsedMoves,
    initialSfen: Option[Sfen],
    variant: shogi.variant.Variant,
    initialPosition: InitialPosition,
    tags: Tags
)

final case class ParsedMoves(value: List[ParsedMove]) extends AnyVal

object ParsedMoves {
  val empty = ParsedMoves(Nil)
}

sealed trait ParsedMove {

  def toUsi(sit: Situation): Validated[String, Usi]

  def positions: List[Pos]

  def metas: Metas

  def withMetas(m: Metas): ParsedMove

  def withSuffixes(s: Suffixes): ParsedMove = withMetas(metas withSuffixes s)

  def withComments(s: List[String]): ParsedMove = withMetas(metas withComments s)

  def withVariations(s: List[ParsedMoves]): ParsedMove = withMetas(metas withVariations s)

  def withTimeSpent(ts: Option[Centis]): ParsedMove = withMetas(metas withTimeSpent ts)

  def withTimeTotal(tt: Option[Centis]): ParsedMove = withMetas(metas withTimeTotal tt)

  def mergeGlyphs(glyphs: Glyphs): ParsedMove =
    withMetas(
      metas.withGlyphs(metas.glyphs merge glyphs)
    )

}

final case class KifMove(
    dest: Pos,
    orig: Pos,
    roles: NonEmptyList[Role], // in chushogi some kanji map to many roles
    midStep: Option[Pos] = None,
    promotion: Boolean = false,
    metas: Metas = Metas.empty
) extends ParsedMove {

  def toUsi(sit: Situation) = valid(Usi.Move(orig, dest, promotion, midStep))

  def withMetas(m: Metas) = copy(metas = m)

  def positions = midStep.fold(List(orig, dest))(ms => List(orig, ms, dest))

}

final case class CsaMove(
    dest: Pos,
    orig: Pos,
    role: Role,
    metas: Metas = Metas.empty
) extends ParsedMove {

  def toUsi(sit: Situation): Validated[String, Usi] =
    Validated.fromOption(sit.board(orig), s"No piece at $orig") map { p =>
      Usi.Move(orig, dest, role != p.role, None)
    }

  def withMetas(m: Metas) = copy(metas = m)

  def positions = List(orig, dest)

}

// All notations can share drop
final case class Drop(
    role: DroppableRole,
    pos: Pos,
    metas: Metas = Metas.empty
) extends ParsedMove {

  def toUsi(sit: Situation) =
    valid(Usi.Drop(role, pos))

  def withMetas(m: Metas) = copy(metas = m)

  def positions = List(pos)
}

final case class InitialPosition(
    comments: List[String]
)

final case class Metas(
    comments: List[String],
    glyphs: Glyphs,
    variations: List[ParsedMoves],
    timeSpent: Option[Centis],
    timeTotal: Option[Centis]
) {

  def withSuffixes(s: Suffixes) =
    copy(
      glyphs = s.glyphs
    )

  def withGlyphs(g: Glyphs) = copy(glyphs = g)

  def withComments(c: List[String]) = copy(comments = c)

  def withVariations(v: List[ParsedMoves]) = copy(variations = v)

  def withTimeSpent(ts: Option[Centis]) = copy(timeSpent = ts)

  def withTimeTotal(tt: Option[Centis]) = copy(timeTotal = tt)
}

object Metas {
  val empty = Metas(Nil, Glyphs.empty, Nil, None, None)
}

final case class Suffixes(
    promotion: Boolean,
    glyphs: Glyphs
)
