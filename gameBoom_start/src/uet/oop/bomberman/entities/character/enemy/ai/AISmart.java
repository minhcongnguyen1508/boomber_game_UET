package uet.oop.bomberman.entities.character.enemy.ai;

import static java.lang.Math.abs;
import java.util.List;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Enemy;

public class AISmart extends AI {
	Bomber _bomber;
	Enemy _enemy;
	List<Bomb> _bombs;
	public AISmart(Bomber bomber, Enemy e, List<Bomb> bombs) {
                //if(bomb == null ) System.out.println("NUll bomb");
		_bomber = bomber;
		_enemy = e;
                _bombs = bombs;
	}

	@Override
	public int calculateDirection() {
		// TODO: cài đặt thuật toán tìm đường đi
                
		if(_bomber == null)
			return random.nextInt(5);
		int vertical = random.nextInt(4);
		if(vertical == 1) {
			int v = calculateRowDirection();
			if(v != -1)
				return v;
			else
				return calculateColDirection();
			
		} else {
			int h = calculateColDirection();
			if(h != -1)
				return h;
			else
				return calculateRowDirection();
		}
	}
        protected int calculateColDirection() {
            if(_bombs.isEmpty()){
                if(_bomber.getXTile() < _enemy.getXTile())// && abs(_bomb.getXTile() - _enemy.getXTile()) > 4 )
			return 3;//Sang trai
                else if(_bomber.getXTile() > _enemy.getXTile())// && abs(_bomb.getXTile() - _enemy.getXTile()) <= 4 )
			return 1;//Sang phai
            }else{
                if(_bomber.getXTile() < _enemy.getXTile() && abs((int)_bombs.get(_bombs.size() - 1).getXTile() - _enemy.getXTile()) > 8){
                    if(abs(_bomber.getXTile() - _enemy.getXTile()) < 4 && abs(_bomber.getYTile() - _enemy.getYTile()) < 4 && _enemy.getSpeed() < Game.getBomberSpeed()*1.5){ 
                        _enemy.setSpeed(true);
                    }else if (abs(_bomber.getXTile() - _enemy.getXTile()) >= 4 && abs(_bomber.getYTile() - _enemy.getYTile()) >= 4 && _enemy.getSpeed() > Game.getBomberSpeed()){
                        _enemy.setSpeed(false);
                        //flagSpeed = true;
                    }return 3;
                }
			
                else if(_bomber.getXTile() < _enemy.getXTile() && abs((int)_bombs.get(_bombs.size() - 1).getXTile() - _enemy.getXTile()) <= 8){
                    if(abs(_bomber.getXTile() - _enemy.getXTile()) < 4 && abs(_bomber.getYTile() - _enemy.getYTile()) < 4 && _enemy.getSpeed() < Game.getBomberSpeed()*1.5){ 
                        _enemy.setSpeed(true);
                    }else if (abs(_bomber.getXTile() - _enemy.getXTile()) >= 4 && abs(_bomber.getYTile() - _enemy.getYTile()) >= 4 && _enemy.getSpeed() > Game.getBomberSpeed()){
                        _enemy.setSpeed(false);
                        //flagSpeed = true;
                    }return 1;
                }
		else if(_bomber.getXTile() > _enemy.getXTile() && abs((int)_bombs.get(_bombs.size() - 1).getXTile() - _enemy.getXTile()) > 8){
                    if(abs(_bomber.getXTile() - _enemy.getXTile()) < 4 && abs(_bomber.getYTile() - _enemy.getYTile()) < 4 && _enemy.getSpeed() < Game.getBomberSpeed()*1.5){ 
                        _enemy.setSpeed(true);
                    }else if (abs(_bomber.getXTile() - _enemy.getXTile()) >= 4 && abs(_bomber.getYTile() - _enemy.getYTile()) >= 4 && _enemy.getSpeed() > Game.getBomberSpeed()){
                        _enemy.setSpeed(false);
                        //flagSpeed = true;
                    }return 1;
                }
                else if(_bomber.getXTile() > _enemy.getXTile() && abs((int)_bombs.get(_bombs.size() - 1).getXTile()- _enemy.getXTile()) <= 8){
                    if(abs(_bomber.getXTile() - _enemy.getXTile()) < 4 && abs(_bomber.getYTile() - _enemy.getYTile()) < 4 && _enemy.getSpeed() < Game.getBomberSpeed()*1.5){ 
                        _enemy.setSpeed(true);
                    }else if (abs(_bomber.getXTile() - _enemy.getXTile()) >= 4 && abs(_bomber.getYTile() - _enemy.getYTile()) >= 4 && _enemy.getSpeed() > Game.getBomberSpeed()){
                        _enemy.setSpeed(false);
                        //flagSpeed = true;
                    }return 3;
                }
            }
		return -1;
	}
	
	protected int calculateRowDirection() {
            if(_bombs.isEmpty()){
                if(_bomber.getYTile() < _enemy.getYTile())// && abs(_bomb.getY() - _enemy.getYTile()) > 4)
			return 0;//Xuong
                else if(_bomber.getYTile() > _enemy.getYTile())// && abs(_bomb.getY() - _enemy.getYTile()) <= 4)
			return 2;// len
            }else{
                if(_bomber.getYTile() < _enemy.getYTile() && abs((int)_bombs.get(_bombs.size() - 1).getYTile() - _enemy.getYTile()) > 8){
                    if(abs(_bomber.getXTile() - _enemy.getXTile()) < 4 && abs(_bomber.getYTile() - _enemy.getYTile()) < 4 && _enemy.getSpeed() < Game.getBomberSpeed()*1.5){ 
                        _enemy.setSpeed(true);
                    }else if (abs(_bomber.getXTile() - _enemy.getXTile()) >= 4 && abs(_bomber.getYTile() - _enemy.getYTile()) >= 4 && _enemy.getSpeed() > Game.getBomberSpeed()){
                        _enemy.setSpeed(false);
                        //flagSpeed = true;
                    }return 0;
                }
                else if(_bomber.getYTile() < _enemy.getYTile() && abs((int)_bombs.get(_bombs.size() - 1).getYTile() - _enemy.getYTile()) <= 8){
                    if(abs(_bomber.getXTile() - _enemy.getXTile()) < 4 && abs(_bomber.getYTile() - _enemy.getYTile()) < 4 && _enemy.getSpeed() < Game.getBomberSpeed()*1.5){ 
                        _enemy.setSpeed(true);
                    }else if (abs(_bomber.getXTile() - _enemy.getXTile()) >= 4 && abs(_bomber.getYTile() - _enemy.getYTile()) >= 4 && _enemy.getSpeed() > Game.getBomberSpeed()){
                        _enemy.setSpeed(false);
                        //flagSpeed = true;
                    }
                    return 2;
                }
		else if(_bomber.getYTile() > _enemy.getYTile() && abs((int)_bombs.get(_bombs.size() - 1).getYTile() - _enemy.getYTile()) > 8){
                    if(abs(_bomber.getXTile() - _enemy.getXTile()) < 4 && abs(_bomber.getYTile() - _enemy.getYTile()) < 4 && _enemy.getSpeed() < Game.getBomberSpeed()*1.5){ 
                        _enemy.setSpeed(true);
                    }else if (abs(_bomber.getXTile() - _enemy.getXTile()) >= 4 && abs(_bomber.getYTile() - _enemy.getYTile()) >= 4 && _enemy.getSpeed() > Game.getBomberSpeed()){
                        _enemy.setSpeed(false);
                        //flagSpeed = true;
                    }
                    return 2;
                }
                else if(_bomber.getYTile() > _enemy.getYTile() && abs((int)_bombs.get(_bombs.size() - 1).getYTile() - _enemy.getYTile()) <= 8){
                    if(abs(_bomber.getXTile() - _enemy.getXTile()) < 4 && abs(_bomber.getYTile() - _enemy.getYTile()) < 4 && _enemy.getSpeed() < Game.getBomberSpeed()*1.5){ 
                        _enemy.setSpeed(true);
                    }else if (abs(_bomber.getXTile() - _enemy.getXTile()) >= 4 && abs(_bomber.getYTile() - _enemy.getYTile()) >= 4 && _enemy.getSpeed() > Game.getBomberSpeed()){
                        _enemy.setSpeed(false);
                        //flagSpeed = true;
                    }return 0;
                }
            }	
		return -1;
	}
}