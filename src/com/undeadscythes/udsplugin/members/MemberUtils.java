package com.undeadscythes.udsplugin.members;

import com.undeadscythes.udsmeta.*;
import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.exceptions.*;
import com.undeadscythes.udsplugin.utilities.*;
import java.io.*;
import java.sql.*;
import java.util.*;
import org.bukkit.*;
import org.bukkit.configuration.file.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;

/**
 * @author UndeadScythes
 */
public class MemberUtils {
    private static final MatchableHashMap<OfflineMember> MEMBERS = new MatchableHashMap<OfflineMember>();
    private static final MatchableHashMap<OfflineMember> HIDDEN_MEMBERS = new MatchableHashMap<OfflineMember>();
    private static final MatchableHashMap<Member> ONLINE_MEMBERS = new MatchableHashMap<Member>();
    private static final MatchableHashMap<OfflineMember> VIPS = new MatchableHashMap<OfflineMember>();
    private static final HashMap<World, YamlConfig> INVENTORIES = new HashMap<World, YamlConfig>(3);

    public static Collection<OfflineMember> getMembers() {
        return MEMBERS.values();
    }

    public static OfflineMember getMember(final String name) throws NoPlayerFoundException {
        try {
            return MEMBERS.get(name);
        } catch(NoKeyFoundException ex) {
            throw new NoPlayerFoundException(name);
        }
    }

    public static List<OfflineMember> getSortedMembers(final Comparator<OfflineMember> comparator) {
        return MEMBERS.getSortedValues(comparator);
    }

    public static OfflineMember matchMember(final String partial) throws NoPlayerFoundException {
        try {
            return MEMBERS.matchKey(partial);
        } catch(NoKeyFoundException ex) {
            throw new NoPlayerFoundException(partial);
        }
    }

    public static void addMember(final OfflineMember member) {
        MEMBERS.put(member.getName(), member);
    }

    public static boolean memberExists(final String name) {
        return MEMBERS.containsKey(name);
    }

    public static int countMembers() {
        return MEMBERS.size();
    }

    public static Collection<OfflineMember> getVips() {
        return VIPS.values();
    }

    public static Collection<OfflineMember> getHiddenMembers() {
        return HIDDEN_MEMBERS.values();
    }

    public static void addHiddenMember(final OfflineMember member) {
        HIDDEN_MEMBERS.put(member.getName(), member);
    }

    public static void removeHiddenMember(final String name) {
        HIDDEN_MEMBERS.remove(name);
    }

    public static Collection<Member> getOnlineMembers() {
        return ONLINE_MEMBERS.values();
    }

    public static Member getOnlineMember(final String name) throws PlayerNotOnlineException {
        try {
            return ONLINE_MEMBERS.get(name);
        } catch(NoKeyFoundException ex) {
            throw new PlayerNotOnlineException(name);
        }
    }

    public static Member getOnlineMember(final OfflineMember member) throws PlayerNotOnlineException {
        return getOnlineMember(member.getName());
    }

    public static Member getOnlineMember(final Player player) {
        try {
            return getOnlineMember(player.getName());
        } catch(PlayerNotOnlineException ex) {
            throw new UnexpectedException("no online member found for online player");
        }
    }

    public static Member matchOnlineMember(final String partial) throws NoPlayerFoundException {
        try {
            return ONLINE_MEMBERS.matchKey(partial);
        } catch(NoKeyFoundException ex) {
            throw new NoPlayerFoundException(partial);
        }
    }

    public static void addOnlineMember(final Member member) {
        String name = member.getName();
        ONLINE_MEMBERS.put(name, member);
        try {
            SQLUtils.load();
            Statement stmt = SQLUtils.getStatement();
            ResultSet rs = stmt.executeQuery("SELECT name FROM members WHERE name=\"" + name + "\";");
            if(!rs.next()) {
                stmt.execute("INSERT INTO members (name) VALUES (\"" + name + "\");");
            }
            SQLUtils.close();
        } catch (SQLException ex) {
            SQLUtils.close();
            Bukkit.getLogger().info(ex.getMessage());
        }
    }

    public static Member removeOnlineMember(final String name) {
        return ONLINE_MEMBERS.remove(name);
    }

    public static void saveMembers() throws IOException {
        MetaCore.save();
        for(final YamlConfig config : INVENTORIES.values()) {
            config.save();
        }
    }

    public static void loadMembers(final File parent) throws IOException {
        for(final World world : Bukkit.getWorlds()) {
            INVENTORIES.put(world, new YamlConfig(UDSPlugin.DATA_PATH + "/inventories/" + world.getName() + ".yml"));
            INVENTORIES.get(world).load();
        }
        if(MetaCore.isEmpty()) {
            try {
                final BufferedReader file = new BufferedReader(new FileReader(parent + File.separator + "players.csv"));
                String nextLine;
                while((nextLine = file.readLine()) != null) {
                    String playerName = nextLine.split("\t")[0];
                    MEMBERS.put(playerName, new OfflineMember(nextLine, UDSPlugin.SERVER.getOfflinePlayer(playerName)));
                }
                file.close();
            } catch(FileNotFoundException ex) {}
        }
        for(OfflinePlayer player : UDSPlugin.SERVER.getOfflinePlayers()) {
            MEMBERS.put(player.getName(), new OfflineMember(player));
        }
        try {
            SQLUtils.load();
            Statement stmt = SQLUtils.getStatement();
            for(World world : Bukkit.getWorlds()) {
                String name = world.getName();
                ResultSet rs = stmt.executeQuery("SELECT members FROM online WHERE loc=\"" + name + "\";");
                if(!rs.next()) {
                    stmt.execute("INSERT INTO online (loc) VALUES (\"" + name + "\");");
                }
            }
            ResultSet rs = stmt.executeQuery("SELECT members FROM online WHERE loc=\"total\";");
            if(!rs.next()) {
                stmt.execute("INSERT INTO online (loc) VALUES (\"total\");");
            }
            rs = stmt.executeQuery("SELECT members FROM online WHERE loc=\"admin\";");
            if(!rs.next()) {
                stmt.execute("INSERT INTO online (loc) VALUES (\"admin\");");
            }
            SQLUtils.close();
        } catch (SQLException ex) {
            SQLUtils.close();
            Bukkit.getLogger().info(ex.getMessage());
        }
    }

    public static void saveInventory(final Member member, final World world) {
        final FileConfiguration config = INVENTORIES.get(world).getConfig();
        config.set(member.getName() + ".inventory", member.getInventory().getContents());
        config.set(member.getName() + ".armor", member.getInventory().getArmorContents());
    }

    @SuppressWarnings("unchecked")
    public static void loadInventory(final Member member, final World world) {
        final FileConfiguration config = INVENTORIES.get(world).getConfig();
        if(config.contains(member.getName())) {
            Object obj = config.get(member.getName() + ".inventory");
            if(obj instanceof ArrayList) {
                member.getInventory().setContents(((List<ItemStack>)obj).toArray(new ItemStack[36]));
                member.getInventory().setArmorContents(((List<ItemStack>)config.getList(member.getName() + ".armor")).toArray(new ItemStack[4]));
            } else {
                member.getInventory().setContents((ItemStack[])obj);
                member.getInventory().setArmorContents((ItemStack[])config.get(member.getName() + ".armor"));
            }
        } else {
            member.getInventory().clear(-1, -1);
        }
    }

    public static void saveInventory(final Member member) {
        saveInventory(member, member.getWorld());
    }

    public static void loadInventory(final Member member) {
        loadInventory(member, member.getWorld());
    }

    public static int countActiveMembers() {
        int count = 0;
        for(final OfflineMember player : MEMBERS.values()) {
            count += player.isActive() ? 1 : 0;
        }
        return count;
    }

    public static void updateMembers() {
        try {
            SQLUtils.load();
            Statement stmt = SQLUtils.getStatement();
            int total = 0;
            for(World world : Bukkit.getWorlds()) {
                int count = world.getEntitiesByClass(Player.class).size();
                total += count;
                stmt.execute("UPDATE online SET members=\"" + count + "\" WHERE loc=\"" + world.getName() + "\"");

            }
            stmt.execute("UPDATE online SET members=\"" + total + "\" WHERE loc=\"total\"");
            total = 0;
            for(Member member : getOnlineMembers()) {
                if(member.hasRank(MemberRank.ADMIN)) {
                    total++;
                }
            }
            stmt.execute("UPDATE online SET members=\"" + total + "\" WHERE loc=\"admin\"");
            SQLUtils.close();
        } catch (SQLException ex) {
            SQLUtils.close();
            Bukkit.getLogger().info(ex.getMessage());
        }
    }

    public static void saveMember(OfflineMember member) {
        try {
            SQLUtils.load();
            Statement stmt = SQLUtils.getStatement();
            stmt.executeUpdate("UPDATE members SET rank=\"" + member.getRank().toString() + "\", rankid=\"" + member.getRank().getID() + "\" WHERE LOWER(name)=\"" + member.getName().toLowerCase() + "\";");
            stmt.close();
            SQLUtils.close();
        } catch (SQLException ex) {
            SQLUtils.close();
            Bukkit.getLogger().info(ex.getMessage());
        }
    }

    public static void saveMember(Member member) {
        saveMember(member.getOfflineMember());
    }

    private MemberUtils() {}
}
