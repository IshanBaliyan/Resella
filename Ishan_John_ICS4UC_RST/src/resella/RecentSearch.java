package resella;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

//Class not implemented in program yet
public class RecentSearch {
	private String keywords;
	private String searchDateTime;
//	private LocalDateTime searchDateTimeObj;
//	private DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("KK:mm a MM/dd/yyyy", Locale.ENGLISH);

	public RecentSearch() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @return the keywords
	 */
	public String getKeywords() {
		return keywords;
	}

	/**
	 * @param keywords the keywords to set
	 */
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	/**
	 * @return the searchDateTime
	 */
	public String getSearchDateTime() {
		return searchDateTime;
	}

	/**
	 * @param searchDateTimeStr the searchDateTime to set
	 */
	public void setSearchDateTime(String searchDateTime) {
		this.searchDateTime = searchDateTime;
//		searchDateTimeObj = LocalDateTime.parse(searchDateTimeStr, dateTimeFormat);
	}
	
	//TODO: Add toString() Override?

}
