package uet.oop.bomberman.entities.character.enemy;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.character.enemy.ai.AIDeep;
import uet.oop.bomberman.entities.character.enemy.ai.AILow;
import uet.oop.bomberman.entities.character.enemy.ai.AIMedium;
import uet.oop.bomberman.entities.character.enemy.ai.AISmart;
import uet.oop.bomberman.entities.character.enemy.ai.AIhight;
import uet.oop.bomberman.graphics.Sprite;


public class Doll extends Enemy {
	
	
	public Doll(int x, int y, Board board) {
		super(x, y, board, Sprite.doll_dead, Game.getBomberSpeed() / 2, 400);
		
		_sprite = Sprite.doll_right1;
		_ai[4] = new AILow();
                _ai[1] = new AIDeep(_board.getBomber(), this);
                _ai[3] = new AIhight(_board.getBomber(), this);
                _ai[2] = new AIMedium(_board.getBomber(), this, _board, true);
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
					_sprite = Sprite.movingSprite(Sprite.doll_right1, Sprite.doll_right2, Sprite.doll_right3, _animate, 60);
				else
					_sprite = Sprite.doll_left1;
				break;
			case 2:
			case 3:
				if(_moving)
					_sprite = Sprite.movingSprite(Sprite.doll_left1, Sprite.doll_left2, Sprite.doll_left3, _animate, 60);
				else
					_sprite = Sprite.doll_left1;
				break;
		}
	}
}
