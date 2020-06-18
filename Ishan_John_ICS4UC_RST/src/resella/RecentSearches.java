package resella;

import java.util.ArrayList;

//Class not implemented in program yet
public class RecentSearches {
	private ArrayList<RecentSearch> recentSearches = new ArrayList<RecentSearch>();

	public RecentSearches() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * @return the recentSearches
	 */
	public ArrayList<RecentSearch> getRecentSearches() {
		return recentSearches;
	}

	/**
	 * @param recentSearches the recentSearches to set
	 */
	public void setRecentSearches(ArrayList<RecentSearch> recentSearches) {
		this.recentSearches = recentSearches;
	}
	
	// TODO: Add toString() Override?
}
