package com.undeadscythes.udsplugin.members;

import com.undeadscythes.udsmeta.*;
import com.undeadscythes.udsmeta.exceptions.*;
import com.undeadscythes.udsplugin.clans.*;
import com.undeadscythes.udsplugin.regions.*;
import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.exceptions.*;
import com.undeadscythes.udsplugin.utilities.*;
import java.io.*;
import java.util.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.util.Vector;

/**
 * @author UndeadScythes
 */
public class OfflineMember {
    protected OfflinePlayer player;
    private MemberMetaUtils meta; //TODO: add methods from online member that really dont matter, e.g. sendmessage, just ignore player not online

    public OfflineMember(final OfflinePlayer player) {
        this.player = player;
        try {
            meta = new MemberMetaUtils(player);
            MetaCore.attach(player);
        } catch(NotMetadatableTypeException ex) {} catch(IOException ex) {}
    }

    public OfflineMember(final String record, final OfflinePlayer player) {
        this.player = player;
        try {
            meta = new MemberMetaUtils(player);
        } catch(NotMetadatableTypeException ex) {}
        String[] recordSplit = record.split("\t");
        meta.set(MemberKey.BOUNTY, Integer.parseInt(recordSplit[1]));
        meta.set(MemberKey.MONEY, Integer.parseInt(recordSplit[2]));
        try {
            meta.set(MemberKey.RANK, MemberRank.getByName(recordSplit[3]).name());
        } catch(NoEnumFoundException ex) {
            throw new UnexpectedException("bad rank on player load:" + recordSplit[3] + "," + player.getName());
        }
        meta.set(MemberKey.VIP_TIME, Long.parseLong(recordSplit[4]));
        meta.set(MemberKey.VIP_SPAWNS, Integer.parseInt(recordSplit[5]));
        meta.set(MemberKey.JAIL_TIME, Long.parseLong(recordSplit[6]));
        meta.set(MemberKey.JAIL_SENTENCE, Long.parseLong(recordSplit[7]));
        meta.set(MemberKey.BAIL, Integer.parseInt(recordSplit[8]));
        meta.set(MemberKey.NICK, recordSplit[9]);
        meta.set(MemberKey.TIME_LOGGED, Long.parseLong(recordSplit[10]));
        if(recordSplit.length == 13) {
            meta.set(MemberKey.PVP_TIME, Long.parseLong(recordSplit[11]));
            meta.set(MemberKey.KILLS, Integer.parseInt(recordSplit[12]));
        }
    }

    protected OfflinePlayer getOfflinePlayer() {
        return player;
    }

    protected MemberMetaUtils getMeta() {
        return meta;
    }

    public void setVipForLife(boolean setting) {
        meta.set(MemberKey.VIP_FOR_LIFE, setting);
    }

    public boolean isVipForLife() {
        return meta.getBool(MemberKey.VIP_FOR_LIFE);
    }

    public void addTime(final long time) {
        meta.set(MemberKey.TIME_LOGGED, getTimeLogged() + time);
    }

    public Vector getLastVector() throws NoMetadataSetException {
        return meta.getVector(MemberKey.LAST_VECTOR);
    }

    public void setLastVector(final Vector vector) {
        meta.set(MemberKey.LAST_VECTOR, vector);
    }

    public void toggleLockdownPass() {
        boolean pass = meta.getBool(MemberKey.LOCKDOWN_PASS);
        meta.set(MemberKey.LOCKDOWN_PASS, !pass);
    }

    public UUID getSelectedPet() throws NoMetadataSetException {
        return meta.getUUID(MemberKey.PET);
    }

    public void selectPet(final UUID id) {
        meta.set(MemberKey.PET, id);
    }

    public boolean isShopping() {
        return meta.getBool(MemberKey.SHOPPING);
    }

    public void setShopping(final boolean isShopping) {
        meta.set(MemberKey.SHOPPING, isShopping);
    }

    public void setWhisperer(final OfflineMember player) {
        meta.set(MemberKey.WHISPERER, player);
    }

    public boolean toggleHidden() {
        boolean hidden = !meta.getBool(MemberKey.HIDDEN);
        meta.set(MemberKey.HIDDEN, hidden);
        return hidden;
    }

    public boolean isHidden() {
        return meta.getBool(MemberKey.HIDDEN);
    }

    public int getWager() {
        try {
            return meta.getInt(MemberKey.WAGER);
        } catch(NoMetadataSetException ex) {
            return 0;
        }
    }

    public boolean isBuying() {
        return meta.getBool(MemberKey.BUYING);
    }

    public boolean hasPvp() {
        return meta.getBool(MemberKey.PVP);
    }

    public void setBuying(final boolean isBuying) {
        meta.set(MemberKey.BUYING, isBuying);
    }

    public Location getShop() throws NoMetadataSetException {
        return meta.getLocation(MemberKey.SHOP);
    }

    public void setShop(final Location shop) {
        meta.set(MemberKey.SHOP, shop);
    }

    public OfflineMember getWhisperer() throws NoMetadataSetException {
        return meta.getSaveablePlayer(MemberKey.WHISPERER);
    }

    public int getPowertoolID() throws NoMetadataSetException {
        return meta.getInt(MemberKey.POWERTOOL_ID);
    }

    public String getPowertool() {
        try {
            return meta.getString(MemberKey.POWERTOOL_COMMAND);
        } catch(NoMetadataSetException ex) {
            return "";
        }
    }

    public void claimPrize() {
        meta.set(MemberKey.LAST_PRIZE_CLAIMED, System.currentTimeMillis());
    }

    public boolean hasClaimedPrize() {
        try {
            return (meta.getLong(MemberKey.LAST_PRIZE_CLAIMED) + TimeUtils.DAY > System.currentTimeMillis());
        } catch(NoMetadataSetException ex) {
            return false;
        }
    }

    public EditSession forceSession() {
        EditSession session = UDSPlugin.getSession(getName());
        if(session == null) {
            session = new EditSession();
            UDSPlugin.addSession(getName(), session);
        }
        return session;
    }

    public void setPowertoolID(final int ID) {
        meta.set(MemberKey.POWERTOOL_ID, ID);
    }

    public void setPowertool(final String cmd) {
        meta.set(MemberKey.POWERTOOL_COMMAND, cmd);
    }

    public ItemStack[] getInventoryCopy() {
        try {
            return meta.getArray(MemberKey.INVENTORY, new ItemStack[36]);
        } catch(NoMetadataSetException ex) {
            return new ItemStack[36];
        }
    }

    public boolean isInShop(final Location location) {
        for(Region region : RegionUtils.getRegions(RegionType.SHOP)) {
            if(location.toVector().isInAABB(region.getV1(), region.getV2())) {
                return true;
            }
        }
        return false;
    }

    public void setClan(final Clan clan) {
        meta.set(MemberKey.CLAN, clan);
    }

    public void endChallenge() {
        meta.remove(MemberKey.WAGER);
        meta.remove(MemberKey.CHALLENGER);
    }

    public int getBail() {
        try {
            return meta.getInt(MemberKey.BAIL);
        } catch(NoMetadataSetException ex) {
            return 0;
        }
    }

    public boolean newChat() {
        List<Long> lastChats;
        try {
            lastChats = meta.getList(MemberKey.LAST_CHATS, new LinkedList<Long>());
        } catch(NoMetadataSetException ex) {
            lastChats = new ArrayList<Long>(5);
        }
        LinkedList<Long> lastChatTimes = new LinkedList<Long>(lastChats);
        if(lastChatTimes.size() > 5) {
            lastChatTimes.removeFirst();
        }
        lastChatTimes.offerLast(System.currentTimeMillis());
        if(lastChatTimes.size() == 5 && lastChatTimes.getLast() - lastChatTimes.getFirst() < 3000) {
            return false;
        }
        return true;
    }

    public String getTimeLogged() {
        try {
            return TimeUtils.timeToString(meta.getLong(MemberKey.TIME_LOGGED));
        } catch(NoMetadataSetException ex) {
            return "no time";
        }
    }

    public String getLastSeen() {
        return TimeUtils.timeToString(System.currentTimeMillis() - player.getLastPlayed());
    }

    public String getVIPTimeString() {
        try {
            return TimeUtils.timeToString(Config.VIP_TIME - System.currentTimeMillis() + meta.getLong(MemberKey.VIP_TIME));
        } catch(NoMetadataSetException ex) {
            return "no time";
        }
    }

    public int useVIPSpawns(final int amount) throws NoMetadataSetException {
        int vipSpawns = meta.getInt(MemberKey.VIP_SPAWNS) - amount;
        meta.set(MemberKey.VIP_SPAWNS, vipSpawns);
        return vipSpawns;
    }

    public void setBackPoint(final Location location) {
        meta.set(MemberKey.BACK_POINT, location);
    }

    public int getMoney() {
        try {
            return meta.getInt(MemberKey.MONEY);
        } catch(NoMetadataSetException ex) {
            return 0;
        }
    }

    public ChatRoom getChatRoom() throws NoMetadataSetException {
        return meta.getChatRoom(MemberKey.CHATROOM);
    }

    public void jail(final long sentence, final int bail) {
        meta.set(MemberKey.JAIL_TIME, System.currentTimeMillis());
        meta.set(MemberKey.JAIL_SENTENCE, sentence * TimeUtils.MINUTE);
        meta.set(MemberKey.BAIL, bail);
    }

    public MemberRank demote() {
        final MemberRank newRank = MemberRank.getBelow(getRank());
        if(newRank != null) {
            meta.set(MemberKey.RANK, newRank.name());
        }
        return newRank;
    }

    public MemberRank promote() {
        final MemberRank newRank = MemberRank.getAbove(getRank());
        if(newRank == MemberRank.OWNER) {
            return null;
        } else if(newRank != null) {
            meta.set(MemberKey.RANK, newRank.name());
        }
        return newRank;
    }

    public ChatChannel getChannel() {
        try {
            return meta.getChatChannel(MemberKey.CHAT_CHANNEL);
        } catch(NoMetadataSetException ex) {
            return ChatChannel.PUBLIC;
        }
    }

    public Location getCheckPoint() throws NoMetadataSetException {
        return meta.getLocation(MemberKey.CHECK_POINT);
    }

    public void setCheckPoint(final Location location) {
        meta.set(MemberKey.CHECK_POINT, location);
    }

    public boolean isDuelling() {
        return meta.exists(MemberKey.CHALLENGER);
    }

    public void setChallenger(final OfflineMember challenger) {
        meta.set(MemberKey.CHALLENGER, challenger);
    }

    public OfflineMember getChallenger() throws NoMetadataSetException {
        return meta.getSaveablePlayer(MemberKey.CHALLENGER);
    }

    public boolean canBuildHere(final Location location) {
        boolean contained = false;
        for(Region region : RegionUtils.getRegions()) {
            if(location.toVector().isInAABB(region.getV1(), region.getV2())) {
                Bukkit.getLogger().info(region.hasMember(this) + "Cant build here");
                if(((region.getRank() != null && getRank().compareTo(region.getRank()) >= 0)) || region.isOwnedBy(this) || region.hasMember(this)) {
                    return true;
                }
                contained = true;
            }
        }
        return contained ? false : UDSPlugin.checkWorldFlag(location.getWorld(), WorldFlag.BUILD);
    }

    public boolean ignorePlayer(final OfflineMember player) {
        try {
            return meta.getSet(MemberKey.IGNORES, new HashSet<OfflineMember>(0)).add(player);
        } catch(NoMetadataSetException ex) {
            HashSet<OfflineMember> ignores = new HashSet<OfflineMember>(1);
            ignores.add(player);
            meta.set(MemberKey.IGNORES, ignores);
            return false;
        }
    }

    public boolean unignorePlayer(final OfflineMember player) {
        try {
            return meta.getSet(MemberKey.IGNORES, new HashSet<OfflineMember>(0)).remove(player);
        } catch(NoMetadataSetException ex) {
            return false;
        }
    }

    public boolean isIgnoringPlayer(final OfflineMember player) {
        try {
            return meta.getSet(MemberKey.IGNORES, new HashSet<OfflineMember>(0)).contains(player);
        } catch(NoMetadataSetException ex) {
            return false;
        }
    }

    public long getLastDamageCaused() throws NoMetadataSetException {
        return meta.getLong(MemberKey.LAST_ATTACKED);
    }

    public int getBounty() {
        try {
            return meta.getInt(MemberKey.BOUNTY);
        } catch(NoMetadataSetException ex) {
            return 0;
        }
    }

    public void setBounty(final int bounty) {
        meta.set(MemberKey.BOUNTY, bounty);
    }

    public void addBounty(final int bounty) {
        setBounty(getBounty() + bounty);
    }

    public Location getBack() throws NoMetadataSetException {
        return meta.getLocation(MemberKey.BACK_POINT);
    }

    public boolean hasRank(final MemberRank rank) {
        return getRank().compareTo(rank) >= 0;
    }

    public boolean isRank(final MemberRank rank) {
        return getRank().equals(rank);
    }

    public void setRank(final MemberRank rank) {
        meta.set(MemberKey.RANK, rank.name());
    }

    public boolean isInClan() {
        return meta.exists(MemberKey.CLAN);
    }

    public Clan getClan() throws NoMetadataSetException {
        return meta.getClan(MemberKey.CLAN);
    }

    public String getNick() {
        try {
            return meta.getString(MemberKey.NICK);
        } catch(NoMetadataSetException ex) {
            return player.getName();
        }
    }

    public boolean toggleChannel(final ChatChannel channel) {
        if(!getChannel().equals(channel)) {
            meta.set(MemberKey.CHAT_CHANNEL, channel);
            return true;
        }
        meta.set(MemberKey.CHAT_CHANNEL, ChatChannel.PUBLIC);
        return false;
    }

    public boolean hasLockdownPass() {
        return meta.getBool(MemberKey.LOCKDOWN_PASS);
    }

    public void setLoadItems(final boolean loadItems) {
        meta.set(MemberKey.LOADITEMS, loadItems);
    }

    public boolean hasLoadItems() {
        return meta.getBool(MemberKey.LOADITEMS);
    }

    public long getVIPTime() throws NoMetadataSetException {
        return meta.getLong(MemberKey.VIP_TIME);
    }

    public void setVIPTime(final long time) {
        meta.set(MemberKey.VIP_TIME, time);
    }

    public int getVIPSpawns() {
        try {
            return meta.getInt(MemberKey.VIP_SPAWNS);
        } catch(NoMetadataSetException ex) {
            return 0;
        }
    }

    public void setVIPSpawns(final int spawns) {
        meta.set(MemberKey.VIP_SPAWNS, spawns);
    }

    public long getJailTime() throws NoMetadataSetException {
        return meta.getLong(MemberKey.JAIL_TIME);
    }

    public void setJailTime(final long time) {
        meta.set(MemberKey.JAIL_TIME, time);
    }

    public long getJailSentence() {
        try {
            return meta.getLong(MemberKey.JAIL_SENTENCE);
        } catch(NoMetadataSetException ex) {
            return 0;
        }
    }

    public void setJailSentence(final long sentence) {
        meta.set(MemberKey.JAIL_SENTENCE, sentence);
    }

    public boolean isJailed() {
        return meta.exists(MemberKey.JAIL_TIME);
    }

    public boolean hasGodMode() {
        return meta.getBool(MemberKey.GODMODE);
    }

    public boolean toggleGodMode() {
        boolean hasGodMode = !meta.getBool(MemberKey.GODMODE);
        meta.set(MemberKey.GODMODE, hasGodMode);
        return hasGodMode;
    }

    public void setGodMode(final boolean mode) {
        meta.set(MemberKey.GODMODE, mode);
    }

    public boolean canAfford(final int price) {
        return getMoney() >= price || hasPerm(Perm.MIDAS);
    }

    public void setWager(final int wager) {
        meta.set(MemberKey.WAGER, wager);
    }

    public void debit(final int amount) {
        meta.set(MemberKey.MONEY, getMoney() - amount);
    }

    public void credit(final int amount) {
        meta.set(MemberKey.MONEY, getMoney() + amount);
    }

    public void setMoney(final int amount) {
        meta.set(MemberKey.MONEY, amount);
    }

    public boolean isAfk() {
        return meta.getBool(MemberKey.AFK);
    }

    public boolean toggleAfk() {
        boolean isAfk = !meta.getBool(MemberKey.AFK);
        meta.set(MemberKey.AFK, isAfk);
        return isAfk;
    }

    public boolean hasPerm(final Perm perm) {
        if(perm.isHereditary()) {
            return perm.getRank().compareTo(getRank()) <= 0;
        } else {
            return perm.getRank().equals(getRank());
        }
    }

    public void setDisplayName(final String name) {
        meta.set(MemberKey.NICK, name);
    }

    public String getName() {
        return player.getName();
    }

    public boolean isOnline() {
        return player.isOnline();
    }

    public long getLastPlayed() {
        return player.getLastPlayed();
    }

    public void setBanned(final boolean banned) {
        player.setBanned(banned);
    }

    public boolean isBanned() {
        return player.isBanned();
    }

    public boolean isOp() {
        return player.isOp();
    }

    public void setPet(final Tameable pet) {
        pet.setOwner(player);
    }

    public Location getBedSpawnLocation() {
        return player.getBedSpawnLocation();
    }

    public ChatColor getRankColor() {
        return getRank().getColor();
    }

    public boolean outRanks(final OfflineMember player) {
        return !player.hasRank(getRank());
    }

    public String getRankName() {
        return getRank().name();
    }

    public boolean sameRank(final OfflineMember player) {
        return player.isRank(getRank());
    }

    public MemberRank getRank() {
        try {
            return meta.getPlayerRank(MemberKey.RANK);
        } catch(NoMetadataSetException ex) {
            return MemberRank.NEWBIE;
        }
    }

    public int getKills() {
        try {
            return meta.getInt(MemberKey.KILLS);
        } catch(NoMetadataSetException ex) {
            return 0;
        }
    }

    public long getPvpTime() throws NoMetadataSetException {
        return meta.getLong(MemberKey.PVP_TIME);
    }

    public void newLogin(final long time) {
        List<Long> lastLogins = new LinkedList<Long>();
        try {
            lastLogins = meta.getList(MemberKey.LAST_LOGINS, new LinkedList<Long>());
        } catch(NoMetadataSetException ex) {}
        lastLogins.add(time);
        while(lastLogins.size() > 2) {
            lastLogins.remove(2);
        }
    }

    public boolean isActive() {
        List<Long> lastLogins = new LinkedList<Long>();
        try {
            lastLogins = meta.getList(MemberKey.LAST_LOGINS, new LinkedList<Long>());
        } catch(NoMetadataSetException ex) {}
        return lastLogins.size() == 2 && System.currentTimeMillis() - TimeUtils.WEEK < lastLogins.get(1);
    }

    public void sendNormal(String message) {
        try {
            MemberUtils.getOnlineMember(this).sendNormal(message);
        } catch (PlayerNotOnlineException ex) {}
    }
}
