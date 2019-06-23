package uet.oop.bomberman.entities;

import uet.oop.bomberman.entities.tile.destroyable.DestroyableTile;
import uet.oop.bomberman.graphics.Screen;

import java.util.LinkedList;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.character.enemy.Minvo;
import uet.oop.bomberman.entities.tile.Grass;
import uet.oop.bomberman.entities.tile.Portal;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.level.Coordinates;

/**
 * Chứa và quản lý nhiều Entity tại cùng một vị trí
 * Ví dụ: tại vị trí dấu Item, có 3 Entity [Grass, Item, Brick]
 */
public class LayeredEntity extends Entity {
	
	protected LinkedList<Entity> _entities = new LinkedList<>();
        
	public LayeredEntity(int x, int y, Entity ... entities) {
		_x = x;
		_y = y;
		
		for (int i = 0; i < entities.length; i++) {
			_entities.add(entities[i]); 
			
			if(i > 1) {
				if(entities[i] instanceof DestroyableTile)
					((DestroyableTile)entities[i]).addBelowSprite(entities[i-1].getSprite());
			}
		}
	}
	public LinkedList<Entity> getLayeredEntity() {
            return _entities;
        }
	@Override
	public void update() {
		clearRemoved();
		getTopEntity().update();
	}
	
	@Override
	public void render(Screen screen) {
		getTopEntity().render(screen);
	}
	
	public Entity getTopEntity() {
		return _entities.getLast();
	}
	
	private void clearRemoved() {
		Entity top  = getTopEntity();
		
		if(top.isRemoved())  {
			_entities.removeLast();
		}
	}
	
	public void addBeforeTop(Entity e) {
		_entities.add(_entities.size() - 1, e);
	}
	
	@Override
	public boolean collide(Entity e) {
		// TODO: lấy entity trên cùng ra để xử lý va chạm
		return getTopEntity().collide(e);
	}

}
