package notSample;

/**
 * @author Fernando Ramirez
 * Date 10/06/2019
 *
 * This class will generate the getters and setters for the three objects used
 */
public class Box implements Item {

    // getters and setters, implementing methods
    @Override
    public int getId() {
        return 0;
    }

    @Override
    public void setName(String name) {
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setManufacturer(String manufacturer) {
    }

    @Override
    public String getManufacturer() {
        return null;
    }
}
