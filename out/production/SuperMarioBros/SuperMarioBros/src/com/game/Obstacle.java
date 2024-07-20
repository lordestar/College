package com.game;

import java.awt.image.BufferedImage;

public class Obstacle implements Runnable{
    //坐标变量
    private int x;
    private int y;
    //记录障碍物类型
    private int type;
    //显示图像
    private BufferedImage show=null;
    //当前场景对象
    private BackGround bg = null;
    //旗子下落的线程对象
    private Thread thread = new Thread(this);

    public Obstacle(int x,int y,int type,BackGround bg){
        this.x=x;
        this.y=y;
        this.type=type;
        this.bg=bg;
        show = StaticValue.obstacle.get(type);
        //如果是旗子，则开启线程
        if(type==8){
            thread.start();
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getType() {
        return type;
    }

    public BufferedImage getShow() {
        return show;
    }


    @Override
    public void run() {
        while(true){
            if(bg.isReach()){
                if (this.y<374){
                    this.y+=5;
                }else {
                    this.bg.setBase(true);
                }
            }
            try {
                thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
