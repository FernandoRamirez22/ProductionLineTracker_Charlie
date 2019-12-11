package notSample;

/**
 * @author Fernando Ramirez
 * Date 10/05/2019
 * <p>
 * This class will specify the object AudioPlayer and define some of its specifications
 */
public class AudioPlayer extends Product implements MultimediaControl {
    String audioSpecification;
    String mediaType;

    public AudioPlayer(
            String name, String manufacturer, String mediaType, String audioSpecification) {

        super(name, manufacturer, ItemType.AUDIO);

        this.audioSpecification = audioSpecification;
        this.mediaType = mediaType;
    } // constructor

    public String getAudioSpecification() {
        return this.audioSpecification;
    } // getAudioSpecification

    public String getMediaType() {
        return this.mediaType;
    } // getMediaType

    public void setAudioSpecification(String audioSpecification) {
        this.audioSpecification = audioSpecification;
    } // setAudioSpecification

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    } // setMediaType

    public void play() {
        System.out.println("Playing");
    }

    public void stop() {
        System.out.println("Stopping");
    }

    public void previous() {
        System.out.println("Previous");
    }

    public void next() {
        System.out.println("Next");
    }

    public String toString() {
        return String.format(
                "%s\nSupported Audio Formats: %s\nSupported Playlist Formats: %s",
                super.toString(), this.mediaType, this.audioSpecification);
    }
} // class
