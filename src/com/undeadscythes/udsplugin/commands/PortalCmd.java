package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.Color;
import org.bukkit.*;
import org.bukkit.util.*;

/**
 * Command to create and manage portals.
 * @author Dave
 */
public class PortalCmd extends CommandWrapper {
    @Override
    public void playerExecute() {
        if(args.length == 1) {
            if(subCmd.equals("list")) {
                list();
            } else {
                subCmdHelp();
            }
        } else if(args.length == 2) {
            if(subCmd.equals("set")) {
                set(null);
            } else if(subCmd.equals("remove")) {
                remove();
            } else {
                subCmdHelp();
            }
        } else if(args.length == 3) {
            if(subCmd.equals("dest")) {
                dest();
            } else if(subCmd.equals("p2p")) {
                p2p();
            } else if(subCmd.equals("remove")) {
                remove();
            } else if(subCmd.equals("exit")) {
                exit();
            } else if(subCmd.equals("set")) {
                final Warp warp = getWarp(args[2]);
                if(warp != null) {
                    set(warp);
                }
            } else {
                subCmdHelp();
            }
        }
    }
    
    private void list() {
        String list = "";
        for(Portal test : UDSPlugin.getPortals().values()) {
            list = list.concat(test.getName() + (test.getWarpName() == null ? ", " : ("(" + test.getWarpName() + "), ")));
        }
        if(list.isEmpty()) {
            player.sendMessage(Color.MESSAGE + "There are no portals.");
        } else {
            player.sendMessage(Color.MESSAGE + "Portals(Warp):");
            player.sendMessage(Color.TEXT + list.substring(0, list.length() - 2));
        }
    }
    
    private void exit() {
        final Portal portal = getPortal(args[1]);
        Direction dir;
        if(portal != null && (dir = getCardinalDirection(args[2])) != null) {
            portal.setExit(dir);
            player.sendMessage(Color.MESSAGE + "Portal now points " + dir.toString() + ".");
        }
    }
    
    private void remove() {
        final Portal portal = getPortal(args[1]);
        if(portal != null) {
            UDSPlugin.getPortals().remove(portal.getName());
            replace(portal, true);
            player.sendMessage(Color.MESSAGE + "Portal removed.");
        }
    }
        
    private void set(final Warp warp) {
        final Session session = getSession();
        if(session != null && inLine(session) && notPortal(args[1]) && noCensor(args[1])) {
            final Portal portal = new Portal(args[1], warp, session.getWorld(), session.getV1(), session.getV2());
            UDSPlugin.getPortals().put(portal.getName(), portal);
            replace(portal, false);
            player.sendMessage(Color.MESSAGE + "Portal " + portal.getName() + " set.");
        }
    }
    
    private void replace(final Portal portal, final boolean remove) {
        final World world = portal.getWorld();
        final Vector min = portal.getV1();
        final Vector max = portal.getV2();
        for(int x = min.getBlockX(); x <= max.getBlockX(); x++) {
            for(int y = min.getBlockY(); y <= max.getBlockY(); y++) {
                for(int z = min.getBlockZ(); z <= max.getBlockZ(); z++) {
                    if(remove) {
                        if(world.getBlockAt(x, y, z).getType() == Material.PORTAL) {
                            world.getBlockAt(x, y, z).setType(Material.AIR);
                        }
                    } else {
                        if(world.getBlockAt(x, y, z).getType() == Material.AIR) {
                            world.getBlockAt(x, y, z).setTypeIdAndData(Material.PORTAL.getId(), (byte)0, false);
                        }
                    }
                }
            }
        }
    }
            
    private void dest() {
        final Portal portal = getPortal(args[1]);
        final Warp target = getWarp(args[2]);
        if(portal != null && target != null) {
            portal.setWarp(target);
            player.sendMessage(Color.MESSAGE + portal.getName() + " now warps to " + target.getName() + ".");
        }
    }
    
    private void p2p() {
        final Portal portal = getPortal(args[1]);
        final Portal target = getPortal(args[2]);
        if(portal != null && target != null) {
            portal.setPortal(target);
            player.sendMessage(Color.MESSAGE + portal.getName() + " now portals to " + target.getName() + ".");
        }
    }
}
