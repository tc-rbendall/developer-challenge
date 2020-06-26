package com.truecommerce.model;

import com.truecommerce.Globals;

import java.util.ArrayList;

public class Inventory {
    private ArrayList<Product> products;
    static float VATRate = 0.2f;
    Float net;
    Float gross;
    Float vat;

    public Inventory() {
        products = new ArrayList<Product>();
        net = gross = vat = 0f;
    }

    public int getProductCount() {
        return products.size();
    }

    public int addProduct(Product newProduct) {
        try {
            products.add(new Product(newProduct));
            gross += newProduct.getUnitPrice();
            vat += newProduct.getUnitPrice() * VATRate;
            net = gross / (1.0f + VATRate);

            if (Globals.DebugEnabled) System.out.println("Inventory.addProduct() : [" + newProduct.getCode() + "]");

            return 0;
        } catch (Exception e){
            System.out.println("ERROR: addProduct() : " + e.getMessage());
            return -1;
        }
    }

    public String renderAsJSON() {
        // outputs the entire item list and totals as a json string
        String output = "";

        output = "{\n"; // opening structure
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
        // outputs the total fields
        String output = "";

        output = "\"total\": {\n";
        output += "\"net\": " + net + ",\n";
        output += "\"vat\": " + vat + ",\n";
        output += "\"gross\": " + gross + "\n";
        output += "}\n";

        if (Globals.DebugEnabled) System.out.println("Inventory.renderTotalsAsJSON() : " + output);

        return output;
    }
}
