package resella;


import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebScraper {

	//Declaring variables for use in class
	private int numListings;
	private String keyWords;
	//	private ArrayList<ProductListings> activeAdListings;
	//	private ArrayList<ProductListings> soldAdListings;


	/**
	 * Constructor for adding keywords
	 * 
	 * @param keyWords	The keywords or the "search text" that the user is using to search the item on the program 
	 */
	public WebScraper(String keyWords) {
		this.keyWords = keyWords;
	}

	/**
	 * Method that scrapes the listings
	 */
	public void scrapeListings(){
		try {

			//item results URL declared and assigned to variable from keywords searched by the user
			String itemResultsURL = "https://www.ebay.com/sch/i.html?_from=R40&_nkw=" + keyWords + "&_sacat=0&LH_TitleDesc=0&_fosrp=2&_odkw=galaxy+note+8&_ipg=100";

			// Here we create a document object and use JSoup to fetch the website
			Document doc = Jsoup.connect(itemResultsURL).get();


			// With the document fetched, we use JSoup's title() method to fetch the title
			//		      System.out.printf("Title: %s\n", doc.title());

			// Get the list of repositories
			Elements numMatches = doc.getElementsByClass("srp-controls__count-heading");

			//Printing the message for the number of results for the item
			System.out.println(numMatches.text());
			Pattern r = Pattern.compile("^([0-9],?)+");
			Matcher m = r.matcher(numMatches.text());


			if (m.find( )) {
				System.out.println(m.group(0).replaceAll(",", ""));
			}

			// Get the list of repositories
			Elements searchItems = doc.getElementsByClass("s-item__image");


			//Canadian repo code:rsHdr


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
			/**
			 * For each repository, extract the following information:
			 * 1. Title
			 * 2. Number of issues
			 * 3. Description
			 * 4. Full name on github
			 */

			// In case of any IO errors, we want the messages written to the console
		} catch (IOException e) {
			e.printStackTrace();
		}

	}


	/**
	 * This method scrapes search results on EBay
	 * 
	 * @param searchURL The search URL for the keywords
	 */
	private void scrapeSearchResultsEBay(String searchURL) {



	}

	/**
	 * This method scrapes listings from EBBay
	 * 
	 * @param productURL The URL for the product listing
	 */
	private void scrapeListingEBay(String productURL) {



	}

	/**
	 * This method scrapes search results from kjiji
	 * 
	 * @param searchURL The search URL for the user's keywords
	 */
	private void scrapeSearchResultsKijiji(String searchURL) {


	}

	/**
	 * This method scrapes listings from kijiji
	 * 
	 * @param productURL The URL for the product listing
	 */
	private void scrapeListingKijiji(String productURL) {


	}


	//	private ArrayList<ProductListing> getActiveAdListings() {
	//		
	//		
	//	}

	//	private ArrayList<ProductListing> getSoldAdListings() {
	//	
	//	
	//}



}
