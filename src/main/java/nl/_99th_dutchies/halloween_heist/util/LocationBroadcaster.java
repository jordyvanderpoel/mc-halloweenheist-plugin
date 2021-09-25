package nl._99th_dutchies.halloween_heist.util;

import nl._99th_dutchies.halloween_heist.HalloweenHeist;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.time.LocalDateTime;
import java.util.Random;

public class LocationBroadcaster implements Runnable {
    private HalloweenHeist plugin;

    public LocationBroadcaster(HalloweenHeist plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        LocalDateTime time = LocalDateTime.now();

        if(time.getMinute() == 0 && time.getHour() >= 13 &&
                (this.plugin.lastLocationBroadcastHour < 0 || time.getHour() > this.plugin.lastLocationBroadcastHour)) {
            this.plugin.lastLocationBroadcastHour = time.getHour();

            Location broadcastLocation = this.calcLocation();
            this.plugin.lastLocationBroadcastLocation = broadcastLocation;

            Bukkit.broadcastMessage("The medal can be found somewhere around [X:" + ((int) broadcastLocation.getX()) + ",Z:" + ((int) broadcastLocation.getZ()) + "]");
        }
    }

    private Location calcLocation() {
        Random rand = new Random();
        Location broadcastLocation = new Location(this.plugin.mainWorld, 0, 0, 0);
        this.plugin.medalLocation.findMedal();
        Location currentLocation = this.plugin.medalLocation.location;

        int randX = (rand.nextInt(10)) * (rand.nextBoolean() ? 1 : -1);
        int randZ = (rand.nextInt(10)) * (rand.nextBoolean() ? 1 : -1);
        broadcastLocation.setX(currentLocation.getX() + randX);
        broadcastLocation.setZ(currentLocation.getZ() + randZ);

        return broadcastLocation;
    }
}
