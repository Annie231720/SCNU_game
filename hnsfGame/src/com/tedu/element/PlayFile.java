package com.tedu.element;

import java.awt.Color;
import java.awt.Graphics;



/**
 * @说明 玩家子弹类，本类的实体对象是由玩家对象调用和创建
 * @子类的开发步骤
 *   1.继承与元素基类;重写show方法
 *   2.按照需求选择性重写其他方法例如：move等
 *   3.思考并定义子类特有的属性
 */
public class PlayFile extends ElementObj{
	//private int attack;//攻击力
	private int moveNum=3;//移动速度值
	private String fx;
	public PlayFile() {}//一个空的构造方法
	public  ElementObj createElement(String str) {//定义字符串的规则
		String[] split = str.split(",");
		for(String str1 : split) {//X:3
			String[] split2 = str1.split(":");// 0下标 是 x,y,f   1下标是值
			switch(split2[0]) {
			case "x": this.setX(Integer.parseInt(split2[1]));break;
			case "y":this.setY(Integer.parseInt(split2[1]));break;
			case "f":this.fx=split2[1];break;
			}
		}
		this.setW(10);
		this.setH(10);
		return this;
	}
	@Override
	public void showElement(Graphics g) {	
		g.setColor(Color.red);// new Color(255,255,255)
		g.fillOval(this.getX()-8, this.getY(), this.getW(), this.getH());
	}	
	@Override
	protected void move() {
		if(this.getX()<0 || this.getX() >900 || 
				this.getY() <0 || this.getY()>600) {
			this.setLive(false);
			return;
		}
		switch(this.fx) {
		case "up": this.setY(this.getY()-this.moveNum);break;
		case "left": this.setX(this.getX()-this.moveNum);break;
		case "right": this.setX(this.getX()+this.moveNum);break;
		case "down": this.setY(this.getY()+this.moveNum);break;
		}
		
	}
	/**
	 * 对于子弹来说：1.出边界  2.碰撞  3.玩家放保险
	 * 处理方式就是，当达到死亡的条件时，只进行 修改死亡状态的操作。
	 */

	
}





