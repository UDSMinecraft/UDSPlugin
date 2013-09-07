package com.undeadscythes.udsplugin;

import com.undeadscythes.udsplugin.members.*;
import com.undeadscythes.udsplugin.exceptions.*;
import com.undeadscythes.udsplugin.utilities.*;
import java.util.*;
import org.apache.commons.lang3.*;
import org.bukkit.*;

/**
 * @author UndeadScythes
 */
public class Warp implements Saveable {
    private final String name;
    private final Location location;
    private final MemberRank rank;
    private final int price;

    public Warp(final String name, final Location location, final MemberRank rank, final int price) {
        this.name = name;
        this.location = location;
        this.rank = rank;
        this.price = price;
    }

    public Warp(final String record) {
        final String[] recordSplit = record.split("\t");
        name = recordSplit[0];
        location = LocationUtils.parseLocation(recordSplit[1]);
        try {
            rank = MemberRank.getByName(recordSplit[2]);
        } catch(NoEnumFoundException ex) {
            throw new UnexpectedException("bad rank on warp load:" + recordSplit[2] + "," + name);
        }
        price = Integer.parseInt(recordSplit[3]);
    }

    @Override
    public String getRecord() {
        final ArrayList<String> record = new ArrayList<String>(4);
        record.add(name);
        record.add(LocationUtils.getString(location));
        record.add(rank.toString());
        record.add(Integer.toString(price));
        return StringUtils.join(record, "\t");
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public MemberRank getRank() {
        return rank;
    }

    public Location getLocation() {
        return LocationUtils.findSafePlace(location);
    }
}
