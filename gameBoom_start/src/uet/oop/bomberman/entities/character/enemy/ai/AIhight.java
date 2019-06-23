package uet.oop.bomberman.entities.character.enemy.ai;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Enemy;
import uet.oop.bomberman.entities.tile.Portal;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.level.FileLevelLoader;

public class AIhight extends AI {
	Bomber _bomber;
	Enemy _e;
        private int x = -1, y = -1;
	public AIhight(Bomber bomber, Enemy e) {
                //if(bomb == null ) System.out.println("NUll bomb");
		_bomber = bomber;
		_e = e;
	}
        public int portalX(){
            for(int i = 0; i < 13; i++){
                for(int j = 0; j < 31; j++){
                    if(FileLevelLoader._map[i][j] == 'x'){
                        //System.out.println(j);
                        x = j;
                        break;
                    }
                }
            }
            //System.out.println(x);
            return x;
        }
        public int portalY(){
            for(int i = 0; i < 13; i++){
                for(int j = 0; j < 31; j++){
                    if(FileLevelLoader._map[i][j] == 'x'){
                        //System.out.println(i);
                        y = i;
                        break;
                    }
                }
            }
            //System.out.println(y);
            return y;
        }
	@Override
	public int calculateDirection() {
		// TODO: cài đặt thuật toán tìm đường đi
	
		int vertical = random.nextInt(2);
		
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
            
		if(portalX() < _e.getXTile()){
                   // System.out.println(portalX() + "X<" + _e.getXTile());
                    if(abs(portalX() - _e.getXTile()) <= 4 && abs(portalY() - _e.getY()) <= 4){
                       // System.out.println("----random----");
                        return random.nextInt(4);
                    }else{
                       // System.out.println("----left----");
                        return 3;
                    }
                }
			
		else if(portalX() > _e.getXTile()){
                   // System.out.println(portalX() + "X>" + _e.getXTile());
                    if(abs(portalX() - _e.getXTile()) <= 4 && abs(portalY() - _e.getY()) <= 4){
                        //System.out.println("----random----");
                        return random.nextInt(4);
                    }else{
                        //System.out.println("----right----");
                        return 1;
                    }
                }
		return random.nextInt(4);
	}
	
	protected int calculateRowDirection() {
		if(portalY() < _e.getYTile()){
                    //System.out.println(portalY() + "Y<" + _e.getYTile());
                    if(abs(portalX() - _e.getXTile()) <= 4 && abs(portalY() - _e.getY()) <= 4){
                        //System.out.println("----random----");
                        return random.nextInt(4);
                    }else{
                        //System.out.println("----up----");
                        return 0;
                    }
                }
			
		else if(portalY() > _e.getYTile()){
                    //System.out.println(portalY() + "Y>" + _e.getYTile());
                    if(abs(portalX() - _e.getXTile()) <= 4 && abs(portalY() - _e.getY()) <= 4){
                        //System.out.println("----random----");
                        return random.nextInt(4);
                    }else{
                        //System.out.println("----down----");
                        return 2;
                    }
                }
                
		return random.nextInt(4);
	}

}
