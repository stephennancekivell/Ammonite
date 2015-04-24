package ammonite.repl.interp

import scala.tools.nsc.Global
import scala.tools.nsc.interactive.{ Global => InteractiveGlobal }
import scala.tools.nsc.typechecker.Analyzer

object CompilerCompatibility {
  def analyzer(g: Global, cl: ClassLoader): Analyzer { val global: g.type } =
    new { val global: g.type = g } with Analyzer {
      override lazy val macroClassloader = cl
    }

  type InteractiveAnalyzer = Analyzer

  def interactiveAnalyzer(g: InteractiveGlobal, cl: ClassLoader): InteractiveAnalyzer { val global: g.type } =
    analyzer(g, cl)

  def trees(g: Global)(parser: g.syntaxAnalyzer.UnitParser): Seq[Global#Tree] =
    parser.templateStats() ++ parser.topStatSeq()
}
