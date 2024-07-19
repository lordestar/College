package com.game;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class BackGround {
    //当前场景的背景图片
    private BufferedImage bgImage = null;
    //记录当前是第几个场景
    private int sort;
    //判断是不是最后一个场景
    private boolean flag;
    //存放所有的障碍物
    private List<Obstacle> obstacleList = new ArrayList<>();
    //存放所有的敌人
    private List<Enemy> enemyList = new ArrayList<>();
    //显示旗杆
    private BufferedImage gan = null;
    //显示城堡
    private BufferedImage tower=null;
    //是否到达旗杆位置
    private boolean isReach=false;
    //旗子是否落地
    private boolean isBase=false;
    public BackGround(){}

    //最后一关使用bg2背景，前两关使用bg1背景
    public BackGround(int sort,boolean flag){
        this.sort=sort;
        this.flag=flag;

        if(flag){
            bgImage=StaticValue.bg2;
        }else {
            bgImage=StaticValue.bg;
        }
        //判断是不是第一关
        if (sort==1){
            //绘制第一关地面和砖块
            for (int i = 0; i <27 ; i++) {
                obstacleList.add(new Obstacle(i*30,420,1,this));
            }//上地面type1
            for (int j = 0; j <=120 ; j+=30) {
                for (int i = 0; i <27 ; i++) {
                    obstacleList.add(new Obstacle(i*30,570-j,2,this));
                }
            }//下地面type2
            for (int i = 120; i <=150 ; i+=30) {
                obstacleList.add(new Obstacle(i,300,7,this));
            }
            for (int i = 300; i <=570 ; i+=30) {
                if (i==360||i==390||i==480||i==510||i==540){
                    obstacleList.add(new Obstacle(i,300,7,this));
                }else {
                    obstacleList.add(new Obstacle(i,300,0,this));
                }
            }
            for (int i = 420; i <450 ; i+=30) {
                obstacleList.add(new Obstacle(i,240,7,this));
            }
            //绘制水管
            for (int i = 360; i <600 ; i+=25) {
                if (i==360){
                    obstacleList.add(new Obstacle(620,i,3,this));
                    obstacleList.add(new Obstacle(645,i,4,this));
                }else {
                    obstacleList.add(new Obstacle(620,i,5,this));
                    obstacleList.add(new Obstacle(645,i,6,this));
                }
            }
            //绘制第一关的敌人
            enemyList.add(new Enemy(580,385,true,1,this));
            enemyList.add(new Enemy(635,420,true,2,328,418,this));

        }
        //判断是不是第二关
        if (sort==2){
            //绘制第二关地面
            for (int i = 0; i <27 ; i++) {
                obstacleList.add(new Obstacle(i*30,420,1,this));

            }
            for (int j = 0; j <=120 ; j+=30) {
                for (int i = 0; i <27 ; i++) {
                    obstacleList.add(new Obstacle(i*30,570-j,2,this));
                }
            }
            //绘制第一根水管
            for (int i = 360; i <600 ; i+=25) {
                if (i==360){
                    obstacleList.add(new Obstacle(60,i,3,this));
                    obstacleList.add(new Obstacle(85,i,4,this));
                }else {
                    obstacleList.add(new Obstacle(60,i,5,this));
                    obstacleList.add(new Obstacle(85,i,6,this));
                }
            }
            //绘制第二根水管
            for (int i = 330; i <600 ; i+=25) {
                if (i==330){
                    obstacleList.add(new Obstacle(620,i,3,this));
                    obstacleList.add(new Obstacle(645,i,4,this));
                }else {
                    obstacleList.add(new Obstacle(620,i,5,this));
                    obstacleList.add(new Obstacle(645,i,6,this));
                }
            }
            //绘制砖块
            obstacleList.add(new Obstacle(300,330,0,this));
            for (int i = 270; i <=330 ; i+=30) {
                if (i==270||i==330){
                    obstacleList.add(new Obstacle(i,360,0,this));
                }else {
                    obstacleList.add(new Obstacle(i,360,7,this));
                }
            }
            for (int i = 240; i <=360; i+=30) {
                if (i==240||i==360){
                    obstacleList.add(new Obstacle(i,390,0,this));
                }else {
                    obstacleList.add(new Obstacle(i,390,7,this));
                }
            }
            obstacleList.add(new Obstacle(240,300,0,this));
            for (int i = 360; i <=540 ; i+=60) {
                obstacleList.add(new Obstacle(i,270,7,this));
            }
            //绘制敌人
            enemyList.add(new Enemy(500,385,true,1,this));
            enemyList.add(new Enemy(200,385,true,1,this));
            enemyList.add(new Enemy(75,420,true,2,328,418,this));
            enemyList.add(new Enemy(635,420,true,2,298,388,this));

        }
        //判断是不是第三关
        if (sort==3){
            //绘制第三关地面
            for (int i = 0; i <27 ; i++) {
                obstacleList.add(new Obstacle(i*30,420,1,this));

            }
            for (int j = 0; j <=120 ; j+=30) {
                for (int i = 0; i <27 ; i++) {
                    obstacleList.add(new Obstacle(i*30,570-j,2,this));
                }
            }
            int temp=290;
            for (int i = 390; i>=270 ; i-=30) {
                for (int j = temp; j <=410 ; j+=30) {
                    obstacleList.add(new Obstacle(j,i,7,this));
                }
                temp+=30;
            }
            temp=60;
            for (int i = 390; i>=360 ; i-=30) {
                for (int j = temp; j <=90 ; j+=30) {
                    obstacleList.add(new Obstacle(j,i,7,this));
                }
                temp+=30;
            }
            //绘制旗杆和城堡
            gan = StaticValue.gan;
            tower=StaticValue.tower;
            //把旗子添加到旗杆上
            obstacleList.add(new Obstacle(515,220,8,this));

            enemyList.add(new Enemy(150,385,true,1,this));
        }
    }

    //三个变量的getter方法
    public BufferedImage getBgImage() {
        return bgImage;
    }

    public int getSort() {
        return sort;
    }

    public boolean isFlag() {
        return flag;
    }

    public List<Obstacle> getObstacleList() {
        return obstacleList;
    }

    public BufferedImage getGan() {
        return gan;
    }

    public BufferedImage getTower() {
        return tower;
    }

    public boolean isReach() {
        return isReach;
    }

    public void setReach(boolean reach) {
        isReach = reach;
    }

    public boolean isBase() {
        return isBase;
    }

    public void setBase(boolean base) {
        isBase = base;
    }

    public List<Enemy> getEnemyList() {
        return enemyList;
    }
}
