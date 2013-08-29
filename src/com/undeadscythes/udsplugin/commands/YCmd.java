package com.undeadscythes.udsplugin.commands;


import com.undeadscythes.udsplugin.requests.*;
import com.undeadscythes.udsplugin.clans.*;
import com.undeadscythes.udsplugin.members.*;
import com.undeadscythes.udsplugin.regions.*;
import com.undeadscythes.udsmeta.exceptions.*;
import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.exceptions.*;
import org.bukkit.entity.*;

/**
 * @author UndeadScythes
 */
public class YCmd extends CommandHandler {
    @Override
    public void playerExecute() throws PlayerNotOnlineException {
        Request request;
        OfflineMember requestSender;
        if((request = getRequest()) != null && (requestSender = request.getSender()) != null && requestSender.isOnline()) {
            int price;
            switch (request.getType()) {
                case TP:
                    MemberUtils.getOnlineMember(requestSender).teleport(player);
                    break;
                case CLAN:
                    clanRequest(ClanUtils.getClan(request.getData()));
                    break;
                case HOME:
                    if(canAfford(price = Integer.parseInt(request.getData())) && hasNoHome()) {
                        final Region home = RegionUtils.getRegion(RegionType.HOME, requestSender.getName() + "home");
                        home.clearMembers();
                        home.changeOwner(player.getOfflineMember());
                        RegionUtils.renameRegion(home, player.getName() + "home");
                        player.debit(price);
                        requestSender.credit(price);
                    }
                    break;
                case PET:
                    if(canAfford(price = Integer.parseInt(request.getData()))) {
                        for(Entity entity : MemberUtils.getOnlineMember(requestSender).getWorld().getEntities()) {
                            try {
                                if(entity.getUniqueId().equals(requestSender.getSelectedPet())) {
                                    player.debit(price);
                                    requestSender.credit(price);
                                    player.setPet((Tameable)entity);
                                    player.teleportHere(entity);
                                    player.sendNormal("Your bought " + requestSender.getNick() + "'s pet.");
                                    MemberUtils.getOnlineMember(requestSender).sendNormal(player.getNick() + " bought your pet.");
                                }
                            } catch(NoMetadataSetException ex) {
                                player.sendError(requestSender.getNick() + " is no longer selling their pet.");
                            }
                        }
                    }
                    break;
                case PVP:
                    if(canAfford(price = Integer.parseInt(request.getData()))) {
                        MemberUtils.getOnlineMember(requestSender).sendNormal(player.getNick() + " accepted your duel, may the best player win.");
                        player.sendNormal("Duel accepted, may the best player win.");
                        player.setChallenger(MemberUtils.getOnlineMember(requestSender));
                        requestSender.setChallenger(requestSender);
                        player.setWager(price);
                        requestSender.setWager(price);
                    }
                    break;
                case SHOP:
                    if(canAfford(price = Integer.parseInt(request.getData())) && hasNoShop()) {
                        final Region shop = RegionUtils.getRegion(RegionType.SHOP, requestSender.getName() + "shop");
                        shop.clearMembers();
                        shop.changeOwner(player.getOfflineMember());
                        player.debit(price);
                        requestSender.credit(price);
                    }
                    break;
                default:
                    MemberUtils.getOnlineMember(requestSender).sendNormal(player.getNick() + " was unable to accept your request.");
            }
        }
        UDSPlugin.removeRequest(player.getName());
    }

    private void clanRequest(final Clan clan) {
        clan.addMember(player.getOfflineMember());
        player.setClan(clan);
        clan.sendMessage(player.getNick() + " has joined the clan.");
        final Region base = RegionUtils.getRegion(RegionType.BASE, clan.getName() + "base");
        if(base != null) {
            base.addMember(player.getOfflineMember());
        }
    }
}
