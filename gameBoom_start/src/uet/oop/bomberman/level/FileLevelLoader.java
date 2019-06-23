package uet.oop.bomberman.level;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Balloon;
import uet.oop.bomberman.entities.character.enemy.Doll;
import uet.oop.bomberman.entities.character.enemy.Kondoria;
import uet.oop.bomberman.entities.character.enemy.Minvo;
import uet.oop.bomberman.entities.character.enemy.Oneal;
import uet.oop.bomberman.entities.tile.Grass;
import uet.oop.bomberman.entities.tile.Portal;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.entities.tile.destroyable.Brick;
import uet.oop.bomberman.entities.tile.item.BombItem;
import uet.oop.bomberman.entities.tile.item.FlameItem;
import uet.oop.bomberman.entities.tile.item.SpeedItem;
import uet.oop.bomberman.exceptions.LoadLevelException;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;

import java.io.File;
import java.net.URL;
import java.util.Scanner;
import uet.oop.bomberman.entities.character.Bomber2;
import uet.oop.bomberman.entities.tile.item.BomberItem;

public class FileLevelLoader extends LevelLoader {

	/**
	 * Ma trận chứa thông tin bản đồ, mỗi phần tử lưu giá trị kí tự đọc được
	 * từ ma trận bản đồ trong tệp cấu hình
	 */
	public static char[][] _map;
	
	public FileLevelLoader(Board board, int level) throws LoadLevelException {
		super(board, level);
	}
	
	@Override
	public void loadLevel(int level) throws LoadLevelException {
		// TODO: đọc dữ liệu từ tệp cấu hình /levels/Level{level}.txt
		// TODO: cập nhật các giá trị đọc được vào _width, _height, _level, _map

        String _path = "/levels/Level" + Integer.toString(level) + ".txt";

        try {
            URL url = FileLevelLoader.class.getResource(_path);
            String _url = url.getPath();
            File mapData = new File(_url);
            Scanner sc = new Scanner(mapData);

            _level = sc.nextInt();  // đọc giá trị level
            _height = sc.nextInt(); // đọc số hàng
            _width = sc.nextInt();  // đọc số cột
            sc.nextLine();

            _map = new char[_height][_width];

            for (int i = 0; i < _height; i++) {
                String row = sc.nextLine();
                for (int j = 0; j < _width; j++) {
                    _map[i][j] = row.charAt(j);
                }
            }
        } catch (Exception e) {
            throw new LoadLevelException("Fail to load level" + _path, e);
        }
    }
    
    @Override
    public void createEntities() {
		// TODO: tạo các Entity của màn chơi
		// TODO: sau khi tạo xong, gọi _board.addEntity() để thêm Entity vào game

		// TODO: phần code mẫu ở dưới để hướng dẫn cách thêm các loại Entity vào game
		// TODO: hãy xóa nó khi hoàn thành chức năng load màn chơi từ tệp cấu hình

        for (int x = 0; x < _width; x++) {
            for (int y = 0; y < _height; y++) {
                switch (_map[y][x]) {
                    case '#':
                        _board.addEntity(x + y * _width, new Wall(x, y, Sprite.wall));
                        break;
                    case 'p':
                        _board.addCharacter( new Bomber(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board) );
                        Screen.setOffset(0, 0);
                        _board.addEntity(x + y * _width, new Grass(x, y, Sprite.grass));
                        break;
                    case 'w':
                        _board.addCharacter( new Bomber2(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board) );
                        Screen.setOffset(0, 0);
                        _board.addEntity(x + y * _width, new Grass(x, y, Sprite.grass));
                        break;
                    case '*':
                        _board.addEntity(x + y * _width,
                                new LayeredEntity(x, y,
                                        new Grass(x, y, Sprite.grass),
                                        new Brick(x, y, Sprite.brick)
                                )
                        );
                        break;
                    case 'b':
                        _board.addEntity(x + y * _width,
                                new LayeredEntity(x, y,
                                        new Grass(x ,y, Sprite.grass),
                                        new BombItem(x, y, Sprite.powerup_bombs),
                                        new Brick(x, y, Sprite.brick)
                                )
                        );
                        break;
                    case 'm':
                        _board.addEntity(x + y * _width,
                                new LayeredEntity(x, y,
                                        new Grass(x ,y, Sprite.grass),
                                        new BomberItem(x, y, Sprite.powerup_wallpass),
                                        new Brick(x, y, Sprite.brick)
                                )
                        );
                        break;
                    case 'f':
                        _board.addEntity(x + y * _width,
                                new LayeredEntity(x, y,
                                        new Grass(x ,y, Sprite.grass),
                                        new FlameItem(x, y, Sprite.powerup_flames),
                                        new Brick(x, y, Sprite.brick)
                                )
                        );
                        break;
                    case 's':
                        _board.addEntity(x + y * _width,
                                new LayeredEntity(x, y,
                                        new Grass(x ,y, Sprite.grass),
                                        new SpeedItem(x, y, Sprite.powerup_speed),
                                        new Brick(x, y, Sprite.brick)
                                )
                        );
                        break;
                    case 'x':
                        _board.addEntity(x + y * _width,
                                new LayeredEntity(x, y,
                                        new Grass(x ,y, Sprite.grass),
                                        new Portal(x, y, _board, Sprite.portal),
                                        new Brick(x, y, Sprite.brick)
                                )
                        );
                        break;
                    case '1':
                        _board.addCharacter( new Balloon(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board));
                        _board.addEntity(x + y * _width, new Grass(x, y, Sprite.grass));
                        break;
                    case '2':
                        _board.addCharacter( new Oneal(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board));
                        _board.addEntity(x + y * _width, new Grass(x, y, Sprite.grass));
                        break;
                    case '3':
                        _board.addCharacter( new Doll(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board));
                        _board.addEntity(x + y * _width, new Grass(x, y, Sprite.grass));
                        break;
                    case '4':
                        _board.addCharacter( new Minvo(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board));
                        _board.addEntity(x + y * _width, new Grass(x, y, Sprite.grass));
                        break;
                    case '5':
                        _board.addCharacter( new Kondoria(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board));
                        _board.addEntity(x + y * _width, new Grass(x, y, Sprite.grass));
                        break;
                    default:
                        _board.addEntity(x + y * _width, new Grass(x, y, Sprite.grass));
                        break;
                }
            }
        }  
    }
    public static char[][] getMap() {
        return _map;
    }
}
