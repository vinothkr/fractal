package fractal

import java.net.URL

case class PathComponent(index:Int, value:String) {
  def matching(index:Int, value:String) = this.index == index && this.value == value
}
case class QueryParam(name:String, value:String) {
  def matching(name:String) = this.name == name
}
case class UrlComponents(url:String) {
  val pathComponents = new URL(url).getPath
                                   .split("/")
                                   .filterNot(_.isEmpty)
                                   .zipWithIndex
                                   .map(t => PathComponent(t._2,t._1))
                                   .toList

  val queryComponents = Option(new URL(url).getQuery) match {
    case Some(query) => query.split("&")
                             .map(_.split("="))
                             .map(a => QueryParam(a(0),a(1)))
                             .toList
    case None => List()
  }


  def matchersPossible = pathComponents.map(p => PathComponentMatcher(p.index,p.value)) ++
                         queryComponents.map(q => QueryParamMatcher(q.name))
}
