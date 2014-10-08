package fractal

import scala.io.Source


trait UrlFilterNode {}
case class Leaf(include:Boolean) extends UrlFilterNode
case class Split(criteria:UrlMatcher, matching:UrlFilterNode, notMatching:UrlFilterNode) extends UrlFilterNode

case class LabelledUrl(url:UrlComponents,include:Boolean)

object UrlFilterTree {
  def from(urls:List[LabelledUrl]):UrlFilterNode = {
    val count = urls.count(_.include)
    if(count == 0) return Leaf(include = false)
    if(count == urls.size) return Leaf(include = true)

    val by = urls.flatMap(_.url.matchersPossible.toSet).maxBy(_.informationGain(urls))
    val (matching,notMatching) = by.partition(urls)
    Split(by,UrlFilterTree.from(matching),UrlFilterTree.from(notMatching))
  }
}