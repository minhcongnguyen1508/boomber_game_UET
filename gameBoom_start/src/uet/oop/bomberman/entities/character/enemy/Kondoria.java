package uet.oop.bomberman.entities.character.enemy;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.character.enemy.ai.AIDeep;
import uet.oop.bomberman.entities.character.enemy.ai.AILow;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.entities.character.enemy.ai.AIMedium;
import uet.oop.bomberman.entities.character.enemy.ai.AISmart;
import uet.oop.bomberman.entities.character.enemy.ai.AIhight;

public class Kondoria extends Enemy {
	
	
	public Kondoria(int x, int y, Board board) {
		super(x, y, board, Sprite.kondoria_dead, Game.getBomberSpeed() / 4, 1000);
		
		_sprite = Sprite.kondoria_right1;
                _ai[0] = new AIhight(_board.getBomber(), this);
                _ai[3] = new AIDeep(_board.getBomber(), this);
                _ai[4] = new AISmart(_board.getBomber(), this, _board.getBombs());
                _ai[1] = new AIMedium(_board.getBomber(), this, _board, true);
                _ai[2] = new AIDeep(_board.getBomber(), this);
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
					_sprite = Sprite.movingSprite(Sprite.kondoria_right1, Sprite.kondoria_right2, Sprite.kondoria_right3, _animate, 60);
				else
					_sprite = Sprite.kondoria_left1;
				break;
			case 2:
			case 3:
				if(_moving)
					_sprite = Sprite.movingSprite(Sprite.kondoria_left1, Sprite.kondoria_left2, Sprite.kondoria_left3, _animate, 60);
				else
					_sprite = Sprite.kondoria_left1;
				break;
		}
	}
}
