package com.undeadscythes.udsplugin;

import java.util.*;
import org.apache.commons.lang.*;

/**
 * A chat censor.
 * @author UndeadScythes
 */
public final class Censor {
    private static String[] badWords;
    private static final String[] ROTTED_WORDS = new String[]{"fuvg", "shpx", "phag", "avttre", "cnxv", "shpxre", "onfgneq", "obyybpxf", "nefrubyr", "juber", "gjng", "fbqqvat", "fcnfgvp", "jnaxre", "fynt", "cvffvat", "qvpx", "chffl", "funt"};
    private static final char[] ALPHABET = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    private Censor() {}

    /**
     * Check if a string has bad words in it.
     * @param string String to check.
     * @return <code>true</code> if the word was clean, <code>false</code> otherwise.
     */
    public static boolean noCensor(final String string) {
        for(String word : badWords) {
            if(string.toLowerCase().contains(word)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Replace all occurrences of censored words.
     * @param message The message to 'fix'.
     * @return The 'fixed' message.
     */
    public static String fix(final String message) {
        String fixedMessage = message;
        for(String word : badWords) {
            fixedMessage = fixedMessage.replaceAll("(?i)" + word, "****");
        }
        return fixedMessage;
    }

    /**
     * This lets me hide the nasty words in the code.
     * Interesting fact: "FU" maps to "SH". It took me a few minutes trying to
     * figure out why the first two letters weren't being mapped...
     * but they were!
     * @return Bad words in plaintext.
     */
    private static String[] rotWords() {
        final ArrayList<String> words = new ArrayList<String>();
        for(String rotted : ROTTED_WORDS) {
            String word = "";
            for(int i = 0 ; i < rotted.length(); i++) {
                word = word.concat(CharUtils.toString(ALPHABET[(ArrayUtils.indexOf(ALPHABET, rotted.charAt(i)) + 13) % 26]));
            }
            words.add(word);
        }
        return words.toArray(new String[words.size()]);
    }

    /**
     * Initialise the censor by rot-ing all the censored words.
     */
    public static void initCensor() {
        badWords = rotWords();
    }
}
