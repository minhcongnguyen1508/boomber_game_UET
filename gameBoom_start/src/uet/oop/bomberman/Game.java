package uet.oop.bomberman;

import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.gui.Frame;
import uet.oop.bomberman.input.Keyboard;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import uet.oop.bomberman.sound.BackgroundMusic;
import uet.oop.bomberman.sound.BomberSoundEffect;


/**
 * Tạo vòng lặp cho game, lưu trữ một vài tham số cấu hình toàn cục,
 * Gọi phương thức render(), update() cho tất cả các entity
 */
public class Game extends Canvas {

	public static final int TILES_SIZE = 16,
			WIDTH = TILES_SIZE * (31 / 2),
			HEIGHT = 13 * TILES_SIZE;

	public static int SCALE = 3;
	
	public static final String TITLE = "NEWBIE-Team-BombermanGame";
	
	private static final int BOMBRATE = 2;
	private static final int BOMBRADIUS = 1;
        private static final int BOMBERKILL = 0;
	private static final double BOMBERSPEED = 1;
	public static final int TIME = 200;
	public static final int POINTS = 0;
	public static final int LIVES = 3;
        
         /** relative path */
        public static final String RP = "./";
        /** flag: whether current machine's java runtime is version 2 or not */
        public static boolean J2 = false;

        static {
        /** get java runtime version */
            String version = System.getProperty("java.version");
        /** parse it */
            int major = Integer.parseInt(version.substring(0, 1));
            int minor = Integer.parseInt(version.substring(2, 3));
        /** if major is greater than or equal to 1 and */
        /** if minor is greater than or equal to 3 */
        /** then it's Java 2 */
            if (major >= 1 && minor >= 2)
            J2 = true;
        }
        
	protected static int SCREENDELAY = 3;

        public static BomberSoundEffect sndEffect = new BomberSoundEffect();
	protected static int bombRate = BOMBRATE;
	protected static int bombRadius = BOMBRADIUS;
        protected static int bomberKill = BOMBERKILL;
	protected static double bomberSpeed = BOMBERSPEED;
        protected static int bombRate_ = BOMBRATE;
	protected static int bombRadius_ = BOMBRADIUS;
        protected static int bomberKill_ = BOMBERKILL;
	protected static double bomberSpeed_ = BOMBERSPEED;
        
        public static double getBomberSpeed() {
            return bomberSpeed;
        }
        public static void resetBomberSpeed() {
            bomberSpeed = BOMBERSPEED;
        }
        
        public static int getBombRate() {
            return bombRate;
        }
        public static int getBombRate1() {
            return bombRate_;
        }
        public static void resetBombRate() {
            bombRate = BOMBRATE;
        }
        public static void resetBombRate1() {
            bombRate_ = BOMBRATE;
        }
        public static int getBombRadius() {
            return bombRadius;
        }
        public static void resetBombRadius() {
            bombRadius = BOMBRADIUS;
        }
        public static int getBomberKill() {
            return bomberKill;
        }
        public static void resetBomberKill() {
            bomberKill = BOMBERKILL;
        }
        public static void addBomberSpeed(double i) {
            bomberSpeed += i;
        }
        public static void addBombRadius(int i) {
            bombRadius += i;
        }
        public static void addBombRate(int i) {
            bombRate += i;
        }
        public static void addBombRate1(int i) {
            bombRate_ += i;
        }
	public static void addBomberKill(int i) {
            bomberKill += i;
        }
	
	protected int _screenDelay = SCREENDELAY;
	
	private Keyboard _input;
	private boolean _running = false;
	private boolean _paused = true;
	
	private Board _board;
	private Screen screen;
	private Frame _frame;
	
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
	public Game(Frame frame) {
		_frame = frame;
		_frame.setTitle(TITLE);
		
		screen = new Screen(WIDTH, HEIGHT);
		_input = new Keyboard();
		
		_board = new Board(this, _input, screen);
		addKeyListener(_input);
	}
	private void renderGame() { //render will run the maximum times it can per second
		BufferStrategy bs = getBufferStrategy(); //create a buffer to store images using canvas
		if(bs == null) { //if canvas dont have a bufferstrategy, create it
                    createBufferStrategy(3); //triple buffer
                    return;
		}
		
		screen.clear();
		
		_board.render(screen);
		
		for (int i = 0; i < pixels.length; i++) { //create the image to be rendered
			pixels[i] = screen._pixels[i];
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		_board.renderMessages(g);
		
		g.dispose(); //release resources
		bs.show(); //make next buffer visible
	}
	
	private void renderScreen() { //TODO: merge these render methods
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		screen.clear();
		
		Graphics g = bs.getDrawGraphics();
		
		_board.drawScreen(g);

		g.dispose();
		bs.show();
	}

	private void update() {
		_input.update();
                _input.update_();
		_board.update();
	}
	/**
         * Chua vong lap cho game
         */
	public void start() {
		_running = true;
		
		long  lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0; //nanosecond, 60 frames per second
		double delta = 0;
		int frames = 0;
		int updates = 0;
		requestFocus();
		while(_running) {
                    /** if Java 2 available */
//                        if (Game.J2) {
//                        /** player music */
//                            BackgroundMusic.change("Battle");
//                        }
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1) {
				update();
				updates++;
				delta--;
			}
			
			if(_paused) {
				if(_screenDelay <= 0) {
					_board.setShow(-1);
					_paused = false;
				}
					
				renderScreen();
			} else {
				renderGame();// Game start
			}
				
			
			frames++;
			if(System.currentTimeMillis() - timer > 1000) {
				_frame.setTime(_board.subtractTime());
				_frame.setPoints(_board.getPoints());
                                _frame.setLives(_board.getLives());
				timer += 1000;
				_frame.setTitle(TITLE + " | " + updates + " rate, " + frames + " fps");
				updates = 0;
				frames = 0;
				
				if(_board.getShow() == 2)
					--_screenDelay;
			}
		}
	}
	

	//screen delay
	public int getScreenDelay() {
		return _screenDelay;
	}
	
	public void decreaseScreenDelay() {
		_screenDelay--;
	}
	
	public void resetScreenDelay() {
		_screenDelay = SCREENDELAY;
	}

	public Keyboard getInput() {
		return _input;
	}
	
	public Board getBoard() {
		return _board;
	}
	
	public void run() {
		_running = true;
		_paused = false;
	}
	
	public void stop() {
		_running = false;
	}
	
	public boolean isRunning() {
		return _running;
	}
	
	public boolean isPaused() {
		return _paused;
	}
	
	public void pause() {
		_paused = true;
	}
}
