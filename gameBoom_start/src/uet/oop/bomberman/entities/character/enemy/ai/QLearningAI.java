/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uet.oop.bomberman.entities.character.enemy.ai;

import static java.lang.Math.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.entities.bomb.FlameSegment;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Enemy;
import uet.oop.bomberman.entities.character.enemy.ai.AI;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.entities.tile.destroyable.Brick;
import uet.oop.bomberman.level.FileLevelLoader;

/**
 *
 * @author Cong
 */
public class QLearningAI extends AI{
    final int HEIGHT = 13;
    final int WIDTH = 31;
    final int DOWN = 0;
    final int UP = 2;
    final int LEFT = 3;
    final int RIGHT = 1;
    //Value Size
    private int num_actions = 4;
    //Optimistic initialization
    private boolean opt_init = true;
    // Frequency with which we should explore
    private float epsilon = 0.2f;
    /* Saves the eligibility traces, same size as the 
    feature vector. */
    // Lưu dấu vết
    float[][][] eligibility = new float [num_actions][HEIGHT][WIDTH];
    /*
    vector of integer of length N that tells us 
    how many time we've played each action
    */
    int[] count;
    /* Discounted rewards we expect to receive if we start 
        at state s, take action a*/
    // Lưu trữ rewards- phần thưởng, state - trạng thái môi trường và hành động
    float [][][]Q = new float [num_actions][HEIGHT][WIDTH];
    /*
    float Parameter function vector
    */
    float [][]parameter_vector = new float[HEIGHT][WIDTH];
    /* Discount factor for future rewards [0.0, 1.0]*/
    float gama = 0.9f;
    float lambda = 0.1f;
    /* Learning rate*/
    float alpha = 0.1f;
    int larget_reage;
    float episode_score = 0.0f;
    ArrayList<Float> epss = new ArrayList<>();
    int []s0 = new int[2];
    int a0;
    float r0;
    int []s1 = new int[2];
    int a1;
    int starting = 1;
    Board _board;
    public QLearningAI(Board _board){
        this._board = _board;
    }
    public int next_action(){
        int pos_x = (int)_board.getBomber().getXTile();
        int pos_y = (int)_board.getBomber().getYTile();
        int action = -1;
        if(starting == 1){
            action = start_episode();
        }else{
            r0 = -0.2f;
            for(int i = 1; i < 2; i++){
                Entity e1 = _board.getEntity(pos_x+i, pos_y, _board.getBomber());
                Entity e2 = _board.getEntity(pos_x-i, pos_y, _board.getBomber());
                Entity e3 = _board.getEntity(pos_x, pos_y+i, _board.getBomber());
                Entity e4 = _board.getEntity(pos_x, pos_y-i, _board.getBomber());
                    if(e1 instanceof Bomb ||e2 instanceof Bomb ||e3 instanceof Bomb ||e4 instanceof Bomb){
                        r0 = (float) -1.5;
                        break;
                    }
            }
            if(r0 == -0.2f){
                Entity e = _board.getEntity(pos_x, pos_y, _board.getBomber());
                if (e instanceof Bomber){
                    r0 = 20.0f;
                    episode_score += r0;
                    if ( episode_score <= 17.4f){
                        //std::cout << "Player " << PLAYER_ID << " scored:" << episode_score << std::endl;
                        epss.add(episode_score);
                    }
                    episode_score = 0;
                }else{
                    episode_score += r0;
                }
            }
            action = step();
        }
        return action;
    }
    public int start_episode(){
        starting = 0;
        for(int i = 0; i < HEIGHT; i++){
            for(int j = 0; j < WIDTH; j++){
                if(!opt_init){
                    parameter_vector[i][j] = 0.0f;
                }else{
                    parameter_vector[i][j] = 1.0f/(WIDTH*HEIGHT);
                }
            }
        }
        for(int a = 0; a < num_actions; a++){
            float Qoa = 0.0f;
            for(int i = 0; i < HEIGHT; i++){
                for(int j = 0; j < WIDTH; j++){
                    Q[a][i][j] = 0.0f;
                    eligibility[a][i][j] = 0;
                }
            }
            //update start state & action s0 & a0;
            a0 = get_max_value_index();
            s1[0] = (int)_board.getBomber().getXTile();
            s1[1] = (int)_board.getBomber().getYTile();
        }
        return a0;
    }
    public int e_greedy( ){
        Random random = new Random();
	float rnumb = (float) (random.nextInt(100))/100;
	if( rnumb < epsilon){
		return random.nextInt() % num_actions;
	}
	else{
		return get_max_value_index();
	}
    }
    public int get_max_value_index(){
        float qmax = -99999.0f;
        int qmax_index = 0;
        for(int a = 0; a < num_actions; a++){
            if(can_move2(a) && Q[a][(int)_board.getBomber().getXTile()][(int)_board.getBomber().getYTile()] >= qmax){
                qmax = Q[a][(int)_board.getBomber().getXTile()][(int)_board.getBomber().getYTile()];
                qmax_index = a;
            }
        }
        return qmax_index;
    }
    public boolean can_move2(int a){
        int pos_x = (int)_board.getBomber().getXTile();
        int pos_y = (int)_board.getBomber().getYTile();
        System.out.println(pos_x + " " + pos_y);
//        if(a == DOWN){
//            Entity e = _board.getEntityGrass(pos_x, pos_y  + a);
//            if(!e.collide(_board.getBomber()))
//                return false;
//            return true;
//        }
//        if(a == UP){
//            Entity e = _board.getEntityGrass(pos_x, pos_y - a);
//            if(!e.collide(_board.getBomber()))
//                return false;
//            return true;
//        }    
//        if(a == LEFT){
//            Entity e = _board.getEntityGrass(pos_x - a, pos_y);
//            if(!e.collide(_board.getBomber()))
//                return false;
//            return true;
//        }    
//        if(a == RIGHT){
//            Entity e = _board.getEntityGrass(pos_x + a, pos_y);
//            if(!e.collide(_board.getBomber()))
//                return false;
//            return true;
//        }
    if(a == DOWN){
            Entity e = _board.getEntity(pos_x, pos_y  + a, _board.getBomber());
            if (e instanceof Wall) {
                return false;
            }
            if (e instanceof Bomb) {
                return false;
            }
            if (e instanceof Enemy) {
                return false;
            }
            if (e instanceof Flame || e instanceof FlameSegment) {
                return false;
            }
            if (e instanceof LayeredEntity) {
                LayeredEntity layerE = (LayeredEntity)e;
                if (layerE.getTopEntity() instanceof Brick) {
                    return false;
                }
            }
        }
        if(a == UP){
            Entity e = _board.getEntity(pos_x, abs(pos_y - a), _board.getBomber());
            if (e instanceof Wall) {
                return false;
            }
            if (e instanceof Enemy) {
                return false;
            }
            if (e instanceof Bomb) {
                return false;
            }
            if (e instanceof Flame || e instanceof FlameSegment) {
                return false;
            }
            if (e instanceof LayeredEntity) {
                LayeredEntity layerE = (LayeredEntity)e;

                if (layerE.getTopEntity() instanceof Brick) {
                    return false;
                }
            }
        }    
        if(a == LEFT){
            Entity e = _board.getEntity(abs(pos_x - a), pos_y, _board.getBomber());
            if (e instanceof Wall) {
                return false;
            }
            if (e instanceof Bomb) {
                return false;
            }
            if (e instanceof Enemy) {
                return false;
            }
            if (e instanceof Flame || e instanceof FlameSegment) {
                return false;
            }
            if (e instanceof LayeredEntity) {
                LayeredEntity layerE = (LayeredEntity)e;

                if (layerE.getTopEntity() instanceof Brick) {
                    return false;
                }
            }
        }    
        if(a == RIGHT){
            Entity e = _board.getEntity(pos_x + a, pos_y, _board.getBomber());
            if (e instanceof Wall) {
                return false;
            }
            if (e instanceof Enemy) {
                return false;
            }
            if (e instanceof Bomb) {
                return false;
            }
            if (e instanceof Flame || e instanceof FlameSegment) {
                return false;
            }
            if (e instanceof LayeredEntity) {
                LayeredEntity layerE = (LayeredEntity)e;

                if (layerE.getTopEntity() instanceof Brick) {
                    return false;
                }
            }
        }
        return false;
    }
    /* Calculates euclidean distance */
    double distance_Calculate(int x1, int y1, int x2, int y2){
        int x = x1 - x2;
        int y = y1 - y2;
        double dist;
        dist = pow(x,2)+pow(y,2); 
        dist = sqrt(dist);
        return dist;
    }
    /* Update largest range */
    int step(){
        // get next action a1
        int action = e_greedy();
        // update s0,s1,a0,a1
    
        s0[0] = s1[0];
        s0[1] = s1[1];
        s1[0] = (int)_board.getBomber().getXTile();
        s1[1] = (int)_board.getBomber().getYTile();
        //Q learning
        int qmax_i = get_max_value_index();
        float qmax = Q[qmax_i][(int)_board.getBomber().getXTile()][(int)_board.getBomber().getYTile()];
        float target = r0 + gama * qmax;
    
        eligibility[a0][s0[0]][s0[1]]++;
        // simpler and faster update without eligibility trace
        // update Q[sa] towards it with some step size
        float update = alpha * (target - Q[a0][s0[0]][s0[1]]);
    
        for (int a = 0; a < num_actions; a++){
            for (int i = 0; i < HEIGHT; ++i){
                for (int j = 0; j < WIDTH; ++j){
                    Q[a][i][j] += update * eligibility[a][i][j];
                    if ( action == qmax_i )
                        eligibility[a][i][j] = lambda * gama * eligibility[a][i][j];
                    else    
                        eligibility[a][i][j] = 0;
                }
            }
        }
        a0 = action;
        return action;
    }
    @Override
    public int calculateDirection() {
        int _direction = -1;
        _direction = next_action();
        return _direction;
    }
}
