package notSample;

/**
 * @author Fernando Ramirez
 * Date 10/10/2019
 *
 * This enum will create the type "ItemType" for me to use in the program
 */
public enum ItemType {
    AUDIO("AU"),

    VISUAL("VI"),

    AUDIO_MOBILE("AM"),

    VISUAL_MOBILE("VM");

    public String productType;

    ItemType(String productType) {
        this.productType = productType;
    }

    public String getProductType() {

        return productType;
    }

    public String setProductType() {
        return productType;
    }
}
