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

	// Declaring variables for use in class
	private int numListings;
	private String keywords;
	private String kijijiSearchID = "k0l9004";
	private String kijijiLocation = "ontario";
	private ArrayList<ProductListing> activeAdListings;
	private ArrayList<ProductListing> soldAdListings;

	/**
	 * Constructor for adding keywords
	 * 
	 * @param keywords The keywords or the "search text" that the user is using to
	 *                 search the item on the program
	 */
	public WebScraper(String keywords) {
		this.keywords = keywords;
	}

	/**
	 * Method that scrapes the listings
	 */
	public void scrapeListings() {
		// item results URL declared and assigned to variable from keywords searched by
		// the user
		String activeEBayResultsURL = "https://www.ebay.com/sch/i.html?_from=R40&_nkw=" + keywords + "&_sop=15";
		activeAdListings.addAll(scrapeSearchResultsEBay(activeEBayResultsURL));

		String soldEBayResultsURL = "https://www.ebay.com/sch/i.html?_from=R40&_nkw=" + keywords + "&LH_Sold=1"
				+ "&_ipg=100" + "&_sop=16";
		soldAdListings.addAll(scrapeSearchResultsEBay(soldEBayResultsURL));

		String activeKijijiResultsURL = "https://www.kijiji.ca/b-" + kijijiLocation + "/" + keywords + "/"
				+ kijijiSearchID + "?dc=true" + "&sort=priceAsc";
		soldAdListings.addAll(scrapeSearchResultsKijiji(activeKijijiResultsURL));
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

			// Printing the message for the number of results for the item
			System.out.println(numResults.text());
			Pattern r = Pattern.compile("^([0-9],?)+");
			Matcher m = r.matcher(numResults.text());

			if (m.find()) {
				int numListings = Integer.parseInt(m.group(0).replaceAll(",", ""));
				System.out.println(numListings);
				this.numListings += numListings;
			}

			// Get the list of repositories
			Elements searchItems = doc.getElementsByClass("s-item__image");

			// Canadian repo code:rsHdr

			for (Element searchItem : searchItems) {

				Elements links = searchItem.select("a[href]");
				for (Element link : links) {

					String href = link.attr("href");

					if (!href.isEmpty()) {

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
		ProductListing scrapedListing = new ProductListing();
		ArrayList<String> tags = new ArrayList<String>();
		try {
			// Here we create a document object and use JSoup to fetch the website
			Document doc = Jsoup.connect(productURL).get();

			Elements titleElement = doc.getElementsByClass("it-ttl");
			String title = titleElement.text().replaceFirst("Details about ", "");

			Element priceElement = doc.getElementById("vi-mskumap-none");
			double price = Double.parseDouble(priceElement.text().replaceFirst("Details about ", ""));

			Element imageElement = doc.getElementById("icImg");
			String imgSource = imageElement.attr("src"); 

			Elements shippingPriceElement = doc.getElementsByClass("u-flL sh-col");
			Pattern r = Pattern.compile("[0-9]+\\.[0-9]+");
			Matcher m = r.matcher(shippingPriceElement.text());

			double shippingPrice = 0;
			if (m.find( )) {
				shippingPrice = Double.parseDouble(m.group(0).replaceAll(",", ""));
			}
			
			// Create scrapedListing
			
			//scrapedListing = new ProductListing(imgSource, price, shippingPrice, title, productURL, ProductListing.BUY_IT_NOW_LISTING, ProductListing.EBAY, tags );
			
//			scrapedListing = new ProductListing(imgSource, price, title, productURL, 
//					ProductListing.BUY_IT_NOW_LISTING, ProductListing.EBAY, tags);


		} catch (IOException e) {
			e.printStackTrace();
		}

		return scrapedListing;
	}

	/**
	 * This method scrapes search results from kjiji
	 * 
	 * @param searchURL The search URL for the user's keywords
	 * @return 
	 */
	private ArrayList<ProductListing> scrapeSearchResultsKijiji(String searchURL) {
		ArrayList<ProductListing> listingSearchResults = new ArrayList<ProductListing>();
		// TODO: check what ?dc=true means
		// Here we create a document object and use JSoup to fetch the website
		Document doc;
		try {
			doc = Jsoup.connect(searchURL).get();

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

			for (Element searchItem : searchItems) {

				Elements links = searchItem.select("a[href]");
				for (Element link : links) {

					String href = link.attr("href");

					if (!href.isEmpty()) {

						// get the value from the href attribute
						listingSearchResults.add(scrapeListingKijiji("https://www.kijiji.ca" + href));
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listingSearchResults;
	}

	/**
	 * This method scrapes listings from kijiji
	 * 
	 * @param productURL The URL for the product listing
	 */
	private ProductListing scrapeListingKijiji(String productURL) {
		ProductListing scrapedListing = new ProductListing();
		ArrayList<String> tags = new ArrayList<String>();
		try {
			Document listing = Jsoup.connect(productURL).get();

			// Scrape title:
			Element titleELement = listing.getElementsByClass("title-2323565163").first();
			String title = titleELement.text();

			// Scrape price:
			Element priceElement = listing.getElementsByClass("currentPrice-2842943473").first();
			String priceStr = priceElement.text().replaceAll("[\\$,]", "");
			double price = Double.parseDouble(priceStr);

			// Scrape image URL
			Element presentation = listing.getElementById("mainHeroImage");
			Element image = presentation.getElementsByTag("img").first();
			String imageURL = image.attr("src");

			// Create scrapedListing
			scrapedListing = new ProductListing(imageURL, price, title, productURL,
					ProductListing.BUY_IT_NOW_LISTING, ProductListing.KIJIJI, tags);

			// In case of any IO errors, we want the messages written to the console
		} catch (IOException e) {
			e.printStackTrace();
		}
		return scrapedListing;
	}

	// private ArrayList<ProductListing> getActiveAdListings() {
	//
	//
	// }

	// private ArrayList<ProductListing> getSoldAdListings() {
	//
	//
	// }

}
