package fractal

trait UrlMatcher {
  def matches(urlComponents:UrlComponents):Boolean
  def informationGain(labels:List[LabelledUrl]) = {
    val (matching,notmatching) = labels.partition(l => matches(l.url))
    entropyIn(labels) - (entropyIn(matching) * matching.size + entropyIn(notmatching) * notmatching.size) / labels.size
  }

  def entropyIn(labels:List[LabelledUrl]):Double = {
    val count: Int = labels.count(_.include)
    if(count == labels.size || count == 0) return 0
    val positive = count.toDouble / labels.size
    - List(positive, 1 - positive).map(d => d * math.log(d) / math.log(2)).sum
  }

  def partition(labels:List[LabelledUrl]) = labels.partition(l => matches(l.url))
}

case class PathComponentMatcher(index:Int, value:String) extends UrlMatcher {
  def matches(urlComponents:UrlComponents) = urlComponents.pathComponents.exists(_.matching(index,value))
}

case class QueryParamMatcher(name:String) extends UrlMatcher {
  def matches(urlComponents:UrlComponents) = urlComponents.queryComponents.exists(_.matching(name))
}
