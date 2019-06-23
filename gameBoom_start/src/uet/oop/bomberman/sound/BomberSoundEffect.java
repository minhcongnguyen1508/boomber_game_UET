/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uet.oop.bomberman.sound;

import java.io.*;
import uet.oop.bomberman.Game;

public class BomberSoundEffect extends Thread{
    public BomberSoundEffect() {
        start();
    }

    public void playSound(String str) {
        /** if Java 2 is available */
        if (Game.J2) {
            SoundPlayer sound = null;
            try {
                /** create sound player */
                sound = new SoundPlayer(
                new File(Game.RP +
                "Sounds/BomberSndEffects/" +  str + ".mid").
                getCanonicalPath());
            }
            catch (Exception e) { }
            /** open file */
            ((SoundPlayer)sound).open();
            /** then play sound */
            sound.change(0, false);
        }
    }
}
