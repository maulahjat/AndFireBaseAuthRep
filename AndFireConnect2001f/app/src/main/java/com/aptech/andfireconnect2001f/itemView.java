package com.aptech.andfireconnect2001f;

class itemView {
    String ID;
    String ItemName;
    String Quantity;
    String Rate;

    public itemView(String ID, String itemName, String quantity, String rate) {
        this.ID = ID;
        ItemName = itemName;
        Quantity = quantity;
        Rate = rate;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
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
