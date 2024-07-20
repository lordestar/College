package com.game;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Start {
    public static void main(String[] args) {

        //启动菜单窗口
        StartMenuPanel gamePanel = new StartMenuPanel();
        gamePanel.launch();

        // 添加窗口关闭监听器到菜单窗口
        gamePanel.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                // 当菜单窗口关闭时，创建游戏窗口
                MyFrame myFrame = new MyFrame();
            }
        });

    }
}
