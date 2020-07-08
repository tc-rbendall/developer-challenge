package com.truecommerce.controller;

import com.truecommerce.model.Inventory;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main {

    public static void main(String[] args) throws URISyntaxException, IOException {

        /*
        TO-DO: (but ran out of time)
        - Complete objectmapper usage for entire Inventory (only managed the Product)
        - Abstract out the JSON generation to complete the View bit of the MVC model
        - Abstract out constant strings used in Jsoup calls to allow re-use for other sites with different HTML models
        - Replace/add exception handling logic to avoid/allow call-stack abuse
        - Pander to Nige's love of the hated BigDecimal class in place of floats
        */

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
