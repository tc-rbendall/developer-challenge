package com.truecommerce.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.*;
import com.truecommerce.controller.Globals;

@JsonInclude (JsonInclude.Include.NON_EMPTY)

public class Product {
    @JsonProperty ("code")
    private String code;
    @JsonProperty ("title")
    private String title;
    @JsonProperty ("kcal_per_100g")
    private Float kCal;
    @JsonProperty ("unit_price")
    private Float unitPrice;
    @JsonProperty ("description")
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
        if (unitPrice != null) output += "\"unit_price\": " + String.format("%. 2f", unitPrice) + ",\n";
        if (!description.equals("")) output += "\"description\": \"" + description + "\"\n"; // no trailing comma
        */

        /*
        // spec only allows for optionally omitting kcal
        output += "\"title\": \"" + title + "\",\n";
        output += "\"code\": \"" + code + "\",\n";
        if (kCal != null) output += "\"kcal_per_100g\": " + kCal.toString() + ",\n";
        output += "\"unit_price\": " + String.format("%.2f", unitPrice) + ",\n";
        output += "\"description\": \"" + description + "\"\n"; // no trailing comma

        output += "}";
        */

        if (Globals.DebugEnabled) System.out.println("Product.renderAsJSon() : " + output);


        ObjectMapper mapper = new ObjectMapper();
        String json = "";
        try {
            ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
            json = writer.writeValueAsString(this);
            if (Globals.DebugEnabled) System.out.println("Product.renderAsJSon() : " + json);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return json; // output
    }

    public String getCode() {
        return code;
    }

    public Float getUnitPrice() {
        return unitPrice;
    }
}

