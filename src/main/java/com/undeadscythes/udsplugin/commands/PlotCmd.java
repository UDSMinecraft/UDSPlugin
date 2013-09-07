package com.undeadscythes.udsplugin.commands;


import com.undeadscythes.udsplugin.regions.*;
import com.undeadscythes.udsplugin.*;
import java.util.*;
import org.apache.commons.lang3.*;
import org.bukkit.*;
import org.bukkit.util.Vector;

/**
 * @author UndeadScythes
 */
public class PlotCmd extends CommandHandler {
    @Override
    public void playerExecute() {
        if(args.length == 0) {
            list();
        } else if(args.length == 1) {
            if(subCmdEquals("claim")) {
                claim();
            } else if(subCmdEquals("like")) {
                like();
            } else if(subCmdEquals("remove")) {
                Region plot;
                if((plot = getPlot()) != null) {
                    remove(plot.getName());
                }
            } else {
                subCmdHelp();
            }
        } else if(args.length == 2) {
            if(subCmdEquals("tp")) {
                tp();
            } else if(subCmdEquals("name")) {
                Region plot;
                if((plot = getPlot()) != null) {
                    name(plot.getName(), args[1]);
                }
            } else if(subCmdEquals("remove")) {
                remove(args[1]);
            } else {
                subCmdHelp();
            }
        } else if(numArgsHelp(3)) {
            if(subCmdEquals("name")) {
                name(args[1], args[2]);
            } else{
                subCmdHelp();
            }
        }
    }

    private void claim() {
        if(getPlot() == null && numPlots()) {
            final int x = player.getLocation().getBlockX();
            final int z = player.getLocation().getBlockZ();
            Vector v1 = new Vector((x / 50) * 50, 0, (z / 50) * 50);
            final Vector v2 = v1.clone().add(new Vector(46, UDSPlugin.BUILD_LIMIT, 46));
            v1.add(new Vector(3, 0, 3));
            int next = nextPlot();
            String name;
            do {
                name = player.getName() + "Plot" + next;
                next++;
            } while(RegionUtils.getRegion(RegionType.PLOT, name) != null);
            final Region plot = new Region(name, v1, v2, player.getLocation(), player.getOfflineMember(), "", RegionType.PLOT);
            RegionUtils.addRegion(plot);
            square(v1.clone().subtract(new Vector(3, 0, 3)), v2.clone().add(new Vector(4, 0, 4)), 3, Material.QUARTZ_BLOCK);
            square(v1.clone().subtract(new Vector(1, 0, 1)), v2.clone().add(new Vector(2, 0, 2)), 3, Material.NETHER_BRICK);
            square(v1.clone(), v2.clone().add(new Vector(1, 0 ,1)), 3, Material.GRASS);
            player.sendNormal("Plot claimed.");
        } else {
            player.sendError("This plot has already been claimed.");
        }
    }

    private void remove(final String plotName) {
        Region plot;
        if(player.hasPerm(Perm.PLOT_REMOVE)) {
            plot = plotExists(plotName);
        } else {
            plot = ownsPlot(plotName);
        }
        if(plot != null) {
            final Vector v1 = plot.getV1();
            final Vector v2 = plot.getV2().clone().add(new Vector(1, 0, 1));
            int y = UDSPlugin.BUILD_LIMIT;
            while(y > 3) {
                square(v1, v2, y--, Material.AIR);
            }
            square(v1, v2, y--, Material.GRAVEL);
            while(y > 0) {
                square(v1, v2, y--, Material.DIRT);
            }
            square(v1, v2, 0, Material.BEDROCK);
            RegionUtils.removeRegion(plot);
            player.sendNormal(plot.getName() + " removed.");
        }
    }

    private void square(final Vector v1, final Vector v2, final int y, final Material m) {
        final World world = player.getWorld();
        for(int x = v1.getBlockX(); x < v2.getBlockX(); x++) {
            for(int z = v1.getBlockZ(); z < v2.getBlockZ(); z++) {
                world.getBlockAt(x, y, z).setType(m);
            }
        }
    }

    private void like() {
        Region plot;
        if((plot = getPlot()) != null) {
            String cur = plot.getData();
            if(cur.isEmpty()) {
                plot.setData("1");
            } else {
                plot.setData("" + (Integer.parseInt(cur) + 1));
            }
            player.sendNormal("You liked this plot.");
        } else {
            player.sendError("You are not in a plot.");
        }
    }

    private Region getPlot() {
        for(Region plot : RegionUtils.getRegions(RegionType.PLOT)) {
            if(plot.contains(player.getLocation())) {
                return plot;
            }
        }
        return null;
    }

    private boolean numPlots() {
        if(nextPlot() > 4 && !player.hasPerm(Perm.PLOT_MANY)) { // Limit hardcoded for speed right now
            player.sendError("You have reached the maximum number of plots you can own.");
            return false;
        }
        return true;
    }

    private int nextPlot() {
        int count = 1;
        for(Region plot : RegionUtils.getRegions(RegionType.PLOT)) {
            if(plot.getOwner().equals(player)) {
                count++;
            }
        }
        return count;
    }

    private void list() {
        List<String> plots = new ArrayList<String>(4);
        for(Region plot : RegionUtils.getRegions(RegionType.PLOT)) {
            if(plot.getOwner().equals(player)) {
                plots.add(plot.getName());
            }
        }
        if(!plots.isEmpty()) {
            player.sendNormal("Your creative plots:");
            player.sendText(StringUtils.join(plots, ", "));
        } else {
            player.sendNormal("You do not have any creative plots.");
        }
    }

    private void tp() {
        Region plot;
        if((plot = plotExists(args[1])) != null) {
            player.teleport(plot.getWarp());
        }
    }

    private void name(final String plotName, final String name) {
        Region plot;
        if((plot = ownsPlot(plotName)) != null && noBadLang(name)) {
            RegionUtils.renameRegion(plot, name);
            player.sendNormal("Plot renamed to " + name + ".");
        }
    }

    private Region ownsPlot(final String name) {
        final Region plot;
        if((plot = plotExists(name)) != null && !plot.getOwner().equals(player)) {
            player.sendError("You do not own that plot.");
            return null;
        }
        return plot;
    }

    private Region plotExists(final String name) {
        final Region plot = RegionUtils.getRegion(RegionType.PLOT, name);
        if(plot == null) {
            player.sendError("No plot exists by that name.");
        }
        return plot;
    }
}
