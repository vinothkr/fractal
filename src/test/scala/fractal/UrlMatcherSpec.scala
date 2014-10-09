package fractal

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FlatSpec

class UrlMatcherSpec extends FlatSpec with ShouldMatchers{
  val includeURLs = List(
    "http://www.reddit.com/r/technology",
    "https://mail.google.com/mail/u/0/#inbox",
    "https://mail.google.com/mail/u/",
    "https://news.ycombinator.com/item?id=8428632"
  ).map(u => LabelledUrl(UrlComponents(u),include = true))

  val excludeURLs = List(
    "https://news.ycombinator.com/item?id=8428522",
    "https://www.facebook.com/?ref=logo"
  ).map(u => LabelledUrl(UrlComponents(u),include = false))

  "UrlMatcher" should "find the information gain by partitioning the based on it" in {
    PathComponentMatcher(0,"mail").informationGain(includeURLs) should be(0)
    PathComponentMatcher(0,"mail").informationGain(includeURLs ++ excludeURLs) should be(DoubleTolerance(0.25,0.01))
    PathComponentMatcher(0,"mail").informationGain(excludeURLs) should be(0)
    PathComponentMatcher(0,"dummy").informationGain(includeURLs ++ excludeURLs) should be(0)
  }

  "PathComponent" should "match if both index and value matches the matcher's" in {
    PathComponentMatcher(0,"mail").matches(UrlComponents("https://mail.google.com/mail/u/0/#inbox")) should be(true)
    PathComponentMatcher(1,"mail").matches(UrlComponents("https://mail.google.com/mail/u/0/#inbox")) should be(false)
    PathComponentMatcher(0,"mail").matches(UrlComponents("https://mail.google.com/somewhereelse/u/0/#inbox")) should be(false)
  }

  "QueryParam" should "match if just the name of the query matches" in {
    QueryParamMatcher("id").matches(UrlComponents("https://news.ycombinator.com/item?id=8428632")) should be(true)
    QueryParamMatcher("id").matches(UrlComponents("https://mail.google.com/mail/u/0/#inbox")) should be(false)
  }

}
