package com.tedu.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tedu.element.ElementObj;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;
import com.tedu.manager.GameStateElement;
/**
 * @说明 监听类，用于监听用户的操作 KeyListener
 *
 */
public class GameListener implements KeyListener{
	private ElementManager em=ElementManager.getManager();
	private Set<Integer> set=new HashSet<Integer>();
	
	@Override
	public void keyTyped(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {
		int key=e.getKeyCode();
		
	    // 检查游戏状态
	    int gameState = em.getGameState();
	    if (gameState != GameStateElement.RUNNING) {
	        // 在游戏结束状态下，R键重新开始
	        if (key == KeyEvent.VK_R) {
	            em.setGameState(GameStateElement.RUNNING);
	            restartGame();
	        }
	        return;
	    }
	    
		if(set.contains(key)) { 
			return;
		}
		set.add(key);
		List<ElementObj> play = em.getElementsByKey(GameElement.PLAY);
		for(ElementObj obj:play) {
			obj.keyClick(true, e.getKeyCode());
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		if(!set.contains(e.getKeyCode())) {
			return;
		}
		set.remove(e.getKeyCode());
		List<ElementObj> play = em.getElementsByKey(GameElement.PLAY);
		for(ElementObj obj:play) {
			obj.keyClick(false, e.getKeyCode());
		}
	}
	
//添加游戏重启方法
private void restartGame() {
 // 清除所有元素
 for (List<ElementObj> list : em.getGameElements().values()) {
     list.clear();
 }
 
 // 重新加载游戏
 GameLoad.loadImg();
 GameLoad.MapLoad(5);
 GameLoad.loadPlay(2); // 单人或双人模式
 GameLoad.loadEnemy(5);
}
}
