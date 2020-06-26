package com.truecommerce;

import com.truecommerce.model.Inventory;
import com.truecommerce.model.Product;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws URISyntaxException, IOException {

        Globals.DebugEnabled = false;
        Inventory productInventory = new Inventory();
        String siteBase = "http://devtools.truecommerce.net:8080";
        String landingPage = "/challenge001/";

        System.out.println("Devchallenge-001\n");

        // pull html source into a usable model
        System.out.print("Reading source from: " + siteBase + landingPage);
        Document doc = Jsoup.connect(siteBase + landingPage).get();
        System.out.println(" done.");
        System.out.print("Extracting product links...");
        Elements links = doc.select("a[href]");
        System.out.println(" done.");

        // ensure we don't duplicate the links we'll follow
        System.out.print("Removing duplicate links...");
        ArrayList<String> uniqueLinks = new ArrayList<String>();
        String href = "";
        boolean isUnique;
        for (Element link : links) {
            href = link.attr("href");
            isUnique = true;
            for (String s : uniqueLinks) {
                if (s.equals(href)) {
                    isUnique = false;
                    break;
                }
            }
            if (isUnique) uniqueLinks.add(href);
        }
        if (Globals.DebugEnabled) { for (String link : uniqueLinks) System.out.println("Unique link: " + link); }
        System.out.println(" done.");

        // loop through links, visit the pages and pull product details
        System.out.println("Reading products...");
        int result = 0;
        for (String link : uniqueLinks) {
            Document itemDoc = Jsoup.connect(siteBase + link).get();
            String code = itemDoc.select("span.productItemCode").text();
            String desc1 = itemDoc.select("p.productDescription1").text();
            String desc2 = itemDoc.select("span.productDescription2").text();
            String cal = itemDoc.select("span.productKcalPer100Grams").text();
            String price = itemDoc.select("span.productUnitPrice").text();

            if (productInventory.addProduct(new Product(code, desc1, cal, price, desc2)) == 0) {
                System.out.println("Added item to inventory: " + code);
            } else {
                System.out.println("Error adding item to inventory: " + code);
            }
        }
        System.out.println("Inventory complete: " + productInventory.getProductCount() + " products added.");

        System.out.println("\n\nInventory output:\n");
        System.out.println(productInventory.renderAsJSON());
    }
}
