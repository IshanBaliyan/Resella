package resella;

import java.util.ArrayList;

import simpleIO.Console;

public class AdListingTable {

	private ArrayList<ProductListing> soldListings = new ArrayList<ProductListing>();
	private double averageSellPrice = 0;

	public AdListingTable(ArrayList<ProductListing> soldListings) {
		this.soldListings = soldListings;
	}
	
	public void calculateAverageSellPrice() {
		
		double average = 0;
		
		for (int i = 0; i < soldListings.size(); i++) {
			average += soldListings.get(i).getPrice().getValue();	
		}
		average /= soldListings.size();
		averageSellPrice = Double.parseDouble(Console.roundDouble(average, 2));
	}
	
	public void filterSoldListings(String filterStr){
		
		//Filtering out given String text from all the listings information
		soldListings.removeIf(listing -> !listing.getTitle().toString().contains(filterStr));
		soldListings.removeIf(listing -> !listing.getLocation().toString().contains(filterStr));
		soldListings.removeIf(listing -> !listing.getOrderMethod().toString().contains(filterStr));
		soldListings.removeIf(listing -> listing.getTags().contains(filterStr));
		soldListings.removeIf(listing -> !listing.getMarketplace().toString().contains(filterStr));
		soldListings.removeIf(listing -> !listing.getListingType().toString().contains(filterStr));
		
	}
	
	public ArrayList<ProductListing> getSoldListings() {
		return soldListings;
	}

	public void setSoldListings(ArrayList<ProductListing> soldListings) {
		this.soldListings = soldListings;
	}
	
	public double getAverageSellPrice() {
		return averageSellPrice;
	}

	public void setAverageSellPrice(double averageSellPrice) {
		this.averageSellPrice = averageSellPrice;
	}
	

}

