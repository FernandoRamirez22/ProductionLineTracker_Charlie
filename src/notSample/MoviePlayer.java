package notSample;

/**
 * @author Fernando Ramirez
 * Date 9/10/2019
 *
 * This c;ass creates several different objects in which we didn't use in our project
 */
public class MoviePlayer extends Product implements MultimediaControl {

    private Screen screen;
    private MonitorType monitorType;

    public MoviePlayer(String name, String manufacturer, Screen screen, MonitorType monitorType) {

        super(name, manufacturer, ItemType.VISUAL);
        this.screen = screen;
        this.monitorType = monitorType;
    }

    public void play() {
        System.out.println("Playing movie");
    }

    public void stop() {
        System.out.println("Stopping movie");
    }

    public void previous() {
        System.out.println("Previous movie");
    }

    public void next() {
        System.out.println("Next movie");
    }

    @Override
    public String toString() {
        return String.format("%s\nScreen: %s\nMonitorType: %s", super.toString(), screen, monitorType);
    }
}
