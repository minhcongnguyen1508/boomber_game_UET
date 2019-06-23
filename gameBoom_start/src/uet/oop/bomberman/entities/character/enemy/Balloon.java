package uet.oop.bomberman.entities.character.enemy;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.character.enemy.ai.AIDeep;
import uet.oop.bomberman.entities.character.enemy.ai.AILow;
import uet.oop.bomberman.entities.character.enemy.ai.AIMedium;
import uet.oop.bomberman.entities.character.enemy.ai.AISmart;
import uet.oop.bomberman.entities.character.enemy.ai.AIhight;
import uet.oop.bomberman.graphics.Sprite;

public class Balloon extends Enemy {
	
	
	public Balloon(int x, int y, Board board) {
		super(x, y, board, Sprite.balloom_dead, Game.getBomberSpeed() / 2, 100);
		
		_sprite = Sprite.balloom_left1;
		_ai[0] = new AILow();
                _ai[1] = new AIhight(_board.getBomber(), this);
                _ai[3] = new AIDeep(_board.getBomber(), this);
                _ai[2] = new AIMedium(_board.getBomber(), this, _board, true);
                _ai[4] = new AIDeep(_board.getBomber(), this);
		//_direction = _ai.calculateDirection();
	}
        /**
         * Load hinh anh khi enemy di chuyen
         */
	@Override
	protected void chooseSprite() {
		switch(_direction) {
			case 0:
			case 1:
					_sprite = Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3, _animate, 60);
				break;
			case 2:
			case 3:
					_sprite = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3, _animate, 60);
				break;
		}
	}
}
