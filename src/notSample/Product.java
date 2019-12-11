package notSample;

/**
 * @author Fernando Ramirez
 * Date 10/10/2019
 *
 * This class creates the object Product which is highly
 * versatile and is a funcamental component of this project
 */

public abstract class Product implements Item {

    // Fields that my Product will use
    private int id;
    private String manufacturer;
    private String name;
    private ItemType itemType;

    public Product(String name, String manufacturer, ItemType itemType) {

        this.name = name;
        this.manufacturer = manufacturer;
        this.itemType = itemType;
    }

    public Product(int prodID, String name, String manufacturer, ItemType itemType) {

        this.id = prodID;
        this.name = name;
        this.manufacturer = manufacturer;
        this.itemType = itemType;
    }

    public ItemType getItemType() {
        return this.itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public ItemType getType() {
        return itemType;
    }

    public void setType(ItemType type) {
        this.itemType = type;
    }

    @Override
    public String toString() {
        String str =
                String.format(
                        "Name: %s\nManufacturer: %s\ntype: %s", this.name, this.manufacturer, this.itemType);
        return str;
    }
}

class Widget extends Product {
    public Widget(String name, String manufacturer, ItemType type) {
        super(name, manufacturer, type);
    }

    public Widget(int prodID, String name, String manufacturer, ItemType type) {
        super(prodID, name, manufacturer, type);
    }
}

