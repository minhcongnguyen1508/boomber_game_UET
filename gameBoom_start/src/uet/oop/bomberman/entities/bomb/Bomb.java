package uet.oop.bomberman.entities.bomb;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.AnimatedEntitiy;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.Bomber2;
import uet.oop.bomberman.entities.character.Character;
import uet.oop.bomberman.entities.character.enemy.Kondoria;
import uet.oop.bomberman.entities.tile.Grass;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.level.Coordinates;

public class Bomb extends AnimatedEntitiy {

	protected double _timeToExplode = 120; //2 seconds
	public int _timeAfter = 20;
	
	protected Board _board;
	protected Flame[] _flames;
	protected boolean _exploded = false;
	protected boolean _allowedToPassThru = true;
	protected double _speed = 2.0;
	public Bomb(int x, int y, Board board) {
		_x = x;
		_y = y;
		_board = board;
		_sprite = Sprite.bomb;
	}
        
	
	@Override
	public void update() {
		if(_timeToExplode > 0) 
			_timeToExplode--;
		else {
		    if(!_exploded) {
                        explode();
                    }
		    else
		        updateFlames();
			
		    if(_timeAfter > 0) 
                       _timeAfter--;
		    else
                    {
                        //Game.sndEffect.playSound("Explosion");
                        remove();
                    }
		}		
		animate();
	}
	
	@Override
	public void render(Screen screen) {
		if(_exploded) {
			_sprite =  Sprite.bomb_exploded2;
			renderFlames(screen);
		}
                else
		    _sprite = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, _animate, 60);
		
                    int xt = (int)_x << 4;
                    int yt = (int)_y << 4;
		
                    screen.renderEntity(xt, yt , this);
	}
	
	public void renderFlames(Screen screen) {
		for (int i = 0; i < _flames.length; i++) {
			_flames[i].render(screen);
		}
	}
	
	public void updateFlames() {
		for (int i = 0; i < _flames.length; i++) {
			_flames[i].update();
		}
	}

    /**
     * Xử lý Bomb nổ
     * Định nghĩa hàm
     */
	protected void explode() {
	    _exploded = true;
	    // TODO: xử lý khi Character đứng tại vị trí Bomb
            _exploded = true;
            // TODO: xử lý khi Character đứng tại vị trí Bomb
            _allowedToPassThru = true;
            Character a = _board.getCharcterAt(_x, _y);
            if (a != null) {
                a.kill();
            }
	    // TODO: tạo các Flame
            _flames = new Flame[4];
		// TODO: tạo các Flame
            _flames = new Flame[4];
            for (int i = 0; i < _flames.length; i++) {
                _flames[i] = new Flame((int)_x, (int)_y, i, Game.getBombRadius(), _board);
            }
	}
	/**
         * 
         * @param x
         * @param y
         * @return 
         */
	public FlameSegment flameAt(int x, int y) {
		if(!_exploded) return null;
		
		for (int i = 0; i < _flames.length; i++) {
			if(_flames[i] == null) return null;
			FlameSegment e = _flames[i].flameSegmentAt(x, y);
			if(e != null) return e;
		}
		
		return null;
	}
        
	@Override
	public boolean collide(Entity e) {
        // TODO: xử lý khi Bomber đi ra sau khi vừa đặt bom (_allowedToPassThru)
        // TODO: xử lý va chạm với Flame của Bomb khác
            if(e instanceof Bomber || e instanceof Bomber2) {
                double diffX = e.getX() - Coordinates.tileToPixel(getX());
                double diffY = e.getY() - Coordinates.tileToPixel(getY());
                if(!(diffX >= -11 && diffX < 16 && diffY >= 1 && diffY <= 28)) { // sau khi ra khỏi bom vừa đặt thì không thể đi qua bom
                    _allowedToPassThru = false;
                    if(Game.getBomberKill() > 0){
                        placeNewPositionBomb(((Bomber) e).getDirection(), Game.getBomberKill(), _x, _y);
                    }
                }
                return _allowedToPassThru;
            }
            if(e instanceof Flame) {
                _timeToExplode = 0;
                return true;
            }
            return false;
	}
//        public void bombKick(Entity e){
//            if(e instanceof Bomber) {
//                if(!collide(e)){
//                    if(Game.getBomberKill() > 0){
//                        placeNewPositionBomb(((Bomber) e).getDirection(), Game.getBomberKill(), _x, _y);
//                    }
//                }
//            }
//        }
        public boolean canMove(double x, double y) {
            // TODO: kiểm tra có đối tượng tại vị trí chuẩn bị di chuyển đến và có thể di chuyển tới đó hay không
            Entity a = _board.getEntityGrass(x, y);
            //System.out.println(x + " ---- " + y);
            if(!a.collide(this))
                return false;
            return true;
        }
        public int placeNewPositionBomb(int _direction, int i, double xa_, double ya_){
            double xa = _x, ya = _y;
            if(i <= 0){
                move(xa_, ya_);
                return 0;
            }
            if(_direction == 0) ya -= (Game.getBomberKill() - i + 1);
            else if(_direction == 2) ya += (Game.getBomberKill() - i + 1);
            else if(_direction == 3) xa -= (Game.getBomberKill() - i + 1);
            else if(_direction == 1) xa += (Game.getBomberKill() - i + 1);
            //System.out.println(Game.getBomberKill() - i + 1);
            if(canMove(xa, ya)){
                placeNewPositionBomb(_direction, i-1, xa, ya);
            }
            else{
                move(xa_, ya_);
                return 0;
                //placeNewPositionBomb(_direction, i-1, xa, ya);
            }
            return 0;
        }
        public void move(double xa, double ya) {
            _x = xa;
            _y = ya;

        }
}	
