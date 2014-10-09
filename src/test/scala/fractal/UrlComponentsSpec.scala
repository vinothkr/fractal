package fractal

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers

class UrlComponentsSpec extends FlatSpec with ShouldMatchers {
  "UrlComponents" should "extract path components from url" in {
    val components = UrlComponents("https://mail.google.com/mail/u/0/?pli=1#inbox")
    components.pathComponents should be(List(PathComponent(0,"mail"),PathComponent(1,"u"),PathComponent(2,"0")))
  }

  it should "extract query params from url" in {
    val components = UrlComponents("https://mail.google.com/mail/u/0/?pli=1#inbox")
    components.queryComponents should be(List(QueryParam("pli","1")))
  }

  it should "give all the path component matchers and query component matchers" in {
    val components = UrlComponents("https://mail.google.com/mail/u/0/?pli=1#inbox")
    components.matchersPossible should be(List(
      PathComponentMatcher(0,"mail"),
      PathComponentMatcher(1,"u"),
      PathComponentMatcher(2,"0"),
      QueryParamMatcher("pli")
    ))
  }
}
