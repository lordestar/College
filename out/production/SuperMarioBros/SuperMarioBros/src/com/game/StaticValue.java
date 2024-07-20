package com.game;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StaticValue {
    //背景
    public static BufferedImage bg = null;
    public static BufferedImage bg2= null;
    //马里奥动作
    public static BufferedImage jump_L =null;
    public static BufferedImage jump_R =null;
    public static BufferedImage stand_L =null;
    public static BufferedImage stand_R =null;
    //城堡
    public static BufferedImage tower = null;
    //旗杆
    public static BufferedImage gan = null;
    //障碍物
    public static List<BufferedImage> obstacle = new ArrayList<>();
    //马里奥左跑
    public static List<BufferedImage> run_L = new ArrayList<>();
    //马里奥右跑
    public static List<BufferedImage> run_R = new ArrayList<>();
    //蘑菇敌人
    public static List<BufferedImage> mogu = new ArrayList<>();
    //食人花敌人
    public static List<BufferedImage> flower = new ArrayList<>();
    //路径前缀
    public static String path=System.getProperty("user.dir")+"/src/images/";
    //初始化方法
    public static void init(){
        try {
            //加载图片
            bg = ImageIO.read(new File(path+"bg.png"));
            bg2=ImageIO.read(new File(path+"bg2.png"));
            stand_L=ImageIO.read(new File(path + "s_mario_stand_L.png"));
            stand_R=ImageIO.read(new File(path + "s_mario_stand_R.png"));
            tower=ImageIO.read(new File(path+"tower.png"));
            gan=ImageIO.read(new File(path+"gan.png"));
            jump_L=ImageIO.read(new File(path+"s_mario_jump1_L.png"));
            jump_R=ImageIO.read(new File(path+"s_mario_jump1_R.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //马里奥向左的两个动作
        for (int i = 1; i <=2 ; i++) {
            try {
                run_L.add(ImageIO.read(new File(path+"s_mario_run"+i+"_L.png")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        //马里奥向右的两个动作
        for (int i = 1; i <=2 ; i++) {
            try {
                run_R.add(ImageIO.read(new File(path+"s_mario_run"+i+"_R.png")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        //加载障碍物
        try {
            obstacle.add(ImageIO.read(new File(path+"brick.png")));
            obstacle.add(ImageIO.read(new File(path+"soil_up.png")));
            obstacle.add(ImageIO.read(new File(path+"soil_base.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //加载水管
        for (int i = 1; i <=4 ; i++) {
            try {
                obstacle.add(ImageIO.read(new File(path+"pipe"+i+".png")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        //加载不可破坏的砖块和旗子
        try {
            obstacle.add(ImageIO.read(new File(path+"brick2.png")));
            obstacle.add(ImageIO.read(new File(path+"flag.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //加载蘑菇敌人
        for (int i = 1; i <=3 ; i++) {
            try {
                mogu.add(ImageIO.read(new File(path+"fungus"+i+".png")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        //加载食人花
        for (int i = 1; i <=2 ; i++) {
            try {
                flower.add(ImageIO.read(new File(path+"flower1."+i+".png")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
