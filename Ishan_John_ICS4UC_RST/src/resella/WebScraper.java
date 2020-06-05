package resella;


import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebScraper {

	//Declaring variables for use in class
	private int numListings;
	private String keywords;
	private ArrayList<ProductListing> activeAdListings;
	private ArrayList<ProductListing> soldAdListings;

 
	/**
	 * Constructor for adding keywords
	 * 
	 * @param keywords	The keywords or the "search text" that the user is using to search the item on the program 
	 */
	public WebScraper(String keywords) {
		this.keywords = keywords;
	}

	/**
	 * Method that scrapes the listings
	 */
	public void scrapeListings(){
		//item results URL declared and assigned to variable from keywords searched by the user
		String activeEBayResultsURL = "https://www.ebay.com/sch/i.html?_from=R40&_nkw=" + keywords;
		activeAdListings.addAll(scrapeSearchResultsEBay(activeEBayResultsURL));
		
		String soldEBayResultsURL = "https://www.ebay.com/sch/i.html?_from=R40&_nkw=" + keywords + "&LH_Sold=1";
		soldAdListings.addAll(scrapeSearchResultsEBay(soldEBayResultsURL));
	}


	/**
	 * This method scrapes search results on EBay
	 * 
	 * @param searchURL The search URL for the keywords
	 * @return ArrayList<ProductListing>
	 */
	private ArrayList<ProductListing> scrapeSearchResultsEBay(String searchURL) {
		ArrayList<ProductListing> listingSearchResults = new ArrayList<ProductListing>();
		try {
			// Here we create a document object and use JSoup to fetch the website
			Document doc = Jsoup.connect(searchURL).get();

			// Get the list of repositories
			Elements numResults = doc.getElementsByClass("srp-controls__count-heading");

			//Printing the message for the number of results for the item
			System.out.println(numResults.text());
			Pattern r = Pattern.compile("^([0-9],?)+");
			Matcher m = r.matcher(numResults.text());

			if (m.find( )) {
				int numListings = Integer.parseInt(m.group(0).replaceAll(",", ""));
				System.out.println(numListings);
				this.numListings += numListings;
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
						listingSearchResults.add(scrapeListingEBay(href));
						
					}
				}

			}

			// In case of any IO errors, we want the messages written to the console
		} catch (IOException e) {
			e.printStackTrace();
		}
		return listingSearchResults;
	}

	/**
	 * This method scrapes listings from EBBay
	 * 
	 * @param productURL The URL for the product listing
	 */
	private ProductListing scrapeListingEBay(String productURL) {
		return null;
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
