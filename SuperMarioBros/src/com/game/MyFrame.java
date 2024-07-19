package com.game;

import javazoom.jl.decoder.JavaLayerException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class MyFrame extends JFrame implements KeyListener,Runnable{
    //存储所有的背景
    private List<BackGround> allBg=new ArrayList<>();
    //存储当前的背景
    private BackGround nowBg=new BackGround();
    //用于双缓存的变量
    private Image offScreenImage=null;
    private Image offScreenImage2=null;

    //马里奥对象
    private Mario mario= new Mario();
    //用于实现马里奥运动的线程对象
    private Thread thread = new Thread(this);
    //记录游戏状态是否开始，0为未开始，1为开始
    private int state=0;
    //选择指针
    private Image select = Toolkit.getDefaultToolkit().getImage("images/arrowhead.png");

    public MyFrame(){
    //设置窗口
        this.setSize(800,600);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("超级玛丽联机版QwQ");
    //添加键盘监听
        this.addKeyListener(this);

    //初始化图片
        StaticValue.init();
    //初始化马里奥对象
        mario = new Mario(10,355);
    //创建全部的场景
        for (int i = 1; i <=3 ; i++) {
            allBg.add(new BackGround(i,i==3?true:false));
        }
    //设置第一个场景为当前场景
        nowBg=allBg.get(0);
        mario.setBackGround(nowBg);
    //绘制图像
        repaint();
        thread.start();
    //播放音乐
        try {
            new Music();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (JavaLayerException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        MyFrame myFrame = new MyFrame();
        myFrame.run();
    }

    //重写KeyListener中的方法
    @Override
    public void keyTyped(KeyEvent e) {

    }
//按下键盘时调用
    @Override
    public void keyPressed(KeyEvent e) {
        //移动
        if (e.getKeyCode()==39){
            mario.rightMove();
        }
        if (e.getKeyCode()==37){
            mario.leftMove();
        }
        //跳跃
        if (e.getKeyCode()==38){
            mario.jump();
        }

    }
//松开键盘时调用
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode()==39){
            mario.rightStop();
        }
        if (e.getKeyCode()==37){
            mario.leftStop();
        }
    }
    //重写paint方法

    @Override
    public void paint(Graphics g) {
        if (offScreenImage==null){
            offScreenImage=createImage(800,600);
        }
        Graphics graphics = offScreenImage.getGraphics();
        graphics.fillRect(0,0,800,600);
        //绘制背景
        graphics.drawImage(nowBg.getBgImage(), 0,0,this);
            //绘制敌人
            for (Enemy enemy:nowBg.getEnemyList()) {
                graphics.drawImage(enemy.getShow(),enemy.getX(),enemy.getY(),this);
            }
            //绘制障碍物
            for (Obstacle ob:nowBg.getObstacleList()) {
                graphics.drawImage(ob.getShow(),ob.getX(),ob.getY(),this);
            }
            //绘制城堡和旗杆
            graphics.drawImage(nowBg.getTower(),620,270,this);
            graphics.drawImage(nowBg.getGan(),500,220,this);
            //绘制马里奥
            graphics.drawImage(mario.getShow(),mario.getX(),mario.getY(),this);

            //绘制分数
            Color c = graphics.getColor();
            graphics.setColor(Color.BLACK);
            graphics.setFont(new Font("黑体",Font.BOLD,25));
            graphics.drawString("当前分数："+mario.getScore(),300,100);
            graphics.setColor(c);

        //将缓冲区的图片绘制到窗口中
        g.drawImage(offScreenImage,0,0,this);

    }

    @Override
    public void run() {
        while (true){
            repaint();
            try {
                Thread.sleep(16);
                //到达最右边进入下一个场景
                if (mario.getX()>=775){
                    nowBg=allBg.get(nowBg.getSort());
                    mario.setBackGround(nowBg);
                    mario.setX(10);
                    mario.setY(355);
                }

                //判断马里奥是否死亡
                if (mario.isDead()){
                    JOptionPane.showMessageDialog(this,"你死了QAQ~");
                    System.exit(0);
                }
                //判断游戏是否结束
                if (mario.isOK()){
                    JOptionPane.showMessageDialog(this,"恭喜你成功通关~");
                    System.exit(0);
                }

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
