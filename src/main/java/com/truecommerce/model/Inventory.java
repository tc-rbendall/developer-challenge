package com.truecommerce.model;

import com.fasterxml.jackson.annotation.*;//JsonProperty;
import com.truecommerce.controller.Globals;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class Inventory {
    private ArrayList<Product> products;
    static float VATRate = 0.2f;
    @JsonProperty("net")
    Float net;
    @JsonProperty ("gross")
    Float gross;
    @JsonProperty ("vat")
    Float vat;


    public int getProductCount() {
        return products.size();
    }

    public int addProduct(Product newProduct) {
        try {
            products.add(new Product(newProduct));
            gross += newProduct.getUnitPrice();
            vat += newProduct.getUnitPrice() * VATRate;
            net = gross / (1f + VATRate);
            net = (int)(100f * net + 0.5f) / 100f; // rounding following the divide

            if (Globals.DebugEnabled) System.out.println("Inventory.addProduct() : [" + newProduct.getCode() + "]");

            return 0;
        } catch (Exception ex){
            System.out.println("ERROR: Inventory.addProduct() : " + ex.getMessage());
            return -1;
        }
    }

    // outputs the entire item list and totals as a json string
    public String renderAsJSON() {
        String output = "{\n"; // opening structure
        output += "\"results\": [\n"; // container for the repeating group

        for (int n = 0; n < products.size(); n++) {
            output += products.get(n).renderAsJSON();
            if (n != products.size() -1) output += ","; // separating comma for all but last item block
            output += "\n";
        }
        output += "],\n"; // close the repeating group
        output += renderTotalsAsJSON(); // now the totals block
        output += "}\n"; // and we're done

        if (Globals.DebugEnabled) System.out.println("Inventory.renderAsJSON() : " + output);

        return output;
    }

    public String renderTotalsAsJSON() {
        String output = "";

        output = "\"total\": {\n";
        output += "\"net\": " + String.format("%.2f", net) + ",\n";
        output += "\"vat\": " + String.format("%.2f", vat) + ",\n";
        output += "\"gross\": " + String.format("%.2f", gross) + "\n";
        output += "}\n";

        if (Globals.DebugEnabled) System.out.println("Inventory.renderTotalsAsJSON() : " + output);

        return output;
    }

    public Inventory(String siteHomePage, String landingPage) {
        products = new ArrayList<Product>();
        net = gross = vat = 0f;

        int result = scrapeProducts(siteHomePage, landingPage);

        if (result == -1) System.out.println("Error: Inventory()");
    }

    // given details about a target site, go and get a product catalogue
    public int scrapeProducts(String siteHomePage, String landingPage) {
        int result = 0;

        // pull html source into a usable model
        System.out.print("Reading source from: " + siteHomePage + landingPage);
        try {
            Document doc = Jsoup.connect(siteHomePage + landingPage).get();
            System.out.println(" done.");
            System.out.print("Extracting product links...");
            Elements links = doc.select("a[href]"); // discovered after the fact that selecting within a <p> block would save all the de-dupe code below. d'oh!
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
            if (Globals.DebugEnabled) {
                for (String link : uniqueLinks) System.out.println("Unique link: " + link);
            }
            System.out.println(" done.");

            // loop through links, visit the pages and pull product details
            System.out.println("Reading products...");
            for (String link : uniqueLinks) {
                Document itemDoc = Jsoup.connect(siteHomePage + link).get();
                String code = itemDoc.select("span.productItemCode").text();
                String desc1 = itemDoc.select("p.productDescription1").text();
                String desc2 = itemDoc.select("span.productDescription2").text();
                String cal = itemDoc.select("span.productKcalPer100Grams").text();
                String price = itemDoc.select("span.productUnitPrice").text();

                if (addProduct(new Product(code, desc1, cal, price, desc2)) == 0) {
                    System.out.println("Added item to inventory: " + code);
                } else {
                    System.out.println("Error adding item to inventory: " + code);
                }
            }
        } catch (Exception ex) {
            System.out.println("Error: Inventory.scrapeProducts(): " + ex.getMessage());
        }

        result = getProductCount();

        System.out.println("Inventory scrape complete: " + result + " products added.");

        return result;
    }
}
