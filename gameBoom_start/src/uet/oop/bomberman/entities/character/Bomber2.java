package uet.oop.bomberman.entities.character;

import java.awt.Color;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.entities.character.enemy.Enemy;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.input.Keyboard;
import uet.oop.bomberman.level.Coordinates;
import uet.oop.bomberman.entities.Message;

import java.util.Iterator;
import java.util.List;
import uet.oop.bomberman.entities.character.enemy.ai.QLearningAI;

public class Bomber2 extends Character {

    private List<Bomb> _bombs;
    protected Keyboard _input;

    /**
     * nếu giá trị này < 0 thì cho phép đặt đối tượng Bomb tiếp theo,
     * cứ mỗi lần đặt 1 Bomb mới, giá trị này sẽ được reset về 0 và giảm dần trong mỗi lần update()
     */
    protected int _timeBetweenPutBombs = 0;
    private AutoPlay _autoPlay;
    protected final double MAX_STEPS;
    protected final double rest;
    protected double _steps;

    public Bomber2(int x, int y, Board board) {
        super(x, y, board);
        _bombs = _board.getBombs();
        _input = _board.getInput();
        _sprite = Sprite.player_right;
        _autoPlay = new AutoPlay(_board, this, true);
        MAX_STEPS = Game.TILES_SIZE / Game.getBomberSpeed();
        rest = (MAX_STEPS - (int) MAX_STEPS) / MAX_STEPS;
        _steps = MAX_STEPS;
    }
    public int getDirection(){
            return _direction;
    }
    @Override
    public void update() {
        clearBombs();
        if (!_alive) {
            afterKill();
            return;
        }
        if (_timeBetweenPutBombs < -7500) _timeBetweenPutBombs = 0;
        else _timeBetweenPutBombs--;

        animate();

        calculateMove();

        detectPlaceBomb();
    }

    @Override
    public void render(Screen screen) {
        //calculateXOffset();

        if (_alive)
            chooseSprite();
        else{
            _sprite = Sprite.player_dead1;
            remove();
        }
        screen.renderEntity((int) _x, (int) _y - _sprite.SIZE, this);
    }

    public void calculateXOffset() {
        int xScroll = Screen.calculateXOffset(_board, this);
        Screen.setOffset(xScroll, 0);
    }

    /**
     * Kiểm tra xem có đặt được bom hay không? nếu có thì đặt bom tại vị trí hiện tại của Bomber
     */
    private void detectPlaceBomb() {
        // TODO: kiểm tra xem phím điều khiển đặt bom có được gõ và giá trị _timeBetweenPutBombs, Game.getBombRate() có thỏa mãn hay không
        // TODO:  Game.getBombRate() sẽ trả về số lượng bom có thể đặt liên tiếp tại thời điểm hiện tại
        // TODO: _timeBetweenPutBombs dùng để ngăn chặn Bomber đặt 2 Bomb cùng tại 1 vị trí trong 1 khoảng thời gian quá ngắn
        // TODO: nếu 3 điều kiện trên thỏa mãn thì thực hiện đặt bom bằng placeBomb()
        // TODO: sau khi đặt, nhớ giảm số lượng Bomb Rate và reset _timeBetweenPutBombs về 0
        boolean place;

        if (_autoPlay.is_enable()) {
            place = _autoPlay.calculatePlaceBomb();
        } else {
            place = _input.space;
        }

        if(_input.space && _timeBetweenPutBombs < 0) {
            int xt = Coordinates.pixelToTile(_x + _sprite.getSize() / 2);
            int yt = Coordinates.pixelToTile( (_y + _sprite.getSize() / 2) - _sprite.getSize() );
            placeBom = false;
//            Game.addBombRate(-1);
            placeBomb(xt,yt);
            _timeBetweenPutBombs = 30;
        }
    }

    protected void placeBomb(int x, int y) {
        // TODO: thực hiện tạo đối tượng bom, đặt vào vị trí (x, y)
        Bomb b = new Bomb(x, y, _board);
        _board.addBomb(b);
    }

    private void clearBombs() {
    }

    @Override
    public void kill() {
        // Viet ham
//        Game.sndEffect.playSound("Die");
//        if (!_alive) return;
//        _alive = false;
//        _board.addLives(-1);
//	Message msg = new Message("-1 LIVE", getXMessage(), getYMessage(), 2, Color.white, 14);
//	_board.addMessage(msg);
    }

    @Override
    protected void afterKill() {
        //Viet ham
        if(_timeAfter > 0) --_timeAfter;
		else {
			if(_bombs.size() == 0) {
				if(_board.getLives() == 0){
//                                    _board.endGame();
                                }
                                else{
//                                    _board.restartLevel();
                                }
			}
		}
    }

    @Override
    protected void calculateMove() {
        // TODO: xử lý nhận tín hiệu điều khiển hướng đi từ _input và gọi move() để thực hiện di chuyển
        // TODO: nhớ cập nhật lại giá trị cờ _moving khi thay đổi trạng thái di chuyển
        int xa = 0, ya = 0;
        if (_autoPlay.is_enable()) {
            if(_steps <= 0){
                _direction = _autoPlay.calculateDirection();
                _steps = MAX_STEPS;
            }
            if(_direction == 0) ya--;
            if(_direction == 2) ya++;
            if(_direction == 3) xa--;
            if(_direction == 1) xa++;
        } else {
            if(_input.up_) ya--;
            if(_input.down_) ya++;
            if(_input.left_) xa--;
            if(_input.right_) xa++;
        }

        if(xa != 0 || ya != 0) {
            _steps -= 1 + rest;
            move(xa * Game.getBomberSpeed(), ya * Game.getBomberSpeed());
            _moving = true;
        } else {
            _steps = 0;
            _moving = false;
        }
    }
    
//    @Override
//    protected void calculateMove() {
//        QLearningAI q = new QLearningAI(_board); 
//        // TODO: xử lý nhận tín hiệu điều khiển hướng đi từ _input và gọi move() để thực hiện di chuyển
//        // TODO: nhớ cập nhật lại giá trị cờ _moving khi thay đổi trạng thái di chuyển
//        int xa = 0, ya = 0;
//        _direction = q.calculateDirection();
//        if(_direction == 2) ya--;
//        if(_direction == 0) ya++;
//        if(_direction == 1) xa--;
//        if(_direction == 3) xa++;
//
//        if(xa != 0 || ya != 0)  {
//            move(xa * Game.getBomberSpeed(), ya * Game.getBomberSpeed());
//            _moving = true;
//        } else {
//            _moving = false;
//        }
//    }
    @Override
    public boolean canMove(double x, double y) {
        // TODO: kiểm tra có đối tượng tại vị trí chuẩn bị di chuyển đến và có thể di chuyển tới đó hay không

        for (int i = 0; i < 4; i++) {
            double xt = ((_x + x) + i % 2 * 11) / Game.TILES_SIZE;                  // chỉnh lại toạ độ để lấy chính xác entity bên cạnh
            double yt = ((_y + y) + i / 2 * 12 - 13) / Game.TILES_SIZE;
            Entity a = _board.getEntity(xt, yt, this);
            if(!a.collide(this))
                return false;
        }
        return true;
    }

    @Override
    public void move(double xa, double ya) {
        // TODO: sử dụng canMove() để kiểm tra xem có thể di chuyển tới điểm đã tính toán hay không và thực hiện thay đổi tọa độ _x, _y
        // TODO: nhớ cập nhật giá trị _direction sau khi di chuyển

        if (_autoPlay.is_enable() == false) {
            if(xa > 0) _direction = 1;
            if(xa < 0) _direction = 3;
            if(ya > 0) _direction = 2;
            if(ya < 0) _direction = 0;
        }

        int yTemp = this.getYTile();
        int xTemp = this.getXTile();
        if (_board._map[yTemp][xTemp] == 'w') {
            _board._map[yTemp][xTemp] = ' ';
        }

        if(canMove(0, ya)) {
            _y += ya;
        }

        if(canMove(xa, 0)) {
            _x += xa;
        }

        yTemp = Coordinates.pixelToTile((_y + _sprite.getSize() / 2) - _sprite.getSize());
        xTemp = Coordinates.pixelToTile(_x + _sprite.getSize() / 2);
        if (_board._map[yTemp][xTemp] == ' ') {
            _board._map[yTemp][xTemp] = 'w';
        }
    }

    @Override
    public boolean collide(Entity e) {
        // TODO: xử lý va chạm với Flame
        // TODO: xử lý va chạm với Enemy

        if(e instanceof Flame) {
            kill();
            return false;
        }
        if(e instanceof Enemy) {
            kill();
            return true;
        }
        return true;
    }
    
    private void chooseSprite() {
        switch (_direction) {
            case 0:
                _sprite = Sprite.player1_up;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player1_up_1, Sprite.player1_up_2, _animate, 20);
                }
                break;
            case 1:
                _sprite = Sprite.player1_right;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player1_right_1, Sprite.player1_right_2, _animate, 20);
                }
                break;
            case 2:
                _sprite = Sprite.player1_down;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player1_down_1, Sprite.player1_down_2, _animate, 20);
                }
                break;
            case 3:
                _sprite = Sprite.player1_left;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player1_left_1, Sprite.player1_left_2, _animate, 20);
                }
                break;
            default:
                _sprite = Sprite.player1_right;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player1_right_1, Sprite.player1_right_2, _animate, 20);
                }
                break;
        }
    }
}
