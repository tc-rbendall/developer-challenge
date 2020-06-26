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
        } catch (Exception e) {
            kCal = null;
            if (Globals.DebugEnabled) System.out.println("Error: kCal, src = [" + _kCal + "]");
        }
        try {
            unitPrice = Float.parseFloat(_unitPrice);
        } catch (Exception e) {
            unitPrice = null;
            if (Globals.DebugEnabled) System.out.println("Error: unitPrice, src = [" + _unitPrice + "]");
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
        if (!code.equals("")) output += "\"code\": \"" + code + "\",\n";
        if (!output.equals("")) output += "\"title\": \"" + title + "\",\n";
        if (kCal != null) output += "\"kcal_per_100g\": " + kCal.toString() + ",\n";
        if (unitPrice != null) output += "\"unit_price\": " + unitPrice.toString() + ",\n";
        if (!description.equals("")) output += "\"description\": \"" + description + "\"\n"; // no trailing comma
        output += "}";

        if (Globals.DebugEnabled) System.out.println("item.renderAsJSon() : " + output);

        return output;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Float getkCal() {
        return kCal;
    }

    public void setkCal(Float kCal) {
        this.kCal = kCal;
    }

    public Float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

