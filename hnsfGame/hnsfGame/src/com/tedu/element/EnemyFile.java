package com.tedu.element;

import java.awt.Color;
import java.awt.Graphics;

/**
 * @说明 敌人子弹类
 */
public class EnemyFile extends ElementObj {
    //private int attack = 1;    // 攻击力
    private int moveNum = 2;   // 移动速度（比玩家子弹稍慢）
    private String fx;         // 子弹方向
    
    public EnemyFile() {} // 空构造方法

    @Override
    public ElementObj createElement(String str) {
        // 参数格式："x:300,y:200,f:left"
        String[] split = str.split(",");
        for(String param : split) {
            String[] keyValue = param.split(":");
            switch(keyValue[0]) {
                case "x": this.setX(Integer.parseInt(keyValue[1])); break;
                case "y": this.setY(Integer.parseInt(keyValue[1])); break;
                case "f": this.fx = keyValue[1]; break;
            }
        }
        this.setW(10);  
        this.setH(10);
        return this;
    }

    @Override
    public void showElement(Graphics g) {
        g.setColor(Color.RED); // 敌人子弹用黄色区分
        g.fillOval(this.getX(), this.getY(), this.getW(), this.getH());
    }

    @Override
    protected void move() {
        // 边界检测
        if(this.getX() < 0 || this.getX() > 780 ||
           this.getY() < 0 || this.getY() > 560) {
            this.setLive(false);
            return;
        }
        
        // 根据方向移动
        switch(this.fx) {
            case "up":    this.setY(this.getY() - moveNum); break;
            case "down":  this.setY(this.getY() + moveNum); break;
            case "left":  this.setX(this.getX() - moveNum); break;
            case "right": this.setX(this.getX() + moveNum); break;
        }
    }
}