// Tutorial: https://stackabuse.com/web-scraping-the-java-way/
package resella;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JSoupWebscraperTest {

	public JSoupWebscraperTest() {
		// TODO Auto-generated constructor stub
	}
	 public static void main(String[] args) {
		    try {
		      // Here we create a document object and use JSoup to fetch the website
		      Document doc = Jsoup.connect("https://www.ebay.com/sch/i.html?_from=R40&_nkw=corvette&_sacat=0&LH_TitleDesc=0&_fosrp=2&_odkw=galaxy+note+8&_ipg=100").get();

//		      // With the document fetched, we use JSoup's title() method to fetch the title
//		      System.out.printf("Title: %s\n", doc.title());
		      
		      // Get the list of repositories
		      Elements numMatches = doc.getElementsByClass("srp-controls__count-heading");
		      
		      
		      System.out.println(numMatches.text());
		      Pattern r = Pattern.compile("^([0-9],?)+");
		      Matcher m = r.matcher(numMatches.text());
		      if (m.find( )) {
		          System.out.println(m.group(0).replaceAll(",", ""));
		      }
		      
		      // Get the list of repositories
		      Elements searchItems = doc.getElementsByClass("s-item__image");
		      
		      
		      //Canadian repo code:rsHdr

		      /**
		       * For each repository, extract the following information:
		       * 1. Title
		       * 2. Number of issues
		       * 3. Description
		       * 4. Full name on github
		       */
		      
		      
		      for (Element searchItem : searchItems) {
		      
		    	  Elements links = searchItem.select("a[href]");
		          for (Element link : links) {
		            
		        	  String href = link.attr("href");
		            
		            if(!href.isEmpty()) {
		            	
		            	// get the value from the href attribute
			            System.out.println("\nlink: " + href);
		            }
		          }
		    	  
		      }

		    // In case of any IO errors, we want the messages written to the console
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
	}
}

