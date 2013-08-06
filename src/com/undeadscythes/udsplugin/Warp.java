package com.undeadscythes.udsplugin;

import com.undeadscythes.udsplugin.utilities.*;
import java.util.*;
import org.apache.commons.lang.*;
import org.bukkit.*;

/**
 * A warp point used for player teleportation.
 * 
 * @author UndeadScythes
 */
public class Warp implements Saveable {
    private final String name;
    private final Location location;
    private final PlayerRank rank;
    private final int price;

    public Warp(final String name, final Location location, final PlayerRank rank, final int price) {
        this.name = name;
        this.location = location;
        this.rank = rank;
        this.price = price;
    }

    public Warp(final String record) {
        final String[] recordSplit = record.split("\t");
        name = recordSplit[0];
        location = LocationUtils.parseLocation(recordSplit[1]);
        rank = PlayerRank.getByName(recordSplit[2]);
        price = Integer.parseInt(recordSplit[3]);
    }

    @Override
    public final String getRecord() {
        final ArrayList<String> record = new ArrayList<String>(4);
        record.add(name);
        record.add(LocationUtils.getString(location));
        record.add(rank.toString());
        record.add(Integer.toString(price));
        return StringUtils.join(record, "\t");
    }

    public final String getName() {
        return name;
    }

    public final int getPrice() {
        return price;
    }

    public final PlayerRank getRank() {
        return rank;
    }

    public final Location getLocation() {
        return LocationUtils.findSafePlace(location);
    }
}
