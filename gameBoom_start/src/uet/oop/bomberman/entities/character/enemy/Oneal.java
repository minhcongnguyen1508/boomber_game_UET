package uet.oop.bomberman.entities.character.enemy;


import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.character.enemy.ai.AIDeep;
import uet.oop.bomberman.entities.character.enemy.ai.AILow;
import uet.oop.bomberman.entities.character.enemy.ai.AIMedium;
import uet.oop.bomberman.entities.character.enemy.ai.AISmart;
import uet.oop.bomberman.entities.character.enemy.ai.AIhight;
import uet.oop.bomberman.graphics.Sprite;

public class Oneal extends Enemy {
	
	public Oneal(int x, int y, Board board) {
		super(x, y, board, Sprite.oneal_dead, Game.getBomberSpeed()/2, 200);
		_sprite = Sprite.oneal_left1;
		_ai[2] = new AILow();
                _ai[4] = new AIhight(_board.getBomber(), this);
                _ai[1] = new AIDeep(_board.getBomber(), this);
                _ai[3] = new AIMedium(_board.getBomber(), this, _board, true);
                _ai[0] = new AIDeep(_board.getBomber(), this);
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
				if(_moving)
					_sprite = Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3, _animate, 60);
				else
					_sprite = Sprite.oneal_left1;
				break;
			case 2:
			case 3:
				if(_moving)
					_sprite = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, _animate, 60);
				else
					_sprite = Sprite.oneal_left1;
				break;
		}
	}
}
