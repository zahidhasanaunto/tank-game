/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.tank;

import model.board.Board;
import java.util.ArrayList;
import java.util.Random;
import model.command.AttackCommand;
import model.command.ITankCommand;
import model.command.MoveCommand;
import model.command.TankCommandStack;
import model.board.Tile;

/**
 *
 * @author bruceoutdoors
 */
public class EnemyTank extends Tank {
    private Random randomGen;
    
    public EnemyTank(Tile tile, Board board) {
        super(tile, board);
        randomGen = new Random();
    }
    
    public ArrayList<ITankCommand> getAvailableMoves() {
        ArrayList<ITankCommand> moves = new ArrayList<>();
        
        for (Tile.Direction d : Tile.Direction.values()) {
            if (d == Tile.Direction.NONE) {
                continue;
            }

            Tile t = m_tile.getNeighbor(d);
            if (t != null) {
                moves.add(new AttackCommand(this, d));
                moves.add(new MoveCommand(this, d));
            }
        }
        
        return moves;
    }
    
    // this also executes the moves, so remember to reset the board afterwards
    public TankCommandStack randGenerateMoves(int movesCount) {
        TankCommandStack tcs = new TankCommandStack(movesCount);
        
        for (int i = 0; i < movesCount; i++) {
            ArrayList<ITankCommand> ms = getAvailableMoves();
            ITankCommand command = ms.get(randomGen.nextInt(ms.size()));
            tcs.addAndExecute(command);
        }
        
        return tcs;
    }
    
}
