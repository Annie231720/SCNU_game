package com.tedu.element;

import java.awt.Graphics;
import java.util.Random;
import javax.swing.ImageIcon;

import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;


public class Enemy extends ElementObj {
    private String fx; // 当前方向(up/down/left/right)
    private Random random = new Random();
    private int moveTimer = 0;
    private int fireTimer=0;
    private final int FIRE_INTERVAL=80;
    private final int MOVE_INTERVAL = 60; // 每60帧改变一次方向
   
    public Enemy() {}
    
    public Enemy(int x, int y, int w, int h, ImageIcon icon) {
        super(x, y, w, h, icon);
        this.fx = getRandomDirection();
        updateEnemyImage();
    }

    @Override
    public ElementObj createElement(String str) {
        // 解析传入参数，如"x,y,direction"
        if(str != null && !str.isEmpty()) {
            String[] split = str.split(",");
            this.setX(Integer.parseInt(split[0]));
            this.setY(Integer.parseInt(split[1]));
            this.fx = split.length > 2 ? split[2] : getRandomDirection();
        } else {
            // 随机生成位置和方向
            this.setX(random.nextInt(600));
            this.setY(random.nextInt(500));
            this.fx = getRandomDirection();
        }
        
        this.setW(35);
        this.setH(40);
        updateEnemyImage();
        return this;
    }

    @Override
    public void showElement(Graphics g) {
        g.drawImage(this.getIcon().getImage(), 
                   this.getX(), this.getY(), 
                   this.getW(), this.getH(), null);
    }

    @Override
    protected void move() {
        // 随机改变方向逻辑
        moveTimer++;
        if(moveTimer >= MOVE_INTERVAL) {
            changeRandomDirection();
            moveTimer = 0;
        }
        
        // 临时存储移动后的位置
        int newX = this.getX();
        int newY = this.getY();
        
        // 根据当前方向计算新位置
        switch(fx) {
            case "up": 
                newY -= 1;
                break;
            case "down": 
                newY += 1;
                break;
            case "left": 
                newX -= 1;
                break;
            case "right": 
                newX += 1;
                break;
        }
        
        // 边界检测
        if(isPositionValid(newX, newY)) {
            this.setX(newX);
            this.setY(newY);
        } else {
            // 如果新位置无效，立即改变方向
            changeRandomDirection();
        }
    }

    /**
     * 检查位置是否有效（在边界内）
     */
    private boolean isPositionValid(int x, int y) {
        // 考虑坦克的宽度和高度
        int tankWidth = this.getW();
        int tankHeight = this.getH();
        
        return x >= 0 && 
               y >= 0 &&
               x + tankWidth <= 780 &&
               y + tankHeight <= 570;
    }

    // 随机获取方向
    private String getRandomDirection() {
        String[] directions = {"up", "down", "left", "right"};
        return directions[random.nextInt(4)];
    }

    // 随机改变方向并更新图片
    private void changeRandomDirection() {
        String newDir = getRandomDirection();
        if(!newDir.equals(fx)) { // 只有方向改变时才更新图片
            fx = newDir;
            updateEnemyImage();
        }
    }

    // 更新敌人图片（根据当前方向）
    private void updateEnemyImage() {
        String imageKey = "enemy_" + fx;
        ImageIcon icon = GameLoad.imgMap.get(imageKey);
        if(icon != null) {
            this.setIcon(icon);
        }
    }

    @Override
    protected void updateImage() {
        updateEnemyImage();
    }
    /**
     * 发射子弹方法
     */
    private void fire() {
        // 计算子弹发射的起始位置
        int bulletX = getBulletStartX();
        int bulletY = getBulletStartY();
        
        // 创建子弹对象
        ElementObj bullet = new EnemyFile().createElement(
            "x:" + bulletX + ",y:" + bulletY + ",f:" + this.fx
        );
        
        // 将子弹添加到元素管理器
        ElementManager.getManager().addElement(bullet, GameElement.ENEMYFILE);
    }

    /**
     * 计算子弹发射的X坐标
     */
    private int getBulletStartX() {
        switch(fx) {
            case "left": return this.getX() - 10;
            case "right": return this.getX() + this.getW();
            default: return this.getX() + this.getW()/2 - 5;
        }
    }

    /**
     * 计算子弹发射的Y坐标
     */
    private int getBulletStartY() {
        switch(fx) {
            case "up": return this.getY() - 10;
            case "down": return this.getY() + this.getH();
            default: return this.getY() + this.getH()/2 - 5;
        }
    }

    @Override
    protected void add(long gameTime) {
        // 发射冷却检测
        if(fireTimer++ < FIRE_INTERVAL) {
            return;
        }
        fireTimer = 0;
        fire(); // 发射子弹
    }

}