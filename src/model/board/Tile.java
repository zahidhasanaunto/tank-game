/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.board;

import model.command.ITankCommand;
import model.tank.Tank;
import model.board.Board;
import java.util.ArrayList;

/**
 *
 * @author bruceoutdoors
 */
public class Tile {

    private final int m_col;
    private final int m_row;

    public enum Direction {
        NONE,
        TOP,
        BOTTOM,
        LEFT,
        RIGHT
    }

    private ArrayList<ITankCommand> m_playerCommands = null;
    private Tank m_tank = null;
    private Board m_board;
    private Boolean m_blasted = false;

    public Tile(Board b, int row, int col) {
        m_row = row;
        m_col = col;
        m_board = b;
    }

    public int getRow() {
        return m_row;
    }

    public int getCol() {
        return m_col;
    }

    public Tile getNeighbor(Direction dir) {
        return m_board.getNeighborTile(this, dir);
    }

    public void setTank(Tank tank) {
        m_tank = tank;
    }

    public Tank getTank() {
        return m_tank;
    }

    public void setBlasted(Boolean b) {
        m_blasted = b;
    }

    public Boolean isBlasted() {
        return m_blasted;
    }
    
    public void setPlayerCommands(ArrayList<ITankCommand> commands) {
        m_playerCommands = commands;
    }
    
    public ArrayList<ITankCommand> getPlayerCommands() {
        return m_playerCommands;
    }

    static public Direction getOppositeDirection(Direction d) {
        switch (d) {
            case TOP:
                return Direction.BOTTOM;
            case BOTTOM:
                return Direction.TOP;
            case RIGHT:
                return Direction.LEFT;
            case LEFT:
                return Direction.RIGHT;
        }

        return Direction.NONE;
    }

    static public String getDirectionStr(Direction d) {
        switch (d) {
            case TOP:
                return "top";
            case BOTTOM:
                return "bottom";
            case RIGHT:
                return "right";
            case LEFT:
                return "left";
        }

        return "Avada Kedavra!! This Ain't suppose to happen D:";
    }
}
