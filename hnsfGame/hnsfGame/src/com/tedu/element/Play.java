package com.tedu.element;

import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;

public class Play extends ElementObj /* implements Comparable<Play>*/{
	private boolean isPlayer1=true;
	private boolean left=false; //左
	private boolean up=false;   //上
	private boolean right=false;//右
	private boolean down=false; //下
	
	public boolean isPlayer1() {
		return isPlayer1;
	}
	
	public void setPlayer1(boolean isPlayer1) {
		this.isPlayer1=isPlayer1;
	}

//	变量专门用来记录当前主角面向的方向,默认为是up
	private String fx="up";
	private boolean pkType=false;//攻击状态 true 攻击  false停止
	
	public Play() {}
	public Play(int x, int y, int w, int h, ImageIcon icon) {
		super(x, y, w, h, icon);
	}
	@Override
	public ElementObj createElement(String str) {
		String[] split = str.split(",");
		this.setX(Integer.parseInt(split[0]));
		this.setY(Integer.parseInt(split[1]));
		/**
		 * 单机和 双人模式的选择
		 * @author 孙海露
		 */
		ImageIcon icon2 = GameLoad.imgMap.get(split[2]);
		this.setW(icon2.getIconWidth());
		this.setH(icon2.getIconHeight());
		this.setIcon(icon2);
		return this;
	}
	
	
	@Override
	public void showElement(Graphics g) {
//		绘画图片
		g.drawImage(this.getIcon().getImage(), 
				this.getX(), this.getY(), 
				this.getW(), this.getH(), null);
	}
	@Override   // 注解 通过反射机制，为类或者方法或者属性 添加的注释(相当于身份证判定)
	
	public void die() {
	    // 玩家死亡后可以重生
	    if (this.isPlayer1) {
	        this.setX(500);
	        this.setY(500);
	        this.setLive(true);
	    } else {
	        this.setX(700);
	        this.setY(500);
	        this.setLive(true);
	    }
	    
	    // 重置方向
	    this.fx = "up";
	    this.updateImage();
	}
	
	public void keyClick(boolean bl,int key) { //只有按下或者鬆開才會 调用这个方法
//		System.out.println("测试："+key);

		if(this.isPlayer1()) {
			handlePlayer1Controls(bl,key);
		}else {
			handlePlayer2Controls(bl,key);
		}
	}
	
	

	private void handlePlayer1Controls(boolean bl, int key) {
		if(bl) {//按下
		switch(key) {
		case 37: 
			this.down=false;this.up=false;
			this.right=false;this.left=true; this.fx="left"; break;
		case 38: 
			this.right=false;this.left=false;
			this.down=false; this.up=true;   this.fx="up"; break;
		case 39: 
			this.down=false;this.up=false;
			this.left=false; this.right=true; this.fx="right";break;
		case 40: 
			this.right=false;this.left=false;
			this.up=false; this.down=true;  this.fx="down";break;
		case 32:
			this.pkType=true;break;//开启攻击状态
		}
	}else {
		switch(key) {
		case 37: this.left=false;  break;
		case 38: this.up=false;    break;
		case 39: this.right=false; break;
		case 40: this.down=false;  break;
		case 32: this.pkType=false; break;//关闭攻击状态
		}
	}
		
	}
	private void handlePlayer2Controls(boolean bl, int key) {
		  if (bl) { // 按下
	            switch (key) {
	                case 65: // A (左)
	                	this.down=false;this.up=false;
	        			this.right=false;this.left=true; this.fx="left"; break;
	                case 87: // W (上)
	                	this.right=false;this.left=false;
	        			this.down=false; this.up=true;   this.fx="up"; break;
	                case 68: // D (右)
	                	this.down=false;this.up=false;
	        			this.left=false; this.right=true; this.fx="right";break;
	                case 83: // S (下)
	                	this.right=false;this.left=false;
	        			this.up=false; this.down=true;  this.fx="down";break;
	                case 70: // F
	                    this.pkType = true;
	                    break;
	            }
	        } else { // 释放
	            switch (key) {
	                case 65: this.left = false; break;
	                case 87: this.up = false; break;
	                case 68: this.right = false; break;
	                case 83: this.down = false; break;
	                case 70: this.pkType = false; break;
	            }
	        }
		
	}
	@Override
	public void move() {
		if (this.left && this.getX()>0) {
			this.setX(this.getX() - 1);
		}
		if (this.up  && this.getY()>0) {
			this.setY(this.getY() - 1);
		}
		if (this.right && this.getX()<780-this.getW()) {  //坐标的跳转由大家来完成
			this.setX(this.getX() + 1);
		}
		if (this.down && this.getY()<560-this.getH()) {
			this.setY(this.getY() + 1);
		}
	}
	@Override
	protected void updateImage() {
		String imageKey;
       if(isPlayer1) {
            // 玩家1使用默认方向图片
            imageKey = fx;
            
        } else {
            // 玩家2使用player2_前缀的图片
            imageKey = "player2_" + fx;
        }
        this.setIcon(GameLoad.imgMap.get(imageKey));
		
	}
	private long filetime=0;
	@Override   //添加子弹
	public void add(long gameTime) {//有啦时间就可以进行控制
		if(!this.pkType) {//如果是不发射状态 就直接return
			return;
		}
		this.pkType=false;
		ElementObj obj=GameLoad.getObj("file");  		
		ElementObj element = obj.createElement(this.toString());

		ElementManager.getManager().addElement(element, GameElement.PLAYFILE);

	}
	@Override
	public String toString() {
		int x=this.getX();
		int y=this.getY();
		switch(this.fx) {
		case "up": x+=20;break;  
		case "left": y+=20;break;
		case "right": x+=50;y+=20;break;
		case "down": y+=50;x+=20; break;
		}
		return "x:"+x+",y:"+y+",f:"+this.fx;
	}
	
	
	
}





