import java.net.URL;
import org.apache.tika.Tika;

String strURL = params.get("u");
def url = strURL.toURL()
Tika tika = new Tika();
String text = tika.parseToString(url) 

def gjp = new GetJSONpairs();
String set = gjp.getWordPairs(text);
System.out.println("in url.groovy set is : $set");
out.print(set)