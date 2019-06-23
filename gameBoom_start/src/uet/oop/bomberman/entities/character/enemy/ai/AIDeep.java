package uet.oop.bomberman.entities.character.enemy.ai;

import static java.lang.Math.*;
import java.util.List;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Enemy;
import uet.oop.bomberman.level.Coordinates;

public class AIDeep extends AI {
	Bomber _bomber;
	Enemy _enemy;
	public AIDeep(Bomber bomber, Enemy e) {
                //if(bomb == null ) System.out.println("NUll bomb");
		_bomber = bomber;
		_enemy = e;
             
	}

	@Override
	public int calculateDirection() {
		// TODO: cài đặt thuật toán tìm đường đi
                boolean flag;
                if(sqrt((_bomber.getX() - _enemy.getX())*(_bomber.getX() - _enemy.getX()) + (_bomber.getY() - _enemy.getY())*(_bomber.getY() - _enemy.getY())) <= 40){
                    flag = true;
                }else{
                    flag = false;
                }
                _enemy.setSpeed(flag);
		return random.nextInt(4);
	}

}