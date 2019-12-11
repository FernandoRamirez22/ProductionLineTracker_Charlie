package notSample;

/**
 * @author Fernando Ramirez
 * Date 10/10/2019
 *
 * This interface will manipulate the variable from the object Item
 */
public interface Item {

    public int getId();

    public void setName(String name);

    public String getName();

    public void setManufacturer(String manufacturer);

    public String getManufacturer();
}
// Initializing methods that I will use in other files
