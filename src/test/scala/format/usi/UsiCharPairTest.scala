package shogi
package format
package usi

import Usi._
import variant.Standard

class UsiCharPairTest extends ShogiTest {

  "char pair encoding" should {

    def conv(usi: Usi)       = UsiCharPair(usi, variant.Standard).toString
    def convStr(str: String) = conv(Usi(str).get)

    val allMoves = for {
      orig <- Standard.allPositions
      dest <- Standard.allPositions
    } yield Move(orig, dest, false)
    val allDrops = for {
      role <- variant.Standard.handRoles
      pos  <- Standard.allPositions
    } yield Drop(role, pos)

    val allMovesCharPairs = allMoves.map(conv(_))
    val allDropsCharPairs = allDrops.map(conv(_))
    val allPairs          = allMoves.map(conv(_)) ++ allDrops.map(conv(_))

    "unicity" in {
      allMovesCharPairs.distinct.size must_== allMoves.size
      allDropsCharPairs.distinct.size must_== allDrops.size
      allPairs.distinct.size must_== allMoves.size + allDrops.size
    }
    "no void char" in {
      allPairs.count(_ contains UsiCharPair.voidChar) must_== 0
    }

    "check all moves" in {
      convStr("1a1b") must_== """#,"""
      convStr("1a1b+") must_== """,#"""
      convStr("1b1a") must_== """,#"""
      convStr("1b1a+") must_== """#,"""
      convStr("1a1c") must_== """#5"""
      convStr("1a1c+") must_== """5#"""
      convStr("1c1a") must_== """5#"""
      convStr("1c1a+") must_== """#5"""
      convStr("1a1d") must_== """#>"""
      convStr("1a1d+") must_== """>#"""
      convStr("1d1a") must_== """>#"""
      convStr("1d1a+") must_== """#>"""
      convStr("1a1e") must_== """#G"""
      convStr("1a1e+") must_== """G#"""
      convStr("1e1a") must_== """G#"""
      convStr("1e1a+") must_== """#G"""
      convStr("1a1f") must_== """#P"""
      convStr("1a1f+") must_== """P#"""
      convStr("1f1a") must_== """P#"""
      convStr("1f1a+") must_== """#P"""
      convStr("1a1g") must_== """#Y"""
      convStr("1a1g+") must_== """Y#"""
      convStr("1g1a") must_== """Y#"""
      convStr("1g1a+") must_== """#Y"""
      convStr("1a1h") must_== """#b"""
      convStr("1a1h+") must_== """b#"""
      convStr("1h1a") must_== """b#"""
      convStr("1h1a+") must_== """#b"""
      convStr("1a1i") must_== """#k"""
      convStr("1a1i+") must_== """k#"""
      convStr("1i1a") must_== """k#"""
      convStr("1i1a+") must_== """#k"""
      convStr("1a2a") must_== """#$"""
      convStr("1a2a+") must_== """$#"""
      convStr("2a1a") must_== """$#"""
      convStr("2a1a+") must_== """#$"""
      convStr("1a2b") must_== """#-"""
      convStr("1a2b+") must_== """-#"""
      convStr("2b1a") must_== """-#"""
      convStr("2b1a+") must_== """#-"""
      convStr("1a2c") must_== """#6"""
      convStr("1a2c+") must_== """6#"""
      convStr("2c1a") must_== """6#"""
      convStr("2c1a+") must_== """#6"""
      convStr("1a2d") must_== """#?"""
      convStr("1a2d+") must_== """?#"""
      convStr("2d1a") must_== """?#"""
      convStr("2d1a+") must_== """#?"""
      convStr("1a2e") must_== """#H"""
      convStr("1a2e+") must_== """H#"""
      convStr("2e1a") must_== """H#"""
      convStr("2e1a+") must_== """#H"""
      convStr("1a2f") must_== """#Q"""
      convStr("1a2f+") must_== """Q#"""
      convStr("2f1a") must_== """Q#"""
      convStr("2f1a+") must_== """#Q"""
      convStr("1a2g") must_== """#Z"""
      convStr("1a2g+") must_== """Z#"""
      convStr("2g1a") must_== """Z#"""
      convStr("2g1a+") must_== """#Z"""
      convStr("1a2h") must_== """#c"""
      convStr("1a2h+") must_== """c#"""
      convStr("2h1a") must_== """c#"""
      convStr("2h1a+") must_== """#c"""
      convStr("1a2i") must_== """#l"""
      convStr("1a2i+") must_== """l#"""
      convStr("2i1a") must_== """l#"""
      convStr("2i1a+") must_== """#l"""
      convStr("1a3a") must_== """#%"""
      convStr("1a3a+") must_== """%#"""
      convStr("3a1a") must_== """%#"""
      convStr("3a1a+") must_== """#%"""
      convStr("1a3b") must_== """#."""
      convStr("1a3b+") must_== """.#"""
      convStr("3b1a") must_== """.#"""
      convStr("3b1a+") must_== """#."""
      convStr("1a3c") must_== """#7"""
      convStr("1a3c+") must_== """7#"""
      convStr("3c1a") must_== """7#"""
      convStr("3c1a+") must_== """#7"""
      convStr("1a3d") must_== """#@"""
      convStr("1a3d+") must_== """@#"""
      convStr("3d1a") must_== """@#"""
      convStr("3d1a+") must_== """#@"""
      convStr("1a3e") must_== """#I"""
      convStr("1a3e+") must_== """I#"""
      convStr("3e1a") must_== """I#"""
      convStr("3e1a+") must_== """#I"""
      convStr("1a3f") must_== """#R"""
      convStr("1a3f+") must_== """R#"""
      convStr("3f1a") must_== """R#"""
      convStr("3f1a+") must_== """#R"""
      convStr("1a3g") must_== """#["""
      convStr("1a3g+") must_== """[#"""
      convStr("3g1a") must_== """[#"""
      convStr("3g1a+") must_== """#["""
      convStr("1a3h") must_== """#d"""
      convStr("1a3h+") must_== """d#"""
      convStr("3h1a") must_== """d#"""
      convStr("3h1a+") must_== """#d"""
      convStr("1a3i") must_== """#m"""
      convStr("1a3i+") must_== """m#"""
      convStr("3i1a") must_== """m#"""
      convStr("3i1a+") must_== """#m"""
      convStr("1a4a") must_== """#&"""
      convStr("1a4a+") must_== """&#"""
      convStr("4a1a") must_== """&#"""
      convStr("4a1a+") must_== """#&"""
      convStr("1a4b") must_== """#/"""
      convStr("1a4b+") must_== """/#"""
      convStr("4b1a") must_== """/#"""
      convStr("4b1a+") must_== """#/"""
      convStr("1a4c") must_== """#8"""
      convStr("1a4c+") must_== """8#"""
      convStr("4c1a") must_== """8#"""
      convStr("4c1a+") must_== """#8"""
      convStr("1a4d") must_== """#A"""
      convStr("1a4d+") must_== """A#"""
      convStr("4d1a") must_== """A#"""
      convStr("4d1a+") must_== """#A"""
      convStr("1a4e") must_== """#J"""
      convStr("1a4e+") must_== """J#"""
      convStr("4e1a") must_== """J#"""
      convStr("4e1a+") must_== """#J"""
      convStr("1a4f") must_== """#S"""
      convStr("1a4f+") must_== """S#"""
      convStr("4f1a") must_== """S#"""
      convStr("4f1a+") must_== """#S"""
      convStr("1a4g") must_== """#\"""
      convStr("1a4g+") must_== """\#"""
      convStr("4g1a") must_== """\#"""
      convStr("4g1a+") must_== """#\"""
      convStr("1a4h") must_== """#e"""
      convStr("1a4h+") must_== """e#"""
      convStr("4h1a") must_== """e#"""
      convStr("4h1a+") must_== """#e"""
      convStr("1a4i") must_== """#n"""
      convStr("1a4i+") must_== """n#"""
      convStr("4i1a") must_== """n#"""
      convStr("4i1a+") must_== """#n"""
      convStr("1a5a") must_== """#'"""
      convStr("1a5a+") must_== """'#"""
      convStr("5a1a") must_== """'#"""
      convStr("5a1a+") must_== """#'"""
      convStr("1a5b") must_== """#0"""
      convStr("1a5b+") must_== """0#"""
      convStr("5b1a") must_== """0#"""
      convStr("5b1a+") must_== """#0"""
      convStr("1a5c") must_== """#9"""
      convStr("1a5c+") must_== """9#"""
      convStr("5c1a") must_== """9#"""
      convStr("5c1a+") must_== """#9"""
      convStr("1a5d") must_== """#B"""
      convStr("1a5d+") must_== """B#"""
      convStr("5d1a") must_== """B#"""
      convStr("5d1a+") must_== """#B"""
      convStr("1a5e") must_== """#K"""
      convStr("1a5e+") must_== """K#"""
      convStr("5e1a") must_== """K#"""
      convStr("5e1a+") must_== """#K"""
      convStr("1a5f") must_== """#T"""
      convStr("1a5f+") must_== """T#"""
      convStr("5f1a") must_== """T#"""
      convStr("5f1a+") must_== """#T"""
      convStr("1a5g") must_== """#]"""
      convStr("1a5g+") must_== """]#"""
      convStr("5g1a") must_== """]#"""
      convStr("5g1a+") must_== """#]"""
      convStr("1a5h") must_== """#f"""
      convStr("1a5h+") must_== """f#"""
      convStr("5h1a") must_== """f#"""
      convStr("5h1a+") must_== """#f"""
      convStr("1a5i") must_== """#o"""
      convStr("1a5i+") must_== """o#"""
      convStr("5i1a") must_== """o#"""
      convStr("5i1a+") must_== """#o"""
      convStr("1a6a") must_== """#("""
      convStr("1a6a+") must_== """(#"""
      convStr("6a1a") must_== """(#"""
      convStr("6a1a+") must_== """#("""
      convStr("1a6b") must_== """#1"""
      convStr("1a6b+") must_== """1#"""
      convStr("6b1a") must_== """1#"""
      convStr("6b1a+") must_== """#1"""
      convStr("1a6c") must_== """#:"""
      convStr("1a6c+") must_== """:#"""
      convStr("6c1a") must_== """:#"""
      convStr("6c1a+") must_== """#:"""
      convStr("1a6d") must_== """#C"""
      convStr("1a6d+") must_== """C#"""
      convStr("6d1a") must_== """C#"""
      convStr("6d1a+") must_== """#C"""
      convStr("1a6e") must_== """#L"""
      convStr("1a6e+") must_== """L#"""
      convStr("6e1a") must_== """L#"""
      convStr("6e1a+") must_== """#L"""
      convStr("1a6f") must_== """#U"""
      convStr("1a6f+") must_== """U#"""
      convStr("6f1a") must_== """U#"""
      convStr("6f1a+") must_== """#U"""
      convStr("1a6g") must_== """#^"""
      convStr("1a6g+") must_== """^#"""
      convStr("6g1a") must_== """^#"""
      convStr("6g1a+") must_== """#^"""
      convStr("1a6h") must_== """#g"""
      convStr("1a6h+") must_== """g#"""
      convStr("6h1a") must_== """g#"""
      convStr("6h1a+") must_== """#g"""
      convStr("1a6i") must_== """#p"""
      convStr("1a6i+") must_== """p#"""
      convStr("6i1a") must_== """p#"""
      convStr("6i1a+") must_== """#p"""
      convStr("1a7a") must_== """#)"""
      convStr("1a7a+") must_== """)#"""
      convStr("7a1a") must_== """)#"""
      convStr("7a1a+") must_== """#)"""
      convStr("1a7b") must_== """#2"""
      convStr("1a7b+") must_== """2#"""
      convStr("7b1a") must_== """2#"""
      convStr("7b1a+") must_== """#2"""
      convStr("1a7c") must_== """#;"""
      convStr("1a7c+") must_== """;#"""
      convStr("7c1a") must_== """;#"""
      convStr("7c1a+") must_== """#;"""
      convStr("1a7d") must_== """#D"""
      convStr("1a7d+") must_== """D#"""
      convStr("7d1a") must_== """D#"""
      convStr("7d1a+") must_== """#D"""
      convStr("1a7e") must_== """#M"""
      convStr("1a7e+") must_== """M#"""
      convStr("7e1a") must_== """M#"""
      convStr("7e1a+") must_== """#M"""
      convStr("1a7f") must_== """#V"""
      convStr("1a7f+") must_== """V#"""
      convStr("7f1a") must_== """V#"""
      convStr("7f1a+") must_== """#V"""
      convStr("1a7g") must_== """#_"""
      convStr("1a7g+") must_== """_#"""
      convStr("7g1a") must_== """_#"""
      convStr("7g1a+") must_== """#_"""
      convStr("1a7h") must_== """#h"""
      convStr("1a7h+") must_== """h#"""
      convStr("7h1a") must_== """h#"""
      convStr("7h1a+") must_== """#h"""
      convStr("1a7i") must_== """#q"""
      convStr("1a7i+") must_== """q#"""
      convStr("7i1a") must_== """q#"""
      convStr("7i1a+") must_== """#q"""
      convStr("1a8a") must_== """#*"""
      convStr("1a8a+") must_== """*#"""
      convStr("8a1a") must_== """*#"""
      convStr("8a1a+") must_== """#*"""
      convStr("1a8b") must_== """#3"""
      convStr("1a8b+") must_== """3#"""
      convStr("8b1a") must_== """3#"""
      convStr("8b1a+") must_== """#3"""
      convStr("1a8c") must_== """#<"""
      convStr("1a8c+") must_== """<#"""
      convStr("8c1a") must_== """<#"""
      convStr("8c1a+") must_== """#<"""
      convStr("1a8d") must_== """#E"""
      convStr("1a8d+") must_== """E#"""
      convStr("8d1a") must_== """E#"""
      convStr("8d1a+") must_== """#E"""
      convStr("1a8e") must_== """#N"""
      convStr("1a8e+") must_== """N#"""
      convStr("8e1a") must_== """N#"""
      convStr("8e1a+") must_== """#N"""
      convStr("1a8f") must_== """#W"""
      convStr("1a8f+") must_== """W#"""
      convStr("8f1a") must_== """W#"""
      convStr("8f1a+") must_== """#W"""
      convStr("1a8g") must_== """#`"""
      convStr("1a8g+") must_== """`#"""
      convStr("8g1a") must_== """`#"""
      convStr("8g1a+") must_== """#`"""
      convStr("1a8h") must_== """#i"""
      convStr("1a8h+") must_== """i#"""
      convStr("8h1a") must_== """i#"""
      convStr("8h1a+") must_== """#i"""
      convStr("1a8i") must_== """#r"""
      convStr("1a8i+") must_== """r#"""
      convStr("8i1a") must_== """r#"""
      convStr("8i1a+") must_== """#r"""
      convStr("1a9a") must_== """#+"""
      convStr("1a9a+") must_== "+#"
      convStr("9a1a") must_== "+#"
      convStr("9a1a+") must_== """#+"""
      convStr("1a9b") must_== """#4"""
      convStr("1a9b+") must_== """4#"""
      convStr("9b1a") must_== """4#"""
      convStr("9b1a+") must_== """#4"""
      convStr("1a9c") must_== """#="""
      convStr("1a9c+") must_== """=#"""
      convStr("9c1a") must_== """=#"""
      convStr("9c1a+") must_== """#="""
      convStr("1a9d") must_== """#F"""
      convStr("1a9d+") must_== """F#"""
      convStr("9d1a") must_== """F#"""
      convStr("9d1a+") must_== """#F"""
      convStr("1a9e") must_== """#O"""
      convStr("1a9e+") must_== """O#"""
      convStr("9e1a") must_== """O#"""
      convStr("9e1a+") must_== """#O"""
      convStr("1a9f") must_== """#X"""
      convStr("1a9f+") must_== """X#"""
      convStr("9f1a") must_== """X#"""
      convStr("9f1a+") must_== """#X"""
      convStr("1a9g") must_== """#a"""
      convStr("1a9g+") must_== """a#"""
      convStr("9g1a") must_== """a#"""
      convStr("9g1a+") must_== """#a"""
      convStr("1a9h") must_== """#j"""
      convStr("1a9h+") must_== """j#"""
      convStr("9h1a") must_== """j#"""
      convStr("9h1a+") must_== """#j"""
      convStr("1a9i") must_== """#s"""
      convStr("1a9i+") must_== """s#"""
      convStr("9i1a") must_== """s#"""
      convStr("9i1a+") must_== """#s"""
    }

    "check all drops" in {
      convStr("P*1a") must_== """#z"""
      convStr("L*1a") must_== """#y"""
      convStr("N*1a") must_== """#x"""
      convStr("S*1a") must_== """#w"""
      convStr("G*1a") must_== """#v"""
      convStr("B*1a") must_== """#u"""
      convStr("R*1a") must_== """#t"""
      convStr("P*1b") must_== """,z"""
      convStr("L*1b") must_== """,y"""
      convStr("N*1b") must_== """,x"""
      convStr("S*1b") must_== """,w"""
      convStr("G*1b") must_== """,v"""
      convStr("B*1b") must_== """,u"""
      convStr("R*1b") must_== """,t"""
      convStr("P*1c") must_== """5z"""
      convStr("L*1c") must_== """5y"""
      convStr("N*1c") must_== """5x"""
      convStr("S*1c") must_== """5w"""
      convStr("G*1c") must_== """5v"""
      convStr("B*1c") must_== """5u"""
      convStr("R*1c") must_== """5t"""
      convStr("P*1d") must_== """>z"""
      convStr("L*1d") must_== """>y"""
      convStr("N*1d") must_== """>x"""
      convStr("S*1d") must_== """>w"""
      convStr("G*1d") must_== """>v"""
      convStr("B*1d") must_== """>u"""
      convStr("R*1d") must_== """>t"""
      convStr("P*1e") must_== """Gz"""
      convStr("L*1e") must_== """Gy"""
      convStr("N*1e") must_== """Gx"""
      convStr("S*1e") must_== """Gw"""
      convStr("G*1e") must_== """Gv"""
      convStr("B*1e") must_== """Gu"""
      convStr("R*1e") must_== """Gt"""
      convStr("P*1f") must_== """Pz"""
      convStr("L*1f") must_== """Py"""
      convStr("N*1f") must_== """Px"""
      convStr("S*1f") must_== """Pw"""
      convStr("G*1f") must_== """Pv"""
      convStr("B*1f") must_== """Pu"""
      convStr("R*1f") must_== """Pt"""
      convStr("P*1g") must_== """Yz"""
      convStr("L*1g") must_== """Yy"""
      convStr("N*1g") must_== """Yx"""
      convStr("S*1g") must_== """Yw"""
      convStr("G*1g") must_== """Yv"""
      convStr("B*1g") must_== """Yu"""
      convStr("R*1g") must_== """Yt"""
      convStr("P*1h") must_== """bz"""
      convStr("L*1h") must_== """by"""
      convStr("N*1h") must_== """bx"""
      convStr("S*1h") must_== """bw"""
      convStr("G*1h") must_== """bv"""
      convStr("B*1h") must_== """bu"""
      convStr("R*1h") must_== """bt"""
      convStr("P*1i") must_== """kz"""
      convStr("L*1i") must_== """ky"""
      convStr("N*1i") must_== """kx"""
      convStr("S*1i") must_== """kw"""
      convStr("G*1i") must_== """kv"""
      convStr("B*1i") must_== """ku"""
      convStr("R*1i") must_== """kt"""
      convStr("P*2a") must_== s"""$$z"""
      convStr("L*2a") must_== s"""$$y"""
      convStr("N*2a") must_== s"""$$x"""
      convStr("S*2a") must_== s"""$$w"""
      convStr("G*2a") must_== s"""$$v"""
      convStr("B*2a") must_== s"""$$u"""
      convStr("R*2a") must_== s"""$$t"""
      convStr("P*2b") must_== """-z"""
      convStr("L*2b") must_== """-y"""
      convStr("N*2b") must_== """-x"""
      convStr("S*2b") must_== """-w"""
      convStr("G*2b") must_== """-v"""
      convStr("B*2b") must_== """-u"""
      convStr("R*2b") must_== """-t"""
      convStr("P*2c") must_== """6z"""
      convStr("L*2c") must_== """6y"""
      convStr("N*2c") must_== """6x"""
      convStr("S*2c") must_== """6w"""
      convStr("G*2c") must_== """6v"""
      convStr("B*2c") must_== """6u"""
      convStr("R*2c") must_== """6t"""
      convStr("P*2d") must_== """?z"""
      convStr("L*2d") must_== """?y"""
      convStr("N*2d") must_== """?x"""
      convStr("S*2d") must_== """?w"""
      convStr("G*2d") must_== """?v"""
      convStr("B*2d") must_== """?u"""
      convStr("R*2d") must_== """?t"""
      convStr("P*2e") must_== """Hz"""
      convStr("L*2e") must_== """Hy"""
      convStr("N*2e") must_== """Hx"""
      convStr("S*2e") must_== """Hw"""
      convStr("G*2e") must_== """Hv"""
      convStr("B*2e") must_== """Hu"""
      convStr("R*2e") must_== """Ht"""
      convStr("P*2f") must_== """Qz"""
      convStr("L*2f") must_== """Qy"""
      convStr("N*2f") must_== """Qx"""
      convStr("S*2f") must_== """Qw"""
      convStr("G*2f") must_== """Qv"""
      convStr("B*2f") must_== """Qu"""
      convStr("R*2f") must_== """Qt"""
      convStr("P*2g") must_== """Zz"""
      convStr("L*2g") must_== """Zy"""
      convStr("N*2g") must_== """Zx"""
      convStr("S*2g") must_== """Zw"""
      convStr("G*2g") must_== """Zv"""
      convStr("B*2g") must_== """Zu"""
      convStr("R*2g") must_== """Zt"""
      convStr("P*2h") must_== """cz"""
      convStr("L*2h") must_== """cy"""
      convStr("N*2h") must_== """cx"""
      convStr("S*2h") must_== """cw"""
      convStr("G*2h") must_== """cv"""
      convStr("B*2h") must_== """cu"""
      convStr("R*2h") must_== """ct"""
      convStr("P*2i") must_== """lz"""
      convStr("L*2i") must_== """ly"""
      convStr("N*2i") must_== """lx"""
      convStr("S*2i") must_== """lw"""
      convStr("G*2i") must_== """lv"""
      convStr("B*2i") must_== """lu"""
      convStr("R*2i") must_== """lt"""
      convStr("P*3a") must_== """%z"""
      convStr("L*3a") must_== """%y"""
      convStr("N*3a") must_== """%x"""
      convStr("S*3a") must_== """%w"""
      convStr("G*3a") must_== """%v"""
      convStr("B*3a") must_== """%u"""
      convStr("R*3a") must_== """%t"""
      convStr("P*3b") must_== """.z"""
      convStr("L*3b") must_== """.y"""
      convStr("N*3b") must_== """.x"""
      convStr("S*3b") must_== """.w"""
      convStr("G*3b") must_== """.v"""
      convStr("B*3b") must_== """.u"""
      convStr("R*3b") must_== """.t"""
      convStr("P*3c") must_== """7z"""
      convStr("L*3c") must_== """7y"""
      convStr("N*3c") must_== """7x"""
      convStr("S*3c") must_== """7w"""
      convStr("G*3c") must_== """7v"""
      convStr("B*3c") must_== """7u"""
      convStr("R*3c") must_== """7t"""
      convStr("P*3d") must_== """@z"""
      convStr("L*3d") must_== """@y"""
      convStr("N*3d") must_== """@x"""
      convStr("S*3d") must_== """@w"""
      convStr("G*3d") must_== """@v"""
      convStr("B*3d") must_== """@u"""
      convStr("R*3d") must_== """@t"""
      convStr("P*3e") must_== """Iz"""
      convStr("L*3e") must_== """Iy"""
      convStr("N*3e") must_== """Ix"""
      convStr("S*3e") must_== """Iw"""
      convStr("G*3e") must_== """Iv"""
      convStr("B*3e") must_== """Iu"""
      convStr("R*3e") must_== """It"""
      convStr("P*3f") must_== """Rz"""
      convStr("L*3f") must_== """Ry"""
      convStr("N*3f") must_== """Rx"""
      convStr("S*3f") must_== """Rw"""
      convStr("G*3f") must_== """Rv"""
      convStr("B*3f") must_== """Ru"""
      convStr("R*3f") must_== """Rt"""
      convStr("P*3g") must_== """[z"""
      convStr("L*3g") must_== """[y"""
      convStr("N*3g") must_== """[x"""
      convStr("S*3g") must_== """[w"""
      convStr("G*3g") must_== """[v"""
      convStr("B*3g") must_== """[u"""
      convStr("R*3g") must_== """[t"""
      convStr("P*3h") must_== """dz"""
      convStr("L*3h") must_== """dy"""
      convStr("N*3h") must_== """dx"""
      convStr("S*3h") must_== """dw"""
      convStr("G*3h") must_== """dv"""
      convStr("B*3h") must_== """du"""
      convStr("R*3h") must_== """dt"""
      convStr("P*3i") must_== """mz"""
      convStr("L*3i") must_== """my"""
      convStr("N*3i") must_== """mx"""
      convStr("S*3i") must_== """mw"""
      convStr("G*3i") must_== """mv"""
      convStr("B*3i") must_== """mu"""
      convStr("R*3i") must_== """mt"""
      convStr("P*4a") must_== """&z"""
      convStr("L*4a") must_== """&y"""
      convStr("N*4a") must_== """&x"""
      convStr("S*4a") must_== """&w"""
      convStr("G*4a") must_== """&v"""
      convStr("B*4a") must_== """&u"""
      convStr("R*4a") must_== """&t"""
      convStr("P*4b") must_== """/z"""
      convStr("L*4b") must_== """/y"""
      convStr("N*4b") must_== """/x"""
      convStr("S*4b") must_== """/w"""
      convStr("G*4b") must_== """/v"""
      convStr("B*4b") must_== """/u"""
      convStr("R*4b") must_== """/t"""
      convStr("P*4c") must_== """8z"""
      convStr("L*4c") must_== """8y"""
      convStr("N*4c") must_== """8x"""
      convStr("S*4c") must_== """8w"""
      convStr("G*4c") must_== """8v"""
      convStr("B*4c") must_== """8u"""
      convStr("R*4c") must_== """8t"""
      convStr("P*4d") must_== """Az"""
      convStr("L*4d") must_== """Ay"""
      convStr("N*4d") must_== """Ax"""
      convStr("S*4d") must_== """Aw"""
      convStr("G*4d") must_== """Av"""
      convStr("B*4d") must_== """Au"""
      convStr("R*4d") must_== """At"""
      convStr("P*4e") must_== """Jz"""
      convStr("L*4e") must_== """Jy"""
      convStr("N*4e") must_== """Jx"""
      convStr("S*4e") must_== """Jw"""
      convStr("G*4e") must_== """Jv"""
      convStr("B*4e") must_== """Ju"""
      convStr("R*4e") must_== """Jt"""
      convStr("P*4f") must_== """Sz"""
      convStr("L*4f") must_== """Sy"""
      convStr("N*4f") must_== """Sx"""
      convStr("S*4f") must_== """Sw"""
      convStr("G*4f") must_== """Sv"""
      convStr("B*4f") must_== """Su"""
      convStr("R*4f") must_== """St"""
      convStr("P*4g") must_== """\z"""
      convStr("L*4g") must_== """\y"""
      convStr("N*4g") must_== """\x"""
      convStr("S*4g") must_== """\w"""
      convStr("G*4g") must_== """\v"""
      convStr("B*4g") must_== """\""" + "u"
      convStr("R*4g") must_== """\t"""
      convStr("P*4h") must_== """ez"""
      convStr("L*4h") must_== """ey"""
      convStr("N*4h") must_== """ex"""
      convStr("S*4h") must_== """ew"""
      convStr("G*4h") must_== """ev"""
      convStr("B*4h") must_== """eu"""
      convStr("R*4h") must_== """et"""
      convStr("P*4i") must_== """nz"""
      convStr("L*4i") must_== """ny"""
      convStr("N*4i") must_== """nx"""
      convStr("S*4i") must_== """nw"""
      convStr("G*4i") must_== """nv"""
      convStr("B*4i") must_== """nu"""
      convStr("R*4i") must_== """nt"""
      convStr("P*5a") must_== """'z"""
      convStr("L*5a") must_== """'y"""
      convStr("N*5a") must_== """'x"""
      convStr("S*5a") must_== """'w"""
      convStr("G*5a") must_== """'v"""
      convStr("B*5a") must_== """'u"""
      convStr("R*5a") must_== """'t"""
      convStr("P*5b") must_== """0z"""
      convStr("L*5b") must_== """0y"""
      convStr("N*5b") must_== """0x"""
      convStr("S*5b") must_== """0w"""
      convStr("G*5b") must_== """0v"""
      convStr("B*5b") must_== """0u"""
      convStr("R*5b") must_== """0t"""
      convStr("P*5c") must_== """9z"""
      convStr("L*5c") must_== """9y"""
      convStr("N*5c") must_== """9x"""
      convStr("S*5c") must_== """9w"""
      convStr("G*5c") must_== """9v"""
      convStr("B*5c") must_== """9u"""
      convStr("R*5c") must_== """9t"""
      convStr("P*5d") must_== """Bz"""
      convStr("L*5d") must_== """By"""
      convStr("N*5d") must_== """Bx"""
      convStr("S*5d") must_== """Bw"""
      convStr("G*5d") must_== """Bv"""
      convStr("B*5d") must_== """Bu"""
      convStr("R*5d") must_== """Bt"""
      convStr("P*5e") must_== """Kz"""
      convStr("L*5e") must_== """Ky"""
      convStr("N*5e") must_== """Kx"""
      convStr("S*5e") must_== """Kw"""
      convStr("G*5e") must_== """Kv"""
      convStr("B*5e") must_== """Ku"""
      convStr("R*5e") must_== """Kt"""
      convStr("P*5f") must_== """Tz"""
      convStr("L*5f") must_== """Ty"""
      convStr("N*5f") must_== """Tx"""
      convStr("S*5f") must_== """Tw"""
      convStr("G*5f") must_== """Tv"""
      convStr("B*5f") must_== """Tu"""
      convStr("R*5f") must_== """Tt"""
      convStr("P*5g") must_== """]z"""
      convStr("L*5g") must_== """]y"""
      convStr("N*5g") must_== """]x"""
      convStr("S*5g") must_== """]w"""
      convStr("G*5g") must_== """]v"""
      convStr("B*5g") must_== """]u"""
      convStr("R*5g") must_== """]t"""
      convStr("P*5h") must_== """fz"""
      convStr("L*5h") must_== """fy"""
      convStr("N*5h") must_== """fx"""
      convStr("S*5h") must_== """fw"""
      convStr("G*5h") must_== """fv"""
      convStr("B*5h") must_== """fu"""
      convStr("R*5h") must_== """ft"""
      convStr("P*5i") must_== """oz"""
      convStr("L*5i") must_== """oy"""
      convStr("N*5i") must_== """ox"""
      convStr("S*5i") must_== """ow"""
      convStr("G*5i") must_== """ov"""
      convStr("B*5i") must_== """ou"""
      convStr("R*5i") must_== """ot"""
      convStr("P*6a") must_== """(z"""
      convStr("L*6a") must_== """(y"""
      convStr("N*6a") must_== """(x"""
      convStr("S*6a") must_== """(w"""
      convStr("G*6a") must_== """(v"""
      convStr("B*6a") must_== """(u"""
      convStr("R*6a") must_== """(t"""
      convStr("P*6b") must_== """1z"""
      convStr("L*6b") must_== """1y"""
      convStr("N*6b") must_== """1x"""
      convStr("S*6b") must_== """1w"""
      convStr("G*6b") must_== """1v"""
      convStr("B*6b") must_== """1u"""
      convStr("R*6b") must_== """1t"""
      convStr("P*6c") must_== """:z"""
      convStr("L*6c") must_== """:y"""
      convStr("N*6c") must_== """:x"""
      convStr("S*6c") must_== """:w"""
      convStr("G*6c") must_== """:v"""
      convStr("B*6c") must_== """:u"""
      convStr("R*6c") must_== """:t"""
      convStr("P*6d") must_== """Cz"""
      convStr("L*6d") must_== """Cy"""
      convStr("N*6d") must_== """Cx"""
      convStr("S*6d") must_== """Cw"""
      convStr("G*6d") must_== """Cv"""
      convStr("B*6d") must_== """Cu"""
      convStr("R*6d") must_== """Ct"""
      convStr("P*6e") must_== """Lz"""
      convStr("L*6e") must_== """Ly"""
      convStr("N*6e") must_== """Lx"""
      convStr("S*6e") must_== """Lw"""
      convStr("G*6e") must_== """Lv"""
      convStr("B*6e") must_== """Lu"""
      convStr("R*6e") must_== """Lt"""
      convStr("P*6f") must_== """Uz"""
      convStr("L*6f") must_== """Uy"""
      convStr("N*6f") must_== """Ux"""
      convStr("S*6f") must_== """Uw"""
      convStr("G*6f") must_== """Uv"""
      convStr("B*6f") must_== """Uu"""
      convStr("R*6f") must_== """Ut"""
      convStr("P*6g") must_== """^z"""
      convStr("L*6g") must_== """^y"""
      convStr("N*6g") must_== """^x"""
      convStr("S*6g") must_== """^w"""
      convStr("G*6g") must_== """^v"""
      convStr("B*6g") must_== """^u"""
      convStr("R*6g") must_== """^t"""
      convStr("P*6h") must_== """gz"""
      convStr("L*6h") must_== """gy"""
      convStr("N*6h") must_== """gx"""
      convStr("S*6h") must_== """gw"""
      convStr("G*6h") must_== """gv"""
      convStr("B*6h") must_== """gu"""
      convStr("R*6h") must_== """gt"""
      convStr("P*6i") must_== """pz"""
      convStr("L*6i") must_== """py"""
      convStr("N*6i") must_== """px"""
      convStr("S*6i") must_== """pw"""
      convStr("G*6i") must_== """pv"""
      convStr("B*6i") must_== """pu"""
      convStr("R*6i") must_== """pt"""
      convStr("P*7a") must_== """)z"""
      convStr("L*7a") must_== """)y"""
      convStr("N*7a") must_== """)x"""
      convStr("S*7a") must_== """)w"""
      convStr("G*7a") must_== """)v"""
      convStr("B*7a") must_== """)u"""
      convStr("R*7a") must_== """)t"""
      convStr("P*7b") must_== """2z"""
      convStr("L*7b") must_== """2y"""
      convStr("N*7b") must_== """2x"""
      convStr("S*7b") must_== """2w"""
      convStr("G*7b") must_== """2v"""
      convStr("B*7b") must_== """2u"""
      convStr("R*7b") must_== """2t"""
      convStr("P*7c") must_== """;z"""
      convStr("L*7c") must_== """;y"""
      convStr("N*7c") must_== """;x"""
      convStr("S*7c") must_== """;w"""
      convStr("G*7c") must_== """;v"""
      convStr("B*7c") must_== """;u"""
      convStr("R*7c") must_== """;t"""
      convStr("P*7d") must_== """Dz"""
      convStr("L*7d") must_== """Dy"""
      convStr("N*7d") must_== """Dx"""
      convStr("S*7d") must_== """Dw"""
      convStr("G*7d") must_== """Dv"""
      convStr("B*7d") must_== """Du"""
      convStr("R*7d") must_== """Dt"""
      convStr("P*7e") must_== """Mz"""
      convStr("L*7e") must_== """My"""
      convStr("N*7e") must_== """Mx"""
      convStr("S*7e") must_== """Mw"""
      convStr("G*7e") must_== """Mv"""
      convStr("B*7e") must_== """Mu"""
      convStr("R*7e") must_== """Mt"""
      convStr("P*7f") must_== """Vz"""
      convStr("L*7f") must_== """Vy"""
      convStr("N*7f") must_== """Vx"""
      convStr("S*7f") must_== """Vw"""
      convStr("G*7f") must_== """Vv"""
      convStr("B*7f") must_== """Vu"""
      convStr("R*7f") must_== """Vt"""
      convStr("P*7g") must_== """_z"""
      convStr("L*7g") must_== """_y"""
      convStr("N*7g") must_== """_x"""
      convStr("S*7g") must_== """_w"""
      convStr("G*7g") must_== """_v"""
      convStr("B*7g") must_== """_u"""
      convStr("R*7g") must_== """_t"""
      convStr("P*7h") must_== """hz"""
      convStr("L*7h") must_== """hy"""
      convStr("N*7h") must_== """hx"""
      convStr("S*7h") must_== """hw"""
      convStr("G*7h") must_== """hv"""
      convStr("B*7h") must_== """hu"""
      convStr("R*7h") must_== """ht"""
      convStr("P*7i") must_== """qz"""
      convStr("L*7i") must_== """qy"""
      convStr("N*7i") must_== """qx"""
      convStr("S*7i") must_== """qw"""
      convStr("G*7i") must_== """qv"""
      convStr("B*7i") must_== """qu"""
      convStr("R*7i") must_== """qt"""
      convStr("P*8a") must_== """*z"""
      convStr("L*8a") must_== """*y"""
      convStr("N*8a") must_== """*x"""
      convStr("S*8a") must_== """*w"""
      convStr("G*8a") must_== """*v"""
      convStr("B*8a") must_== """*u"""
      convStr("R*8a") must_== """*t"""
      convStr("P*8b") must_== """3z"""
      convStr("L*8b") must_== """3y"""
      convStr("N*8b") must_== """3x"""
      convStr("S*8b") must_== """3w"""
      convStr("G*8b") must_== """3v"""
      convStr("B*8b") must_== """3u"""
      convStr("R*8b") must_== """3t"""
      convStr("P*8c") must_== """<z"""
      convStr("L*8c") must_== """<y"""
      convStr("N*8c") must_== """<x"""
      convStr("S*8c") must_== """<w"""
      convStr("G*8c") must_== """<v"""
      convStr("B*8c") must_== """<u"""
      convStr("R*8c") must_== """<t"""
      convStr("P*8d") must_== """Ez"""
      convStr("L*8d") must_== """Ey"""
      convStr("N*8d") must_== """Ex"""
      convStr("S*8d") must_== """Ew"""
      convStr("G*8d") must_== """Ev"""
      convStr("B*8d") must_== """Eu"""
      convStr("R*8d") must_== """Et"""
      convStr("P*8e") must_== """Nz"""
      convStr("L*8e") must_== """Ny"""
      convStr("N*8e") must_== """Nx"""
      convStr("S*8e") must_== """Nw"""
      convStr("G*8e") must_== """Nv"""
      convStr("B*8e") must_== """Nu"""
      convStr("R*8e") must_== """Nt"""
      convStr("P*8f") must_== """Wz"""
      convStr("L*8f") must_== """Wy"""
      convStr("N*8f") must_== """Wx"""
      convStr("S*8f") must_== """Ww"""
      convStr("G*8f") must_== """Wv"""
      convStr("B*8f") must_== """Wu"""
      convStr("R*8f") must_== """Wt"""
      convStr("P*8g") must_== """`z"""
      convStr("L*8g") must_== """`y"""
      convStr("N*8g") must_== """`x"""
      convStr("S*8g") must_== """`w"""
      convStr("G*8g") must_== """`v"""
      convStr("B*8g") must_== """`u"""
      convStr("R*8g") must_== """`t"""
      convStr("P*8h") must_== """iz"""
      convStr("L*8h") must_== """iy"""
      convStr("N*8h") must_== """ix"""
      convStr("S*8h") must_== """iw"""
      convStr("G*8h") must_== """iv"""
      convStr("B*8h") must_== """iu"""
      convStr("R*8h") must_== """it"""
      convStr("P*8i") must_== """rz"""
      convStr("L*8i") must_== """ry"""
      convStr("N*8i") must_== """rx"""
      convStr("S*8i") must_== """rw"""
      convStr("G*8i") must_== """rv"""
      convStr("B*8i") must_== """ru"""
      convStr("R*8i") must_== """rt"""
      convStr("P*9a") must_== """+z"""
      convStr("L*9a") must_== """+y"""
      convStr("N*9a") must_== """+x"""
      convStr("S*9a") must_== """+w"""
      convStr("G*9a") must_== """+v"""
      convStr("B*9a") must_== """+u"""
      convStr("R*9a") must_== """+t"""
      convStr("P*9b") must_== """4z"""
      convStr("L*9b") must_== """4y"""
      convStr("N*9b") must_== """4x"""
      convStr("S*9b") must_== """4w"""
      convStr("G*9b") must_== """4v"""
      convStr("B*9b") must_== """4u"""
      convStr("R*9b") must_== """4t"""
      convStr("P*9c") must_== """=z"""
      convStr("L*9c") must_== """=y"""
      convStr("N*9c") must_== """=x"""
      convStr("S*9c") must_== """=w"""
      convStr("G*9c") must_== """=v"""
      convStr("B*9c") must_== """=u"""
      convStr("R*9c") must_== """=t"""
      convStr("P*9d") must_== """Fz"""
      convStr("L*9d") must_== """Fy"""
      convStr("N*9d") must_== """Fx"""
      convStr("S*9d") must_== """Fw"""
      convStr("G*9d") must_== """Fv"""
      convStr("B*9d") must_== """Fu"""
      convStr("R*9d") must_== """Ft"""
      convStr("P*9e") must_== """Oz"""
      convStr("L*9e") must_== """Oy"""
      convStr("N*9e") must_== """Ox"""
      convStr("S*9e") must_== """Ow"""
      convStr("G*9e") must_== """Ov"""
      convStr("B*9e") must_== """Ou"""
      convStr("R*9e") must_== """Ot"""
      convStr("P*9f") must_== """Xz"""
      convStr("L*9f") must_== """Xy"""
      convStr("N*9f") must_== """Xx"""
      convStr("S*9f") must_== """Xw"""
      convStr("G*9f") must_== """Xv"""
      convStr("B*9f") must_== """Xu"""
      convStr("R*9f") must_== """Xt"""
      convStr("P*9g") must_== """az"""
      convStr("L*9g") must_== """ay"""
      convStr("N*9g") must_== """ax"""
      convStr("S*9g") must_== """aw"""
      convStr("G*9g") must_== """av"""
      convStr("B*9g") must_== """au"""
      convStr("R*9g") must_== """at"""
      convStr("P*9h") must_== """jz"""
      convStr("L*9h") must_== """jy"""
      convStr("N*9h") must_== """jx"""
      convStr("S*9h") must_== """jw"""
      convStr("G*9h") must_== """jv"""
      convStr("B*9h") must_== """ju"""
      convStr("R*9h") must_== """jt"""
      convStr("P*9i") must_== """sz"""
      convStr("L*9i") must_== """sy"""
      convStr("N*9i") must_== """sx"""
      convStr("S*9i") must_== """sw"""
      convStr("G*9i") must_== """sv"""
      convStr("B*9i") must_== """su"""
      convStr("R*9i") must_== """st"""
    }
  }
}
