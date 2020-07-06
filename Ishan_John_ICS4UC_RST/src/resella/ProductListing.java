package resella;

/**
 * @author Ishan Baliyan and John Wolf
 * Date June 2020
 * Course: ICS4U
 * ProductListing.java
 * Responsible for representing each product listing and its accompanying data
 */

import java.util.ArrayList;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProductListing extends RecursiveTreeObject<ProductListing> {
	
	//Variables for the characteristics of the product listings
	private StringProperty imageURL;
	private StringProperty title;
	private ObjectProperty<ProductLink> listingURL;
	private DoubleProperty price;
	private DoubleProperty profit;
	private static DoubleProperty sellPrice;
	private ObservableList<String> tags;
	private StringProperty listingType;
	private StringProperty marketplace;
	private boolean isSold;
	private StringProperty orderMethod;
	private StringProperty location;
	
	// Useful constants
	public static final String BUY_IT_NOW_LISTING = "Buy It Now";
	public static final String AUCTION_LISTING = "Auction";
	public static final String EBAY = "eBay";
	public static final String KIJIJI = "Kijiji";
	public static final String CRAIGSLIST = "Craiglist";
	public static final String FACEBOOK_MARKETPLACE = "Facebook Marketplace";
	
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
	 * listingType: BUY_IT_NOW_LISTING
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
	 * @param listingType  the listing type: (Buy it Now or Auction listings)
	 * @param marketplace  the marketplace where the listing can be found
	 * @param tags
	 */
	public ProductListing (String imageURL, double newPrice, String orderMethod, String location, String title, String listingURL, String listingType, String marketplace, ArrayList<String> tags) {
		// Initialize all the variables
		this.imageURL = new SimpleStringProperty(imageURL);
		this.price = new SimpleDoubleProperty(newPrice);
		this.orderMethod = new SimpleStringProperty(orderMethod);
		this.location = new SimpleStringProperty(location);
		this.title = new SimpleStringProperty(title);
		this.listingURL = new SimpleObjectProperty<ProductLink>(new ProductLink(title, listingURL));
		this.listingType = new SimpleStringProperty(listingType);
		this.marketplace = new SimpleStringProperty(marketplace);
		this.tags =  FXCollections.observableArrayList(tags);
		
		setSellPrice(newPrice);
		sellPrice.addListener((observable, oldValue, newValue) -> {
			profit = new SimpleDoubleProperty((sellPrice.getValue() / price.getValue()) * 100.0 + 100.0);
		});
		
		profit = new SimpleDoubleProperty((sellPrice.getValue() / price.getValue()) * 100.0 + 100.0);
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
	 * Sets the listing's price, updates the profit
	 * @param price (double) the new price of the listing
	 */
	public void setPrice(double price) {
		this.price = new SimpleDoubleProperty(price);
		this.profit = new SimpleDoubleProperty(ProductListing.sellPrice.getValue() - this.price.getValue());
	}
	
	/**
	 * Retrieves the listing's profit
	 * @return profit (DoubleProperty) the profit of the listing
	 */
	public DoubleProperty getProfit() {
		return profit;
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
	 * @return listingType  the listing's type (StringProperty) (see class constants)
	 */
	public StringProperty getListingType() {
		return listingType;
	}

	/**
	 * Sets the listingType
	 * @param listingType (String) (see class constants)
	 */
	public void setListingType(String listingType) {
		this.listingType = new SimpleStringProperty(listingType);
	}

	/**
	 * Retrieves the listing's marketplace
	 * @return marketplace (StringProperty) (see class constants)
	 */
	public StringProperty getMarketplace() {
		return marketplace;
	}

	/**
	 * Sets the the listing's marketplace
	 * @param marketplace  the marketplace to set (String) (see class constants)
	 */
	public void setMarketplace(String marketplace) {
		this.marketplace = new SimpleStringProperty(marketplace);
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
	 * Retrieves the listing's sell price
	 * @return sellPrice (double) the sell price of the listing
	 */
	public static double getSellPrice() {
		return sellPriceProperty().get();
	}
	
	/**
	 * Sets the listing's sell price, updates the profit
	 * @param sellPrice (double) the new sell price of the listing
	 */
	public static void setSellPrice(double sellPrice) {
		sellPriceProperty().set(sellPrice);
	}
	
	
	/**
	 * Method that check if the sell price is empty (null), then assigns a value as necessary
	 * 
	 * @return sellPrice (DoubleProperty) the sell price of the listing
	 */
	public static DoubleProperty sellPriceProperty() {
		if(sellPrice == null) {
			sellPrice = new SimpleDoubleProperty(0);
		}
		
		return sellPrice;
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
