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
	// TODO: manage different locations
	static String location = "ottawa";
	static String keywords = "corvette";
	static String searchID = "k0l1700185";

	public static void main(String[] args) {
	    try {
	    	// TODO: check what ?dc=true means
	      // Here we create a document object and use JSoup to fetch the website
	      Document doc = Jsoup.connect("https://www.kijiji.ca/b-" + location + "/" + keywords + "/" + searchID + "?dc=true" + "&sort=priceAsc").get();
	      
	      // Get the list of repositories
	      Elements numMatches = doc.getElementsByClass("showing");
	      
	      
	      System.out.println(numMatches.text());
	      Pattern r = Pattern.compile("([0-9],?)+ Ad");
	      Matcher m = r.matcher(numMatches.text());
	      if (m.find()) {
	          System.out.println(m.group(0).replaceAll("[ Ad,]", ""));
	      }
	      
	      // Get the list of repositories
	      Elements searchItems = doc.getElementsByClass("regular-ad");
	      
	      for (Element searchItem : searchItems){
	      
	    	  Elements links = searchItem.select("a[href]");
	          for (Element link : links) {
	            
	        	  String href = link.attr("href");
	            
	            if(!href.isEmpty()) {
	            	
	            	// get the value from the href attribute
		            System.out.println("\nlink: "+ "https://www.kijiji.ca"  + href);
	            }
	          }
	      }
	    Document listing = Jsoup.connect("https://www.kijiji.ca/v-classic-cars/oshawa-durham-region/1984-corvette/1503692990").get();
	    
	    // Title:
	    Element title = listing.getElementsByClass("title-2323565163").first();
	    System.out.println("Title: " + title.text());
	    
	    // Price:
	    Element price = listing.getElementsByClass("currentPrice-2842943473").first();
	    String dollars = price.text().replaceAll("[\\$,]", "");
	    System.out.println("Price: " + Double.parseDouble(dollars));
	    
	    // Image src
		Element presentation = listing.getElementById("mainHeroImage");
		Element image = presentation.getElementsByTag("img").first();
		System.out.println("Image src: " + image.attr("src"));
	
	    // In case of any IO errors, we want the messages written to the console
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	}
}
