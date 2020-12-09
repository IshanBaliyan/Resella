![image](https://user-images.githubusercontent.com/43831507/100688976-8fa11c00-3351-11eb-9cea-e5cf2cb69e52.png)

# Resella

## This is a web scraper that scrapes websites, such as EBay and Kijiji, for listings of products and organizes them for the best deals.

The application was made as an assignment for the course ICS 4U (Grade 12 Computer Science) and the two authors are Ishan Baliyan and John Wolf. As seen by the program *insights* on GitHub, the project took several months to build. 

Note: We are developing an enhanced Version 2 of this program with Node.JS (in a private repo) as software for sale in a business. The following is Version 1 in java.

#**Overview:**# The program scrapes information for online products such as name, location, image, auction listing, link, price, shipping price and more (while also filtering out sponsored listings).
Then, the program allows the user to organize the information by calculating the average sell price of the product and determining which products are the best deal and below market-value.

Furthermore, individuals can use the program to find less expensive products for a lower price (since some people just want to get rid of their old stuff at a overly low price) and the user of the program could theortically RESELL it back on EBay/kijiji for a higher price.
Thus, the name of the application is Resella.

![image](https://user-images.githubusercontent.com/43831507/100687724-d04b6600-334e-11eb-99d9-f282f6096103.png)

The user can also filter certain information to get a desired listing that only have those features (e.g. preferred location for pickup, preferred product colour, preferred manufacturing date, preferred size, etc...)
![image](https://user-images.githubusercontent.com/43831507/100688399-40a6b700-3350-11eb-874f-748954ad93e1.png)

Below is an example of filtering "controller" when "Playstation 5" was the keyword searched on the program
![image](https://user-images.githubusercontent.com/43831507/100688065-7a2af280-334f-11eb-88d2-3334a46b66ef.png)

The user can also browse images of the products up close:
![image](https://user-images.githubusercontent.com/43831507/100687838-0b4d9980-334f-11eb-83b4-06dae67b484d.png)

The program can scrape as many products as you want (although the code has been set to a default of 40 products on each marketplace) and theoretically, someone could run the program and potentitally find extremely low price listings after filtering through thousands of products in different marketplaces and resell them back on the marketplace.

Note: The program was required to be submitted in Java and we used the JavaFx library to build the program. However, after submitting the program, we decided to navigate our program to JavaScript (Node.js) and are currently developing a final for-sale version of the software (private repo) as a Business.
