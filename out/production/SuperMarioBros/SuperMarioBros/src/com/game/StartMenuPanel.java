package com.game;

import javazoom.jl.decoder.JavaLayerException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;

public class StartMenuPanel extends JFrame{

    Image offScreenImage = null;
    //游戏状态 0 游戏未开始， 1 单人模式， 2 双人模式
    static int state= 0;
    //游戏是否开始
    private boolean start = false;
    //临时变量
    int a = 1;
    //窗口长宽
    int width = 800;
    int height = 600;
    //游戏指针
    Image select = Toolkit.getDefaultToolkit().getImage(System.getProperty("user.dir")+"/src/images/"+"arrowhead.png");
    //开始菜单背景
    Image bg = Toolkit.getDefaultToolkit().getImage(System.getProperty("user.dir")+"/src/images/"+"bg.png");
    //游戏标题
    Image title= Toolkit.getDefaultToolkit().getImage(System.getProperty("user.dir")+"/src/images/"+"title1.png");
    //指针初始高度
    int y = 340;

    //窗口的启动方法
    public void launch(){
        //标题
        setTitle("超级玛丽联机版QwQ");
        //窗口初始大小
        setSize(width, height);
        //使屏幕居中
        setLocationRelativeTo(null);
        //添加关闭事件
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //用户不能调整大小
        setResizable(false);
        //使窗口可见
        setVisible(true);
        //添加键盘事件
        this.addKeyListener(new KeyMonitor());

        new StartMenuPanel.StartThread().start();


    }

    class StartThread extends Thread {

        public void run() {
            while (true) {
                repaint();
                try {
                    Thread.sleep(16);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        //创建和容器一样大小的Image图片
        if(offScreenImage ==null){
            offScreenImage=this.createImage(width, height);
        }

        //获得该图片的画布
        Graphics gImage= offScreenImage.getGraphics();
        //添加背景图片
        gImage.drawImage(bg,0,0,null);
        //添加标题
        gImage.drawImage(title,230,60,null);
        //挂变画笔颜色
        gImage.setColor(Color.BLACK);
        //改变文字大小和样式
        gImage.setFont(new Font("仿宋",Font.BOLD,40));
        if(state == 0){
            //添加文字
            gImage.drawString("选择游戏模式",265,300);
            gImage.drawString("单人游戏",300,370);
            gImage.drawString("双人游戏",300,450);
            gImage.drawString("按1，2选择模式，按回车开始游戏",100,550);
            gImage.drawImage(select,250,y,null);
        }
        else if(state == 1||state == 2){
            //进入单人模式或双人模式
            if(state == 1){
                dispose();
            }
            else{
                //双人模式
                dispose();
            }
        }
        /** 将缓冲区绘制好哦的图形整个绘制到容器的画布中 */
        g.drawImage(offScreenImage, 0, 0, null);
    }

    private class KeyMonitor extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            //super.keyPressed(e);
            int key = e.getKeyCode();
            switch (key){
                case KeyEvent.VK_1:
                    if(!start){
                        y = 340;
                        a = 1;
                    }
                    System.out.println(state);
                    break;
                case KeyEvent.VK_2:
                    if(!start){
                        y = 421;
                        a = 2;
                    }
                    System.out.println(state);
                    break;
                case KeyEvent.VK_ENTER:
                    state = a;
                    start = true;
                    System.out.println("state:"+""+state);
                    break;
                default:
                    break;
            }
        }
    }

    public int getState() {
        return state;
    }
}

