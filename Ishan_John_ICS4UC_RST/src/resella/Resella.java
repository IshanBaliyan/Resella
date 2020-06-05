package resella;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Resella {

	public static void main(String[] args) {
		String productURL = "https://www.ebay.com/itm/HO-1-87-Ricko-38268-Dodge-Charger-Police-Car-Black-White/293518309147?hash=item44570df71b:g:olUAAOSwwmpedDkP";
		try {
			// Here we create a document object and use JSoup to fetch the website
			Document doc = Jsoup.connect(productURL).get();
			
			Elements titleElement = doc.getElementsByClass("it-ttl");
			String title = titleElement.text().replaceFirst("Details about ", "");

			Element priceElement = doc.getElementById("vi-mskumap-none");
			String price = priceElement.text().replaceFirst("Details about ", "");
			
			Element imageElement = doc.getElementById("icImg");
			String imgSource = imageElement.attr("src"); 
			
			Elements shippingPriceElement = doc.getElementsByClass("u-flL sh-col");
			Pattern r = Pattern.compile("[0-9]+\\.[0-9]+");
			Matcher m = r.matcher(shippingPriceElement.text());
			
			double shippingPrice = 0;
			if (m.find( )) {
				shippingPrice = Double.parseDouble(m.group(0).replaceAll(",", ""));
			}
			//String shippingPrice = shippingPriceElement.text();
			
			//u-flL sh-col
			
			System.out.println(price);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
