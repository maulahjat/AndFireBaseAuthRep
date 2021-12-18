package com.aptech.andfireconnect2001f;

class item {
    String ItemName;
    String Quantity;
    String Rate;

    public item(String itemName, String quantity, String rate) {
        ItemName = itemName;
        Quantity = quantity;
        Rate = rate;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getRate() {
        return Rate;
    }

    public void setRate(String rate) {
        Rate = rate;
    }
}
