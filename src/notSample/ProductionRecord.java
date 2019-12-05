package notSample;

import java.util.Date;

public class ProductionRecord {
    int productionNum;
    int productID;
    String serialNum;
    Date prodDate;
    String productName;

    /**
     * Class constructor
     * @param productID
    * */
    public ProductionRecord(int productID) {
        this.productID = productID;
        this.productionNum = 0;
        this.serialNum = "0";
        this.prodDate = new Date();
    }

    /**
     * Class constructor
     * @param productionNum
     * @param productName
     * @param serialNum
     * @param prodDate
     * */
    public ProductionRecord(int productionNum, String productName, String serialNum, Date prodDate) {
        this.productionNum = productionNum;
        this.productName = productName;
        this.serialNum = serialNum;
        this.prodDate = prodDate;
    }

    /**
     * Class construtor
     * @param product object is being passed as an argument
     * @param count integer value being passed as argument
     * */
    public ProductionRecord(Product product, int count) {
        String IDNumber = String.format("%05d", count);
        this.serialNum = product.getManufacturer().substring(0, 3) + product.getItemType() + IDNumber;
        this.productName = product.getName();
        this.prodDate = new Date();
    }

    // Getters
    public String getProductName(){return productName;}

    public int getProductionNum() {
        return productionNum;
    }

    public int getProductID() {
        return productID;
    }

    public String getSerialNum() {
        return serialNum;
    }

    public Date getProdDate() {
        return prodDate;
    }

    // Setters
    public void setProductName(String productName){this.productName = productName;}

    public void setProductionNum(int productionNum) {
        this.productionNum = productionNum;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public void setSerialNum(String serialNumber) {
        this.serialNum = serialNum;
    }

    public void setProdDate(Date prodDate) {
        this.prodDate = prodDate;
    }

    @Override
    public String toString() {
        String str = String.format(
                "Prod. Num: %s Product ID: %s Serial Num: %s Date: %s",
                productionNum, productID, serialNum, prodDate);
        return str;
    }
}
