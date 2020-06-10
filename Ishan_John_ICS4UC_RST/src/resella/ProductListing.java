package resella;

/**
 * @author John Wolf
 * Date June 2020
 * Course: ICS4U
 * ProductListing.java
 * Responsible representing each product listing and its accompanying data
 */

import java.util.ArrayList;

import javafx.scene.image.Image;

public class ProductListing {
	private Image imgListing;
	private String title;
	private String listingURL;
	private double price;
	private ArrayList<String> tags;
	private int listingType;
	private int marketplace;
	private boolean isSold;
	private String orderMethod;
	private String location;
	
	
	// Useful constants
	public static final int BUY_IT_NOW_LISTING = 0;
	public static final int AUCTION_LISTING = 1;
	public static final int EBAY = 0;
	public static final int KIJIJI = 1;
	public static final int CRAIGSLIST = 2;
	public static final int FACEBOOK_MARKETPLACE = 3;
	
	public static final String DEFAULT_IMG = "https://ir.ebaystatic.com/cr/v/c1/ebay-logo-1-1200x630-margin.png";
	
	// TODO: Manage currency conversion
	
	/**
	 * Blank constructor, populates the fields with the following values
	 * Default values:
	 * imageURL: the ebay logo, https://ir.ebaystatic.com/cr/v/c1/ebay-logo-1-1200x630-margin.png
	 * price: 0.0
	 * orderMethod the method of ordering the listing (i.e. shipping with shipping price or pickup)
	 * location the location of the listing
	 * title: the String "UNSET"
	 * listingURL: eBya's home page, https://www.ebay.com/
	 * listingType: Buy It Now
	 * marketplace: EBAY
	 * tags
	 * 
	 */
	public ProductListing () {
		this("https://ir.ebaystatic.com/cr/v/c1/ebay-logo-1-1200x630-margin.png", 0.0, "UNSET", "UNSET", "UNSET", "https://www.ebay.com/", BUY_IT_NOW_LISTING, EBAY, new ArrayList<String>());
	}
	
	/**
	 * Creates a new product listing with the specified parameters
	 * @param imageURL  the URL of the image
	 * @param price  the listing price
	 * @param orderMethod the method of ordering the listing (i.e. shipping with shipping price or pickup)
	 * @param location the location of the listing
	 * @param title  the listing title
	 * @param listingURL  the listing's URL
	 * @param listingType  the listing type: 0 for Buy it Now, 1 for auction listings
	 * @param marketplace  the marketplace where the listing can be found (ex:
	 * @param tags
	 */
	public ProductListing (String imageURL, double price, String orderMethod, String location, String title, String listingURL, int listingType, int marketplace, ArrayList<String> tags) {
		// Initialize all the variables
		imgListing = new Image(imageURL);
		this.price = price;
		this.orderMethod = orderMethod;
		this.location = location;
		this.title = title;
		this.listingType = listingType;
		this.marketplace = marketplace;
		this.tags = tags;
	}
	
	/**
	 * Retrieves the listing's image
	 * @return imgListing (the Image)
	 */
	public Image getImgListing() {
		return imgListing;
	}

	/**
	 * Sets the listing's image 
	 * @param imgListingURL the URL of the new listing Image
	 */
	public void setImgListing(String imgListingURL) {
		imgListing = new Image(imgListingURL);
	}

	/**
	 * Retrieves the title of the listing
	 * @return title (String)
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title of the listing
	 * @param title (String) the new listing title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Retrieves the listing's URL
	 * @return listingURL  (String) the URL of the listing
	 */
	public String getListingURL() {
		return listingURL;
	}

	/**
	 * Sets the listings URL
	 * @param listingURL (String) the listing's new URL
	 */
	public void setListingURL(String listingURL) {
		this.listingURL = listingURL;
	}

	/**
	 * Retrieves the listing's price
	 * @return price (double) the price of the listing
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * Sets the listing's price
	 * @param price (double) the new price of the listing
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * Retrieves the listing's tags
	 * @return tags (ArrayList<String>) the listing's tags (ex: condition, seller username, seller rating, etc.)
	 */
	public ArrayList<String> getTags() {
		return tags;
	}

	/**
	 * Sets the listing's tags
	 * @param tags (ArrayList<String>)  the listing's new tags (ex: condition, seller username, seller rating, etc.)
	 */
	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}

	/**
	 * Retrieves listingType
	 * @return listingType (int) (see class constants)
	 */
	public int getListingType() {
		return listingType;
	}

	/**
	 * Sets the listingType
	 * @param listingType (int) (see class constants)
	 */
	public void setListingType(int listingType) {
		this.listingType = listingType;
	}

	/**
	 * Retrieves marketplace
	 * @return marketplace (int) (see class constants)
	 */
	public int getMarketplace() {
		return marketplace;
	}

	/**
	 * Sets the marketplace
	 * @param marketplace  the marketplace to set (int) (see class constants)
	 */
	public void setMarketplace(int marketplace) {
		this.marketplace = marketplace;
	}

	/**
	 * Retrieves isSold 
	 * @return isSold, boolean (true is sold, false is active)
	 */
	public boolean getIsSold() {
		return isSold;
	}

	/**
	 * Sets isSold (true is sold, false is active)
	 * @param isSold the new value of isSold
	 */
	public void setIsSold(boolean isSold) {
		this.isSold = isSold;
	}
}
