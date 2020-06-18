package resella;

import java.util.ArrayList;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import simpleIO.Console;

public class SoldListingsManager {

	private ArrayList<ProductListing> soldListings;
	private ArrayList<ProductListing> filteredListings;
	private DoubleProperty averageSellPrice;
	
	public SoldListingsManager() {
		
		filteredListings = new ArrayList<ProductListing>();
		this.soldListings = new ArrayList<ProductListing>();
	}
	
	public SoldListingsManager(ArrayList<ProductListing> soldListings) {
		
		filteredListings = new ArrayList<ProductListing>(soldListings);
		this.soldListings = soldListings;
	}
	
	public void calculateAverageSellPrice() {
		
		double average = 0;
		
		for (int i = 0; i < filteredListings.size(); i++) {
			average += filteredListings.get(i).getPrice().getValue();	
			
		}
		average /= filteredListings.size();
		setAverageSellPrice(Double.parseDouble(Console.roundDouble(average, 2)));
	}
	
	public void filterSoldListings(String filterStr){
		filteredListings = new ArrayList<ProductListing>(soldListings);
		//Filtering out given String text from all the listings information
		filteredListings.removeIf(listing -> 
			!(listing.getTitle().getValue().toLowerCase().contains(filterStr) || listing.getLocation().getValue().toLowerCase().contains(filterStr) ||
					listing.getOrderMethod().getValue().toLowerCase().contains(filterStr) || listing.getTags().contains(filterStr) || 
					listing.getLocation().getValue().toLowerCase().contains(filterStr))
		);
	}
	
	public void resetFilteredListings() {
		this.filteredListings = new ArrayList<ProductListing>(soldListings);
	}
	
	public ArrayList<ProductListing> getSoldListings() {
		return soldListings;
	}

	public void setSoldListings(ArrayList<ProductListing> soldListings) {
		this.soldListings = new ArrayList<ProductListing>(soldListings);
	}

	public double getAverageSellPrice() {
		return averageSellPriceProperty().get();
	}

	public void setAverageSellPrice(double averageSellPrice) {
		averageSellPriceProperty().set(averageSellPrice);
	}
	
	public DoubleProperty averageSellPriceProperty() {
		if(averageSellPrice == null) {
			averageSellPrice = new SimpleDoubleProperty(0.0);
		}
		return averageSellPrice;
	}

}

