package com.tedu.manager;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.ImageIcon;

import com.tedu.element.ElementObj;

public class GameStateElement extends ElementObj {
    public static final int RUNNING = 0;
    public static final int WIN = 1;
    public static final int LOSE = 2;
    
    private int state = RUNNING;
    
    public GameStateElement() {}
    
    public void setState(int state) {
        this.state = state;
    }
    public int getState() {
    	// TODO 自动生成的方法存根
    	return 0;
    	}
    
    @Override
    public void showElement(Graphics g) {
        if (state == RUNNING) return;
        
        // 半透明黑色背景
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, 800, 600);
        
        // 设置字体
        g.setFont(new Font("宋体", Font.BOLD, 60));
        
        if (state == WIN) {
            g.setColor(Color.GREEN);
            g.drawString("游戏胜利!", 280, 250);
        } else if (state == LOSE) {
            g.setColor(Color.RED);
            g.drawString("游戏失败!", 280, 250);
        }
        
        // 提示信息
        g.setFont(new Font("宋体", Font.PLAIN, 30));
        g.setColor(Color.WHITE);
        g.drawString("按R键重新开始", 300, 350);
    }

}