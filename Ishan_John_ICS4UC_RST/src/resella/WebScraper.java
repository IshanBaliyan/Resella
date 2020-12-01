package resella;

/**
 * @author Ishan Baliyan and John Wolf
 * Date June 2020
 * Course: ICS4U
 * WebScraper.java
 * Scrapes the web for product listings in different marketplaces
 */
import java.io.IOException;
import java.net.SocketTimeoutException;
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
	private ArrayList<ProductListing> activeAdListings = new ArrayList<ProductListing>();
	private ArrayList<ProductListing> soldAdListings = new ArrayList<ProductListing>();

	/**
	 * Constructor for adding keywords
	 * 
	 * @param keywords The keywords or the "search text" that the user is using to
	 *                 search the item on the program
	 */
	public WebScraper(String keywords) {
		setKeyWords(keywords);
	}

	/**
	 * Blank Constructor
	 */
	public WebScraper() {
	}

	/**
	 * Method that scrapes the listings
	 */
	public void scrapeListings() {

		try {
			// item results URL declared and assigned to variable from keywords searched by
			// the user

			/*
			 * Commenting out for going straight to the sold listings
			 */
			String eBayResultsURL = "https://www.ebay.com/sch/i.html?_from=R40&_nkw=" + keywords;
			scrapeSearchResultsEBay(eBayResultsURL, true);
			scrapeSearchResultsEBay(eBayResultsURL, false);

			String activeKijijiResultsURL = "https://www.kijiji.ca/b-" + kijijiLocation + "/" + keywords + "/"
					+ kijijiSearchID + "?ad=offering" + "?dc=true";// + "&sort=priceAsc";
			scrapeSearchResultsKijiji(activeKijijiResultsURL);
		}

		// TODO Fix timeout exception
		// catch(SocketTimeoutException e) {
		//
		//
		// }
		catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * This method scrapes search results on EBay
	 * 
	 * @param searchURL The search URL for the keywords
	 */
	private void scrapeSearchResultsEBay(String searchURL, boolean isActiveListings) {
		ArrayList<ProductListing> listingSearchResults = new ArrayList<ProductListing>();

		// Sets number of listings per page to 100 (changed to 40 for faster speed)
		searchURL += "&_ipg=40";

		// Based on active listings or sold listings,
		if (isActiveListings) {
			// Sorts listings from price lowest to highest (find cheapest listings)
			// searchURL += "&_sop=15";
		} else {
			// Filters sold listings only
			searchURL += "&LH_Sold=1";
		}

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

			// Retrieve the individual listings
			Elements searchItems = doc.getElementsByClass("s-item__wrapper clearfix");

			int counter = 0;
			// Canadian repo code:rsHdr (not used here but for future reference)
			for (Element searchItem : searchItems) {
				Element sponsoredElement = searchItem.getElementsByClass("s-item__title--tagblock").first();
				if (sponsoredElement == null || isActiveListings == false) {
					Element imageElement = searchItem.getElementsByClass("s-item__image").first();

					counter++;
					Element link = imageElement.select("a[href]").first();
					String href = link.attr("href");
					if (!href.isEmpty()) {

						// get the value from the href attribute
						System.out.println("\nlink " + counter + ": " + href);
						scrapeListingEBay(href, isActiveListings);
					}
				}

			}
			// In case of any IO errors, we want the messages written to the console
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method scrapes listings from EBBay
	 * 
	 * @param productURL The URL for the product listing
	 */
	private boolean scrapeListingEBay(String productURL, boolean isActiveListings) {
		ProductListing scrapedListing = new ProductListing();
		ArrayList<String> tags = new ArrayList<String>();
		String listingType = ProductListing.BUY_IT_NOW_LISTING;

		boolean isSuccessful = true;
		try {

			// Here we create a document object and use JSoup to fetch the website
			Document doc = Jsoup.connect(productURL).get();

			// Scrape title:
			Elements titleElement = doc.getElementsByClass("it-ttl");
			String title = titleElement.text().replaceFirst("Details about ", "");

			String[] listingLinkClasses = { "nodestar-item-card-details__view", "vi-inl-lnk vi-cvip-prel5",
					"vi-inl-lnk vi-original-listing", "ogitm", "u-flL w29 vi-price" };

			for (int i = 0; i < listingLinkClasses.length; i++) {
				Elements linkElements = doc.getElementsByClass(listingLinkClasses[i]);

				for (Element linkElement : linkElements) {
					if (linkElement != null) {
						Elements originalListingLink = linkElement.select("a[href]");

						for (Element link : originalListingLink) {

							String href = link.attr("href");

							if (!href.isEmpty()) {

								// get the value from the href attribute
								return scrapeListingEBay(href, isActiveListings);
							}
						}
					}

				}

			}

			Element priceElement = null;
			double price = 0;

			String[] priceIDs = { "prcIsum", "prcIsum_bidPrice", "mm-saleDscPrc" };

			for (int i = 0; i < priceIDs.length; i++) {
				priceElement = doc.getElementById(priceIDs[i]);

				if (priceElement != null) {
					if (priceIDs[i].equals("prcIsum_bidPrice")) {
						listingType = ProductListing.AUCTION_LISTING;
					}
					break;
				}

			}

			if (priceElement == null) {
				isSuccessful = false;
			} else {
				String fullPriceStr = priceElement.text();
				Pattern pricePattern = Pattern.compile("[0-9]+\\.[0-9]+");
				Matcher priceMatcher = pricePattern.matcher(fullPriceStr.replace(",", ""));
				if (priceMatcher.find()) {

					Pattern currencyPattern = Pattern.compile("^[A-Z]+");
					Matcher currencyMatcher = currencyPattern.matcher(fullPriceStr);
					currencyMatcher.find();
					String currency = currencyMatcher.group(0);

					if (currency.equals("US") == false) {
						Element convPriceElement = doc.getElementById("convbinPrice");
						if (convPriceElement == null) {
							convPriceElement = doc.getElementById("convbidPrice");
						}
						fullPriceStr = convPriceElement.text();
					}
					
					//TODO - fix price formatting for certain situations with formatting (temporary solution is below)
					try {
						price = Double.parseDouble(fullPriceStr.replaceAll("[a-z]+|[A-Z]+|[\\$ ,\\(\\)]", ""));
					}
					catch (NumberFormatException e) {
						price = 999999;
						
					}
			
				}

				// Scrape image URL
				Element imageElement = doc.getElementById("icImg");
				String imgURL = imageElement.attr("src");

				// Scrape shipping price

				Elements shippingPriceElement = doc.getElementsByClass("u-flL sh-col");
				String shippingPriceStr = shippingPriceElement.text();
				Pattern shippingPricePattern = Pattern.compile("^[A-Z]+");
				Matcher shippingPriceMatcher = shippingPricePattern.matcher(shippingPriceStr);

				if (shippingPriceMatcher.find() && shippingPriceStr != null) {
					String currency = shippingPriceMatcher.group(0);
					if (currency.equals("US") == false) {
						//TODO - fix problem with unreadable shipping prices from terrible formatting (a more temporary try catch situation is used below)
						try {
						shippingPriceStr = doc.getElementById("convetedPriceId").text();
						}
						catch(NullPointerException e) {
							shippingPriceStr = "0";

						}
					}
				}

				// Reformat shipping price to a double
				double shippingPrice = 0;
				
				try {
				shippingPrice = Double.parseDouble(shippingPriceStr.replaceAll("[A-Z]+|[\\$ ,]", ""));
				}
				//TODO - fix problem when shipping price is in an unreadable (a more temporary solution is below)
				catch (NumberFormatException e){
					shippingPrice = 0;
				}

				// Scrape the location of the listing
				Elements availableLocation = doc.getElementsByAttributeValue("itemprop", "availableAtOrFrom");
				String location = availableLocation.text();

				// Create scrapedListing
				scrapedListing = new ProductListing(imgURL, price, shippingPriceStr, location, title, productURL,
						listingType, ProductListing.EBAY, tags);

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (isSuccessful && isActiveListings) {
			scrapedListing.setIsSold(false);
			activeAdListings.add(scrapedListing);
		} else if (isSuccessful && !isActiveListings) {
			scrapedListing.setIsSold(true);
			soldAdListings.add(scrapedListing);
		}

		return isSuccessful;
	}

	/**
	 * This method scrapes search results from kjiji
	 * 
	 * @param searchURL The search URL for the user's keywords
	 */
	private void scrapeSearchResultsKijiji(String searchURL) {
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
			int counter = 1;

			for (Element searchItem : searchItems) {

				Element link = searchItem.select("a[href]").first();
				String href = link.attr("href");

				if (!href.isEmpty()) {

					if (href.contains("https:")) {
						System.out.println("\nlink " + counter + ": " + href);
						System.out.println("Cannot access links that are not from https://www.kijiji.ca");
					} else {
						System.out.println("\nlink " + counter + ": " + "https://www.kijiji.ca" + href);

						// get the value from the href attribute
						scrapeListingKijiji("https://www.kijiji.ca" + href);
					}
					counter++;
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This method scrapes listings from kijiji
	 * 
	 * @param productURL The URL for the product listing
	 */
	private boolean scrapeListingKijiji(String productURL) {
		ProductListing scrapedListing = new ProductListing();
		ArrayList<String> tags = new ArrayList<String>();
		boolean isSuccessful = true;
		try {

			Document listing = Jsoup.connect(productURL).get();

			// Scrape title:
			Element titleELement = listing.getElementsByClass("title-2323565163").first();
			String title;
			
			try {
				title = titleELement.text();
			}
			catch(NullPointerException e) {
				title = "No Name Found";
			}
			// Scrape price:
			Element priceElement = listing.getElementsByClass("currentPrice-2842943473").first();
			String priceStr;
			try {
				priceStr= priceElement.text().replaceAll("[\\$,]", "").replace("Free", "0");
			}
			catch(NullPointerException e) {
				priceStr = "0";
				isSuccessful = false;
			}
			// Price of the product
			double price = 0;

			// Using regix to filter out unwanted information from the variable
			Pattern r = Pattern.compile("[0-9]+\\.[0-9]{2}");
			Matcher m = r.matcher(priceStr);

			// If there is no price, just skip the listing (please contact means you have to
			// contact for the price)
			if (priceStr.equals("Please Contact") || !m.find()) {

				// Checking if the web scraper was able to pull successful listings without a
				// "Please Contact" for the price
				isSuccessful = false;
			} else {
				priceStr = m.group();
				price = Double.parseDouble(priceStr);
			}

			// Scrape image URL
			String imageURL = "";
			try {
				Element presentation = listing.getElementById("mainHeroImage");
				Element image = presentation.getElementsByTag("img").first();
				imageURL = image.attr("src");
			} catch (NullPointerException e) {
				System.out.println("No image found");
				isSuccessful = false;
			}

			// Can't ship orders for kijii
			String orderMethod = "PICK UP ONLY";

			// Scrape the location of the listing
			Elements availableLocation = listing.getElementsByAttributeValue("itemprop", "address");
			String location = availableLocation.text();

			// Create scrapedListing
			scrapedListing = new ProductListing(imageURL, price, orderMethod, location, title, productURL,
					ProductListing.BUY_IT_NOW_LISTING, ProductListing.KIJIJI, tags);

			// In case of any IO errors, we want the messages written to the console
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (isSuccessful) {
			activeAdListings.add(scrapedListing);
		}

		return isSuccessful;
	}

	/**
	 * Get the active listings
	 * 
	 * @return (ArrayList<ProductListing>) The active listings
	 */
	public ArrayList<ProductListing> getActiveAdListings() {
		return activeAdListings;
	}

	/**
	 * Get the sold listings
	 * 
	 * @return (ArrayList<ProductListing>) The sold listings
	 */
	public ArrayList<ProductListing> getSoldAdListings() {
		return soldAdListings;
	}

	/**
	 * Set the keywords
	 * 
	 * @param keywords (String) The keywords to set to
	 */
	public void setKeyWords(String keywords) {
		this.keywords = keywords;
	}
}
