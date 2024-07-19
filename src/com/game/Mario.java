package com.game;

import java.awt.image.BufferedImage;

public class Mario implements Runnable{
    //马里奥的坐标
    private int x;
    private int y;
    //马里奥的状态和当前状态对应的图像
    private String status;
    private BufferedImage show=null;
    //BackGround对象用于获取障碍物信息
    private BackGround backGround = new BackGround();
    //线程对象用来实现马里奥的动作
    private Thread thread = null;
    //马里奥的移动速度和跳跃速度
    private int xSpeed;
    private int ySpeed;
    //用于取得马里奥运动图像的索引
    private int index;
    //马里奥上升下落时间
    private int upTime=0;
    //是否到达城堡
    private boolean isOK;
    //判断马里奥是否死亡
    private boolean isDead=false;
    //记录积分
    private int score=0;

    public Mario(){
    }

    public Mario(int x,int y){
        this.x=x;
        this.y=y;
        show = StaticValue.stand_R;
        this.status="stand--right";
        thread=new Thread(this);
        thread.start();
    }
    //向左移动
    public void leftMove(){
        //改变速度
        xSpeed=-5;
        //马里奥是否碰到旗子
        if (backGround.isReach()){
            xSpeed=0;
        }
        //判断马里奥是否处于空中
        if (status.indexOf("jump")!=-1){
            status="jump--left";
        }else {
            status = "move--left";
        }
    }
    //向右移动
    public void rightMove(){
        //改变速度
        xSpeed=5;
        //马里奥是否碰到旗子
        if (backGround.isReach()){
            xSpeed=0;
        }
        //判断马里奥是否处于空中
        if (status.indexOf("jump")!=-1){
            status="jump--right";
        }else {
            status = "move--right";
        }
    }
    //向左停止
    public void leftStop(){
        xSpeed=0;
        if(status.indexOf("jump")!=-1){
            status="jump--left";
        }else {
            status="stop--left";
        }
    }
    //向右停止
    public void rightStop(){
        xSpeed=0;
        if(status.indexOf("jump")!=-1){
            status="jump--right";
        }else {
            status="stop--right";
        }
    }

    //马里奥死亡方法
    public void death(){
        isDead=true;
    }

    @Override
    public void run() {
       while (true) {
           //判断是不是在障碍物上
           boolean onObstacle = false;
           //判断能不能向右走
           boolean canRight = true;
           //判断能不能向左走
           boolean canLeft = true;
           //判断是否碰到旗杆
           if (backGround.isFlag() && this.x >= 500) {
               this.backGround.setReach(true);
               //判断旗子是否下落完成
               if (this.backGround.isBase()) {
                   status = "move--right";
                   if (x < 690) {
                       x += 5;
                   } else {
                       isOK = true;
                   }
               } else {
                   if (y < 395) {
                       xSpeed = 0;
                       this.y += 5;
                       status = "jump--right";
                   }
                   if (y > 395) {
                       this.y = 395;
                       status = "stop--right";
                   }
               }
           } else {

               //遍历当前场景所有障碍物
               for (int i = 0; i < backGround.getObstacleList().size(); i++) {
                   Obstacle ob = backGround.getObstacleList().get(i);
                   if (ob.getY() == this.y + 25 && (ob.getX() > this.x - 30 && ob.getX() < this.x + 25)) {
                       onObstacle = true;
                   }
                   //判断是否跳起来顶到砖块
                   if ((ob.getY() >= this.y - 30 && ob.getY() <= this.y - 20) && (ob.getX() > this.x - 30 && ob.getX() < this.x + 25)) {
                       if (ob.getType() == 0) {
                           backGround.getObstacleList().remove(ob);//顶到type0砖块移除
                           score += 1;
                       }
                       upTime = 0;//顶到砖块下落
                   }
                   //是否能往右走
                   if (ob.getX() == this.x + 25 && (ob.getY() > this.y - 30 && ob.getY() < this.y + 25)) {
                       canRight = false;
                   }
                   //是否能往左走
                   if (ob.getX() == this.x - 30 && (ob.getY() > this.y - 30 && ob.getY() < this.y + 25)) {
                       canLeft = false;
                   }
               }

               //实现马里奥的跳跃
               if (onObstacle && upTime == 0) {
                   if (status.indexOf("left") != -1) {
                       if (xSpeed != 0) {
                           status = "move--left";
                       } else {
                           status = "stop--left";
                       }
                   } else {
                       if (xSpeed != 0) {
                           status = "move--right";
                       } else {
                           status = "stop--right";
                       }
                   }
               } else {
                   if (upTime != 0) {
                       upTime--;
                   } else {
                       fall();
                   }
                   y += ySpeed;
               }
           }
           if (canLeft && xSpeed < 0 || canRight && xSpeed > 0) {
               x += xSpeed;
               //到最左边无法运动
               if (x < 0) {
                   x = 0;
               }
           }
           //判断是不是移动状态
           if (status.contains("move")) {
               //马里奥运动时图像来回切换
               index = index == 0 ? 1 : 0;
           }
           //判断移动方向和停止方向
           if ("move--left".equals(status)) {
               show = StaticValue.run_L.get(index);
           }
           if ("move--right".equals(status)) {
               show = StaticValue.run_R.get(index);
           }
           if ("stop--left".equals(status)) {
               show = StaticValue.stand_L;
           }
           if ("stop--right".equals(status)) {
               show = StaticValue.stand_R;
           }

           try {
               Thread.sleep(50);
           } catch (InterruptedException e) {
               throw new RuntimeException(e);
           }
           //判断跳跃方向
           if ("jump--left".equals(status)) {
               show = StaticValue.jump_L;
           }
           if ("jump--right".equals(status)) {
               show = StaticValue.jump_R;
           }
           //判断马里奥是否遇到敌人死亡或者踩死蘑菇敌人
           for (int i = 0; i < backGround.getEnemyList().size(); i++) {
               Enemy e = backGround.getEnemyList().get(i);
               if (e.getY()==this.y+20&&(e.getX()-25<this.x&&e.getX()+35>=this.x)){
                   if (e.getType()==1){
                       e.death();
                       upTime=3;
                       ySpeed=-10;
                       score+=2;
                   } else if (e.getType()==2) {
                       //马里奥死亡
                       death();
                   }
               }
               if ((e.getX()+35>this.x&&e.getX()-25<this.x)&&(e.getY()+35>this.y&&e.getY()-20<this.y)){
                   //马里奥死亡
                   death();
               }
           }
       }
    }

    //马里奥跳跃的方法
    public void jump(){
        if (status.indexOf("jump")==-1){
            if (status.indexOf("left")!=-1){
                status="jump--left";
            }else {
                status="jump--right";
            }
            ySpeed=-10;
            upTime=7;
        }
        //马里奥是否碰到旗子
        if (backGround.isReach()){
            ySpeed=0;
        }
    }
    //马里奥下落的方法
    public void fall(){
        if (status.indexOf("left")!=-1){
            status="jump--left";
        }else {
            status="jump--right";
        }
        ySpeed=10;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public BufferedImage getShow() {
        return show;
    }

    public void setShow(BufferedImage show) {
        this.show = show;
    }

    public void setBackGround(BackGround backGround) {
        this.backGround = backGround;
    }

    public boolean isOK() {
        return isOK;
    }

    public boolean isDead() {
        return isDead;
    }

    public int getScore() {
        return score;
    }
}
