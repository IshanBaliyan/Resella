package resella;

/**
 * @author John Wolf
 * Date June 2020
 * Course: ICS4U
 * ProductListing.java
 * Responsible representing each product listing and its accompanying data
 */

import java.util.ArrayList;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProductListing extends RecursiveTreeObject<ProductListingObservable> {
	private StringProperty imageURL;
	private StringProperty title;
	private ObjectProperty<ProductLink> listingURL;
	private DoubleProperty price;
	private ObservableList<String> tags;
	private IntegerProperty listingType;
	private IntegerProperty marketplace;
	private boolean isSold;
	private StringProperty orderMethod;
	private StringProperty location;
	
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
	 * imageURL: the eBay logo, https://ir.ebaystatic.com/cr/v/c1/ebay-logo-1-1200x630-margin.png
	 * price: 0.0
	 * orderMethod the String "UNSET"
	 * location the String "UNSET"
	 * title: the String "UNSET"
	 * listingURL: eBay's home page, https://www.ebay.com/
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
		this.imageURL = new SimpleStringProperty(imageURL);
		this.price = new SimpleDoubleProperty(price);
		this.orderMethod = new SimpleStringProperty(orderMethod);
		this.location = new SimpleStringProperty(location);
		this.title = new SimpleStringProperty(title);
		this.listingURL = new SimpleObjectProperty<ProductLink>(new ProductLink(title, listingURL));
		this.listingType = new SimpleIntegerProperty(listingType);
		this.marketplace = new SimpleIntegerProperty(marketplace);;
		this.tags =  FXCollections.observableArrayList(tags);
	}
	
	/**
	 * Retrieves the listing image's image URL
	 * @return imageURL (the image)
	 */
	public StringProperty getImgURL() {
		return imageURL;
	}

	/**
	 * Sets the listing's image URLL 
	 * @param imageURL the URL of the new listing Image
	 */
	public void setImgURL(String imageURL) {
		this.imageURL = new SimpleStringProperty(imageURL);
	}

	/**
	 * Retrieves the title of the listing
	 * @return title (StringProperty)
	 */
	public StringProperty getTitle() {
		return title;
	}

	/**
	 * Sets the title of the listing
	 * @param title (StringProperty) the new listing title
	 */
	public void setTitle(StringProperty title) {
		this.title = title;
	}

	/**
	 * Retrieves the listing's URL
	 * @return listingURL  (ObjectProperty) the URL object of the listing
	 */
	public ObjectProperty<ProductLink> getListingURL() {
		return listingURL;
	}

	/**
	 * Sets the listing's URL
	 * @param productLinkURL (String) the listing's new URL
	 */
	public void setProductLinkURL(String productLinkURL) {
		listingURL.getValue().setLinkURL(productLinkURL);
	}
	
	/**
	 * Sets the listing's URL title
	 * @param productLinkURL (String) the listing's new URL title
	 */
	public void setProductLinkTitle(String productLinkTitle) {
		listingURL.getValue().setLinkTitle(productLinkTitle);
	}

	/**
	 * Retrieves the listing's price
	 * @return price (DoubleProperty) the price of the listing
	 */
	public DoubleProperty getPrice() {
		return price;
	}

	/**
	 * Sets the listing's price
	 * @param price (double) the new price of the listing
	 */
	public void setPrice(double price) {
		this.price = new SimpleDoubleProperty(price);
	}

	/**
	 * Retrieves the listing's order method
	 * @return orderMethod  (StringProperty) the order method of the listing
	 */
	public StringProperty getOrderMethod() {
		return orderMethod;
	}

	/**
	 * Sets the listing's orderMethod
	 * @param orderMethod (String) the listing's new orderMethod
	 */
	public void setOrderMethod(String orderMethod) {
		this.orderMethod = new SimpleStringProperty(orderMethod);
	}

	/**
	 * Retrieves the listing's location
	 * @return location  (StringProperty) the location of the listing
	 */
	public StringProperty getLocation() {
		return location;
	}

	/**
	 * Sets the listing's location
	 * @param location (String) the listing's new location
	 */
	public void setLocation(String location) {
		this.location = new SimpleStringProperty(location);
	}

	/**
	 * Retrieves the listing's tags
	 * @return tags (ObservableList<String>) the listing's tags (ex: condition, seller username, seller rating, etc.)
	 */
	public ObservableList<String> getTags() {
		return tags;
	}

	/**
	 * Sets the listing's tags
	 * @param tags (ArrayList<String>)  the listing's new tags (ex: condition, seller username, seller rating, etc.)
	 */
	public void setTags(ArrayList<String> tags) {
		this.tags =  FXCollections.observableArrayList(tags);
	}

	/**
	 * Retrieves the listing's listingType
	 * @return listingType  the listing's type (IntegerProperty) (see class constants)
	 */
	public IntegerProperty getListingType() {
		return listingType;
	}

	/**
	 * Sets the listingType
	 * @param listingType (int) (see class constants)
	 */
	public void setListingType(int listingType) {
		this.listingType = new SimpleIntegerProperty(listingType);
	}

	/**
	 * Retrieves the listing's marketplace
	 * @return marketplace (IntegerProperty) (see class constants)
	 */
	public IntegerProperty getMarketplace() {
		return marketplace;
	}

	/**
	 * Sets the the listing's marketplace
	 * @param marketplace  the marketplace to set (int) (see class constants)
	 */
	public void setMarketplace(int marketplace) {
		this.marketplace = new SimpleIntegerProperty(marketplace);
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
	
	/**
	 * Stores the properties associated with the listing's URL
	 * Used to set the hyperLink in the JFXTreeTableView
	 */
	public class ProductLink {
		
		private String linkTitle;
		private String linkURL;
		
		/**
		 * Creates a new ProductLink instance
		 * @param linkTitle  the ProductLink's title
		 * @param linkURL  the ProductLink's URL
		 */
		ProductLink(String linkTitle,String linkURL){
			 this.linkTitle = linkTitle;
			 this.linkURL = linkURL;
		}

		/**
		 * Retrieves the ProductLink's title
		 * @return linkTitle  (String) the ProductLink's title
		 */
		public String getLinkTitle() {
			return linkTitle;
		}
		
		/**
		 * Sets the the ProductLink's title
		 * @param linkTitle  the link's title to set (String)
		 */
		public void setLinkTitle(String linkTitle) {
			this.linkTitle = linkTitle;
		}
		
		/**
		 * Retrieves the ProductLink's URL
		 * @return linkURL  (String) the ProductLink's URL
		 */
		public String getLinkURL() {
			return linkURL;
		}
		
		/**
		 * Sets the the ProductLink's URL
		 * @param linkURL  the link's URL to set (String)
		 */
		public void setLinkURL(String linkURL) {
			this.linkURL = linkURL;
		}		
	}
}
