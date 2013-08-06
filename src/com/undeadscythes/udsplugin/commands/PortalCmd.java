package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.utilities.*;
import org.bukkit.*;
import org.bukkit.util.*;

/**
 * Command to create and manage portals.
 * 
 * @author UndeadScythes
 */
public class PortalCmd extends CommandHandler {
    @Override
    public final void playerExecute() {
        if(argsLength() == 1) {
            if(subCmdEquals("list")) {
                list();
            } else {
                subCmdHelp();
            }
        } else if(argsLength() == 2) {
            if(subCmdEquals("set")) {
                set(null);
            } else if(subCmdEquals("remove")) {
                remove();
            } else {
                subCmdHelp();
            }
        } else if(argsLength() == 3) {
            if(subCmdEquals("dest")) {
                dest();
            } else if(subCmdEquals("p2p")) {
                p2p();
            } else if(subCmdEquals("remove")) {
                remove();
            } else if(subCmdEquals("exit")) {
                exit();
            } else if(subCmdEquals("set")) {
                final Warp warp = matchWarp(arg(2));
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
        for(Portal test : PortalUtils.getPortals()) {
            list = list.concat(test.getName() + (test.getWarpName() == null ? ", " : ("(" + test.getWarpName() + "), ")));
        }
        if(list.isEmpty()) {
            player().sendNormal("There are no portals.");
        } else {
            player().sendNormal("Portals(Warp):");
            player().sendText(list.substring(0, list.length() - 2));
        }
    }
    
    private void exit() {
        final Portal portal = getPortal(arg(1));
        Direction dir;
        if(portal != null && (dir = getCardinalDirection(arg(2))) != null) {
            portal.setExit(dir);
            player().sendNormal("Portal now points " + dir.toString() + ".");
        }
    }
    
    private void remove() {
        final Portal portal = getPortal(arg(1));
        if(portal != null) {
            PortalUtils.removePortal(portal);
            replace(portal, true);
            player().sendNormal("Portal removed.");
        }
    }
        
    private void set(final Warp warp) {
        final EditSession session = player().forceSession();
        if(session != null && selectionIs2D(session) && noPortalExists(arg(1)) && noBadLang(arg(1))) {
            final Portal portal = new Portal(arg(1), warp, session.getWorld(), session.getV1(), session.getV2());
            PortalUtils.addPortal(portal);
            replace(portal, false);
            player().sendNormal("Portal " + portal.getName() + " set.");
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
        final Portal portal = getPortal(arg(1));
        final Warp target = matchWarp(arg(2));
        if(portal != null && target != null) {
            portal.setWarp(target);
            player().sendNormal(portal.getName() + " now warps to " + target.getName() + ".");
        }
    }
    
    private void p2p() {
        final Portal portal = getPortal(arg(1));
        final Portal target = getPortal(arg(2));
        if(portal != null && target != null) {
            portal.setPortal(target);
            player().sendNormal(portal.getName() + " now portals to " + target.getName() + ".");
        }
    }
}
