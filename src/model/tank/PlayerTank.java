/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.tank;

import model.board.Board;
import java.util.ArrayList;
import model.command.AttackCommand;
import model.command.ITankCommand;
import model.command.MoveCommand;
import model.board.Tile;

/**
 *
 * @author bruceoutdoors
 */
public class PlayerTank extends Tank {
    
    public PlayerTank(Tile tile, Board board) {
        super(tile, board);
    }
    
    @Override
    public Boolean move(Tile.Direction d) {
        Boolean result = super.move(d);
        
        if (result) updatePlayerMoves();
        
        return result;
    } 
    
    @Override
    public Boolean attack(Tile.Direction d) {
        Boolean result = super.attack(d);
        
        if (result) updatePlayerMoves();
        
        return result;
    }
    
    public void updatePlayerMoves() {
        if (!m_board.isSimulationMode()) {
            return;
        }

        m_board.clearPlayerMoves();

        for (Tile.Direction d : Tile.Direction.values()) {
            if (d == Tile.Direction.NONE) {
                continue;
            }

            Tile t = m_tile.getNeighbor(d);
            if (t != null) {
                ArrayList<ITankCommand> commands = new ArrayList<>();
                commands.add(new AttackCommand(this, d));
                commands.add(new MoveCommand(this, d));
                t.setPlayerCommands(commands);
            }
        }
    }
    
}
