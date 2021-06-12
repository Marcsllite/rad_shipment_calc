package rad.shipment.calculator.helpers;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;
import org.joda.time.Interval;
import org.joda.time.LocalDateTime;
import org.joda.time.Period;
import rad.shipment.calculator.gui.Main;

/**
 * Author Olimpiu POP https://stackoverflow.com/questions/13227809/displaying-changing-values-in-javafx-label
 */
public class UpTime extends Label {
    private final Timeline timeline;
    private LocalDateTime lastReboot;

    /**
     * Constructs an Uptime object with the DeviceInfo's default text
     * and null lastReboot time
     */
    public UpTime() {
        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);
        lastReboot = null;
        setText(Main.getString("defaultStr"));
    }

    /**
     * Function to update the uptime given a new lastReboot time
     *
     * @param newLastReboot the local date and time that the device was last reboot
     */
    public void update(LocalDateTime newLastReboot) {
        setLastReboot(newLastReboot);
        if(lastReboot == null) {
            setText(Main.getString("defaultStr"));
            timeline.stop();
        } else {
            timeline.getKeyFrames().setAll((new KeyFrame(Duration.seconds(0),
                    event -> setText(getDuration(lastReboot)))),
                    new KeyFrame(Duration.seconds(1)));
            timeline.play();
        }
    }

    /**
     * Getter function to get the uptime without the seconds
     *
     * @return the uptime without the seconds
     */
    public String getHTMLUptime(){
        if (getText().equals(Main.getString("defaultStr"))) return getText();
        else return getText().substring(0, getText().lastIndexOf(' '));
    }

    /**
     * Getter function to get the last reboot time
     *
     * @return the lastReboot that the uptime is based off
     */
    public LocalDateTime getLastReboot() { return lastReboot; }

    /**
     * Setter function to set the last reboot time
     *
     * @param newLastReboot the bew last reboot time
     */
    public void setLastReboot(LocalDateTime newLastReboot) {lastReboot = newLastReboot;}

    /**
     * Helper function to get the time between lastReboot and LocalDateTime.now()
     *
     * @param lastReboot the local date and time that the device was last reboot
     * @return a string representation of the time between lastReboot and LocalDateTime.now()
     */
    private String getDuration(LocalDateTime lastReboot) {
        // getting the interval between lastReboot and LocalDateTime.now()
        // making sure that the oldest date is in the first parameter of Interval so no errors occur
        Interval interval = (LocalDateTime.now().toDate().after(lastReboot.toDate())) ? new Interval(lastReboot.toDate().getTime(), LocalDateTime.now().toDate().getTime()) :
                new Interval(LocalDateTime.now().toDate().getTime(), lastReboot.toDate().getTime());
        Period duration = interval.toPeriod();

        // Getting the different values from the duration to display in a string
        int years = duration.getYears();
        int months = duration.getMonths();
        int weeks = duration.getWeeks();
        int days = duration.getDays();
        int hours = duration.getHours();
        int minutes = duration.getMinutes();
        int seconds = duration.getSeconds();

        // building the duration time omitting anything that is 0 except for seconds
        String ret = seconds + "sec";
        if(minutes != 0) {
            if (minutes == 1) ret = minutes + "min " + ret;
            else ret = minutes + "mins " + ret;
        }
        if(hours != 0) {
            if (hours == 1) ret = hours + "hr " + ret;
            else ret = hours + "hrs " + ret;
        }
        if(days != 0) ret = days + "d " + ret;
        if(weeks != 0) {
            if (weeks == 1) ret = weeks + "wk " + ret;
            else ret = weeks + "wks " + ret;
        }
        if(months != 0) {
            if (months == 1) ret = months + "mo " + ret;
            else ret = months + "mos " + ret;
        }
        if(years != 0) {
            if (years == 1) ret = years + "yr " + ret;
            else ret = years + "yrs " + ret;
        }

        return ret;
    }
}

