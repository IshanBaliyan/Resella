package resella;
/**
 * @author Ishan Baliyan and John Wolf
 * Date June 2020
 * Course: ICS4U
 * SoldListingsManager.java
 * Responsible for managing sold listings for aiding in tasks such as finding average sell price and filtering listings
 */
import java.util.ArrayList;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import simpleIO.Console;

public class SoldListingsManager {

	private ArrayList<ProductListing> soldListings;
	private ArrayList<ProductListing> filteredListings;
	private DoubleProperty averageSellPrice;
	
	/**
	 * Blank Constructor
	 */
	public SoldListingsManager() {
		
		filteredListings = new ArrayList<ProductListing>();
		this.soldListings = new ArrayList<ProductListing>();
	}
	
	
	/**
	 * Constructor for filtering sold listings
	 * 
	 * @param soldListings ArrayList with the sold listings
	 */
	public SoldListingsManager(ArrayList<ProductListing> soldListings) {
		
		filteredListings = new ArrayList<ProductListing>(soldListings);
		this.soldListings = soldListings;
	}
	
	/**
	 * Calculate the average price of the sold listings (including filtered keywords)
	 */
	public void calculateAverageSellPrice() {
		
		double average = 0;
		
		for (int i = 0; i < filteredListings.size(); i++) {
			average += filteredListings.get(i).getPrice().getValue();	
			
		}
		average /= filteredListings.size();
		setAverageSellPrice(Double.parseDouble(Console.roundDouble(average, 2)));
	}
	
	/**
	 * Filter the sold listings with what information the user wants to filter
	 * 
	 * @param filterStr The information the user wants to filter
	 */
	public void filterSoldListings(String filterStr){
		filteredListings = new ArrayList<ProductListing>(soldListings);
		//Filtering out given String text from all the listings information
		filteredListings.removeIf(listing -> 
			!(listing.getTitle().getValue().toLowerCase().contains(filterStr) || listing.getLocation().getValue().toLowerCase().contains(filterStr) ||
					listing.getOrderMethod().getValue().toLowerCase().contains(filterStr) || listing.getTags().contains(filterStr) || 
					listing.getLocation().getValue().toLowerCase().contains(filterStr))
		);
	}
	
	/**
	 * Reset the filtered listings to their old version with old listings
	 */
	public void resetFilteredListings() {
		this.filteredListings = new ArrayList<ProductListing>(soldListings);
	}
	
	/**
	 * Get the sold listings
	 * 
	 * @return ArrayList<ProductListing> of the sold listings
	 */
	public ArrayList<ProductListing> getSoldListings() {
		return soldListings;
	}

	/**
	 * Set the sold listings
	 * 
	 * @param soldListings (ArrayList<ProductListing>) The soldListings to set to
	 */
	public void setSoldListings(ArrayList<ProductListing> soldListings) {
		this.soldListings = new ArrayList<ProductListing>(soldListings);
	}

	/**
	 * Get the average sell price of the sold listings (provides sold filters listings as needed)
	 * 
	 * @return
	 */
	public double getAverageSellPrice() {
		return averageSellPriceProperty().get();
	}

	/**
	 * Set average sell price of the listing
	 * 
	 * @param averageSellPrice (double) The average sell price of the listing
	 */
	public void setAverageSellPrice(double averageSellPrice) {
		averageSellPriceProperty().set(averageSellPrice);
	}
	
	/**
	 * Use average sell price property
	 * 
	 * @return (DoubleProperty) The average sell price
	 */
	public DoubleProperty averageSellPriceProperty() {
		if(averageSellPrice == null) {
			averageSellPrice = new SimpleDoubleProperty(0.0);
		}
		return averageSellPrice;
	}

}

