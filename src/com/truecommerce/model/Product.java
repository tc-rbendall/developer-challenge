package com.truecommerce.model;

import com.truecommerce.Globals;

public class Product {
    private String code;
    private String title;
    private Float kCal;
    private Float unitPrice;
    private String description;

    public Product(String _code, String _title, String _kCal, String _unitPrice, String _description) {
        code = _code;
        title = _title;
        try {
            kCal = Float.parseFloat(_kCal);
        } catch (Exception ex) {
            kCal = null;
            if (Globals.DebugEnabled) System.out.println("Error: Product.kCal, src = [" + _kCal + "]: " + ex.getMessage());
        }
        try {
            unitPrice = Float.parseFloat(_unitPrice);
        } catch (Exception ex) {
            unitPrice = null;
            if (Globals.DebugEnabled) System.out.println("Error: Product.unitPrice, src = [" + _unitPrice + "]: " + ex.getMessage());
        }
        description = _description;
    }

    public Product(Product newProduct) {
        code = newProduct.code;
        title = newProduct.title;
        kCal = newProduct.kCal;
        unitPrice = newProduct.unitPrice;
        description = newProduct.description;
    }

    // render this item out as a json string
    public String renderAsJSON() {
        String output = "{\n";

        /*
        if (!title.equals("")) output += "\"title\": \"" + title + "\",\n";
        if (!code.equals("")) output += "\"code\": \"" + code + "\",\n";
        if (kCal != null) output += "\"kcal_per_100g\": " + kCal.toString() + ",\n";
        if (unitPrice != null) output += "\"unit_price\": " + unitPrice.toString() + ",\n";
        if (!description.equals("")) output += "\"description\": \"" + description + "\"\n"; // no trailing comma
        */

        // spec only allows for optionally omitting kcal
        output += "\"title\": \"" + title + "\",\n";
        output += "\"code\": \"" + code + "\",\n";
        if (kCal != null) output += "\"kcal_per_100g\": " + kCal.toString() + ",\n";
        output += "\"unit_price\": " + unitPrice.toString() + ",\n";
        output += "\"description\": \"" + description + "\"\n"; // no trailing comma


        output += "}";

        if (Globals.DebugEnabled) System.out.println("Product.renderAsJSon() : " + output);

        return output;
    }

    public String getCode() {
        return code;
    }

    public Float getUnitPrice() {
        return unitPrice;
    }
}

