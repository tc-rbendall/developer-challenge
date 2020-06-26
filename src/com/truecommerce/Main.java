package com.truecommerce;

import com.truecommerce.model.Inventory;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main {

    public static void main(String[] args) throws URISyntaxException, IOException {

        Globals.DebugEnabled = false;

        System.out.println("Devchallenge-001\n");

        String siteBase = "";
        String landingPage = "";


        if (args.length == 2) {
            siteBase = args[0];
            landingPage = args[1];
        } else {
            siteBase = "http://devtools.truecommerce.net:8080";
            landingPage = "/challenge001/";
        }

        Inventory productInventory = new Inventory(siteBase, landingPage);

        System.out.println("\n\nInventory output:\n");
        System.out.println(productInventory.renderAsJSON());
    }
}
