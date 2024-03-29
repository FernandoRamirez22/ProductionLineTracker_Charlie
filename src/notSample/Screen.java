package notSample;

/**
 * @author Fernando Ramirez
 * Date 10/10/2019
 *
 * Class is meant to create an object screen which has different attibutes, similar to Product
 */
public class Screen implements ScreenSpec {
    private String resolution;
    private int refreshRate;
    private int responseTime;

    public Screen(String resolution, int refreshrate, int responsetime) {
        this.resolution = resolution;
        this.refreshRate = refreshrate;
        this.responseTime = responsetime;
    }

    public String getResolution() {
        return this.resolution;
    }

    public int getRefreshRate() {
        return this.refreshRate;
    }

    public int getResponseTime() {
        return this.responseTime;
    }

    public String toString() {
        return String.format(
                "\nResolution: %s\nRefresh rate: %d\nResponse time: %d",
                this.resolution, this.refreshRate, this.responseTime);
    }
}
