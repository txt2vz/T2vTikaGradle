//import org.apache.tika.Tika;
//import org.apache.tika.exception.TikaException;
import groovy.transform.BaseScript

import org.jsoup.Jsoup

import processText.*
import twitter4j.*
import twitter4j.conf.*

@BaseScript TwitterKeys keys

String text = params.get("s")

//twitter query
if ( params.get("tw")){
	System.out.println  "Twitter Query: " +  params.get("tw")
	Twitter twitter = getTwitterAuth();
	Query query = new Query(text + "-filter:retweets");
	query.setCount(40)
	//GeoLocation sheffield = new GeoLocation(53.377127, -1.467705);
	//query.setGeoCode(sheffield, distance, Query.KILOMETERS)
	QueryResult result = twitter.search(query);

	def twCount =0;
	def twitterText= ""

	for   (i in 0..10){//(;;) {
		result = twitter.search(query);
		def tweets = result.getTweets()

		tweets.each {
			twCount++
			twitterText = twitterText + it.getText()
		}

		query = result.nextQuery()
		if (query == null) break;
	}
	System.out.println( "twCount : $twCount" )
	text = twitterText
} else

	// should use apache commons URLValidator
	if (text.startsWith("http") && text.size() < 256) {
		def url = text.toURL()
		text = Jsoup.parse(url.getText()).text()

		//Tika tika = new Tika();
		//text = tika.parseToString(url)
	}

def networkType = params.get("networkType")
float cooc = params.get("cooc").toFloat()
int maxLinks = params.get("maxLinks").toInteger()
int maxWords = params.get("maxWords").toInteger()

System.out.println "***TEXTIN: networkType is $networkType cooc: $cooc maxLinks $maxLinks maxWords $maxWords"

def nt = (networkType == "forceNet") ? "graph" : "tree"
def gwl = new GenerateWordLinks(nt, cooc, maxLinks, maxWords)
def	json = gwl.getJSONnetwork(text)
print json

private Twitter getTwitterAuth(){

	ConfigurationBuilder cb = new ConfigurationBuilder();
	cb.setDebugEnabled(true)
			.setOAuthConsumerKey(consumerKey)
			.setOAuthConsumerSecret(consumerSecret)
			.setOAuthAccessToken(accessToken)
			.setOAuthAccessTokenSecret(accessTokenSecret);
	TwitterFactory tf = new TwitterFactory(cb.build());
	Twitter twitter = tf.getInstance();

	return tf.getInstance();
}