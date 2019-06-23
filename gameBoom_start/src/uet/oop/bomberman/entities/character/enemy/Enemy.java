package uet.oop.bomberman.entities.character.enemy;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Message;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.Character;
import uet.oop.bomberman.entities.character.enemy.ai.AI;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.level.Coordinates;
import java.awt.*;
import java.util.Random;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.character.Bomber2;

public abstract class Enemy extends Character {

	protected int _points;
	
	protected double _speed;
        protected final double _SPEEDS = _speed;
	protected AI[] _ai = new AI[5];
	protected final double MAX_STEPS;
	protected final double rest;
	protected double _steps;
	
	protected int _finalAnimation = 30;
	protected Sprite _deadSprite;
	
	public Enemy(int x, int y, Board board, Sprite dead, double speed, int points) {
		super(x, y, board);
		
		_points = points;
		_speed = speed;
		
		MAX_STEPS = Game.TILES_SIZE / _speed;
		rest = (MAX_STEPS - (int) MAX_STEPS) / MAX_STEPS;
		_steps = MAX_STEPS;
		
		_timeAfter = 20;
		_deadSprite = dead;
	}
	
	@Override
	public void update() {
		animate();
		
		if(!_alive) {
			afterKill();
			return;
		}
		
		if(_alive)
			calculateMove();
	}
	
	@Override
	public void render(Screen screen) {
		
		if(_alive)
			chooseSprite();
		else {
			if(_timeAfter > 0) {
				_sprite = _deadSprite;
				_animate = 0;
			} else {
				_sprite = Sprite.movingSprite(Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3, _animate, 60);
			}
				
		}
			
		screen.renderEntity((int)_x, (int)_y - _sprite.SIZE, this);
	}
	
	@Override
	public void calculateMove() {
		// TODO: nhớ cập nhật lại giá trị cờ _moving khi thay đổi trạng thái di chuyển
            int xa = 0, ya = 0;
            int i;
            //Random random = new Random();
            if(_steps <= 0){
                if(_board.getTime() <= 200 && _board.getTime() >= 150){
                    _direction = (int)_ai[0].calculateDirection();
                }else if(_board.getTime() < 150 && _board.getTime() >= 110){
                    _direction = (int)_ai[1].calculateDirection();
                }else if(_board.getTime() < 110 && _board.getTime() >= 80){
                    _direction = (int)_ai[2].calculateDirection();
                }else if(_board.getTime() < 80 && _board.getTime() >= 40){
                    _direction = (int)_ai[3].calculateDirection();
                }else if(_board.getTime() < 40 && _board.getTime() > 1){
                    _direction = (int)_ai[4].calculateDirection();
                }
                _steps = MAX_STEPS;
            }
            // TODO: Tính toán hướng đi và di chuyển Enemy theo _ai và cập nhật giá trị cho _direction
            if(_direction == 0) ya--;
            if(_direction == 2) ya++;
            if(_direction == 3) xa--;
            if(_direction == 1) xa++;
            // TODO: sử dụng canMove() để kiểm tra xem có thể di chuyển tới điểm đã tính toán hay không
            if(canMove(xa, ya)) {
                _steps -= 1 + rest;// De thoi gian cho enemy tinh huong  va di chuyen
                // TODO: sử dụng move() để di chuyển
                move(xa * _speed, ya * _speed);
                _moving = true;
            } else {
                _steps = 0;
                _moving = false;
            }
	}
	
	@Override
	public void move(double xa, double ya) {
		if(!_alive) return;
		_y += ya;
		_x += xa;
	}
	
	@Override
	public boolean canMove(double x, double y) {
            // TODO: kiểm tra có đối tượng tại vị trí chuẩn bị di chuyển đến và có thể di chuyển tới đó hay không

        double xr = _x, yr = _y - 16; // chỉnh sửa y để có kết quả chính xác hơn

        if(_direction == 0) { yr += _sprite.getSize() -1 ; xr += _sprite.getSize()/2; }
        if(_direction == 1) {yr += _sprite.getSize()/2; xr += 1;}
        if(_direction == 2) { xr += _sprite.getSize()/2; yr += 1;}
        if(_direction == 3) { xr += _sprite.getSize() -1; yr += _sprite.getSize()/2;}

        int xx = Coordinates.pixelToTile(xr) +(int)x;
        int yy = Coordinates.pixelToTile(yr) +(int)y;

        Entity a = _board.getEntity(xx, yy, this);

        return a.collide(this);
	}

	@Override
	public boolean collide(Entity e) {
		// TODO: xử lý va chạm với Flame
		// TODO: xử lý va chạm với Bomber
            
            if(e instanceof Flame) {
                kill(); //Neu va cham voi flame se chet
                return false;
            }

            if(e instanceof Bomber) {
                ((Bomber) e).kill(); // Neu va cham voi bomber thi bomber se chet
                return false;
            }
            if(e instanceof Bomber2) {
                ((Bomber2) e).kill(); // Neu va cham voi bomber thi bomber se chet
                return false;
            }
        return true;
	}
	
	@Override
	public void kill() {
		if(!_alive) return;
		_alive = false;
		
		_board.addPoints(_points);

		Message msg = new Message("+" + _points, getXMessage(), getYMessage(), 2, Color.white, 14);
		_board.addMessage(msg);
	}
	
	
	@Override
	protected void afterKill() {
		if(_timeAfter > 0) --_timeAfter;
		else {
			if(_finalAnimation > 0) --_finalAnimation;
			else
				remove();
		}
	}
	
	protected abstract void chooseSprite();

        public double getSpeed() {
            return _speed; //To change body of generated methods, choose Tools | Templates.
        }
        public void setSpeed(boolean flag){
            if(flag)
                _speed = Game.getBomberSpeed()*2;
            else
                _speed = Game.getBomberSpeed() / 2;
        }
}
