package com.game;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Music {
    public Music(int music) throws FileNotFoundException, JavaLayerException {
        //music 0为游戏背景音乐，1为开始菜单音乐
            Player player;

        String str = null;
        if (music==0) {
            str = System.getProperty("user.dir")+"/src/Music/music.wav";
        }else{
            str = System.getProperty("user.dir")+"/src/Music/startMusic.wav";
        }

        BufferedInputStream name = new BufferedInputStream(new FileInputStream(str));
            player = new Player(name);
            player.play();
    }
}
