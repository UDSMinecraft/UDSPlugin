package com.undeadscythes.udsplugin;

import org.apache.commons.lang.*;

/**
 * A chat censor.
 * @author UndeadScythes
 */
public class Censor {
    /**
     * Check if a string has bad words in it.
     * @param string String to check.
     * @return <code>true</code> if the word was clean, <code>false</code> otherwise.
     */
    public static boolean censor(String string) {
        String[] badWords = new String[]{"fuvg", "shpx", "phag", "avttre", "cnxv", "shpxre", "onfgneq", "obyybpxf", "nefrubyr", "juber", "gjng", "fbqqvat", "fcnfgvp", "jnaxre", "fynt", "cvffvat", "qvpx", "chffl", "funt"};
        for(String word : badWords) {
            if(string.toLowerCase().contains(rot13(word))) {
                return false;
            }
        }
        return true;
    }

    /**
     * This let's me hide the nasty words in the previous function. Interesting fact: "FU" maps to "SH". It took me a few minutes trying to figure out why the first two letters weren't being mapped... but they were!
     * @param string String to rot.
     * @return Rotted string.
     */
    private static String rot13(String string) {
        char[] alphabet = "abcdefghijklmnopqrstuvwxyzabcdefghijklm".toCharArray();
        String rotted = "";
        for(int i = 0 ; i < string.length(); i++) {
            rotted = rotted.concat(CharUtils.toString(alphabet[ArrayUtils.indexOf(alphabet, string.charAt(i)) + 13]));
        }
        return rotted;
    }
}
