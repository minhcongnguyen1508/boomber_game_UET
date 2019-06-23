/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uet.oop.bomberman.sound;

import java.io.File;

import uet.oop.bomberman.Game;
import uet.oop.bomberman.exceptions.ErrorDialog;

/**
 *
 * @author VŨ ĐỨC
 */
public class BackgroundMusic {
       /** SoundPlayer object */
    private static Object player;
    /** last music played */
    private static int lastSelection = -1;

    static {
        /** if Java2 available */
        if (Game.J2) {
           /** create the SoundPlayer object and load the music files */
           try {
               player = new SoundPlayer(
           new File(Game.RP + "Sounds/BomberBGM/").
           getCanonicalPath());
           }
           catch (Exception e) { new ErrorDialog(e); }
           ((SoundPlayer)player).open();
        }
    }

    /**
     * Change BGM music.
     * @param arg BGM music to chagne to
     */
    public static void change(String arg) {
        /** if Java 2 available */
        if (Game.J2) {
            /**
             * change music only if the the current music is not equal to
             * the specified music
             */
            int i = 0;
            while (i < ((SoundPlayer)player).sounds.size() &&
            ((SoundPlayer)player).sounds.elementAt(i).
            toString().indexOf(arg) < 0) i += 1;
            if (i != lastSelection && i <
               ((SoundPlayer)player).sounds.size()) {
                lastSelection = i;
                ((SoundPlayer)player).change(lastSelection, true);
            }
        }
    }

    /**
     * Stop playing the BGM.
     */
    public static void stop()
    {
        /** if Java 2 available */
        if (Game.J2) {
           ((SoundPlayer)player).controlStop();
        }
    }

    /**
     * Mute the BGM.
     */
    public static void mute()
    {
        /** if Java 2 available */
        if (Game.J2) {
           ((SoundPlayer)player).mute();
        }
    }

    /**
     * Unmute the BGM.
     */
    public static void unmute()
    {
        /** if Java 2 available */
        if (Game.J2) {
            ((SoundPlayer)player).unmute();
        }
    }
}
