package resella;

import java.util.ArrayList;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import simpleIO.Console;

public class AdListingTable {

	private ArrayList<ProductListing> soldListings;
	private ArrayList<ProductListing> filteredListings;
	private DoubleProperty averageSellPrice;
	
	public AdListingTable(ArrayList<ProductListing> soldListings) {
		
		filteredListings = new ArrayList<ProductListing>(soldListings);
		this.soldListings = soldListings;
	}
	
	public void calculateAverageSellPrice() {
		
		double average = 0;
		
		for (int i = 0; i < filteredListings.size(); i++) {
			average += filteredListings.get(i).getPrice().getValue();	
			Console.print("Price (adding each to the total): " + filteredListings.get(i).getPrice().getValue());
			
		}
		average /= filteredListings.size();
		setAverageSellPrice(Double.parseDouble(Console.roundDouble(average, 2)));
	}
	
	public void filterSoldListings(String filterStr){
		filteredListings = new ArrayList<ProductListing>(soldListings);
		
		//Filtering out given String text from all the listings information
		filteredListings.removeIf(listing -> !listing.getTitle().toString().contains(filterStr));
		filteredListings.removeIf(listing -> !listing.getLocation().toString().contains(filterStr));
		filteredListings.removeIf(listing -> !listing.getOrderMethod().toString().contains(filterStr));
		filteredListings.removeIf(listing -> listing.getTags().contains(filterStr));
		filteredListings.removeIf(listing -> !listing.getMarketplace().toString().contains(filterStr));
		filteredListings.removeIf(listing -> !listing.getListingType().toString().contains(filterStr));
		
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

