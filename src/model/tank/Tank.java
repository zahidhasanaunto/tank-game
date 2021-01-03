/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.tank;

import model.board.Board;
import java.util.ArrayList;
import model.board.Tile;
import model.board.Tile.Direction;

/**
 *
 * @author bruceoutdoors
 */
public class Tank {

    protected Board m_board;
    protected Tile m_tile;
    protected Direction m_direction = Direction.TOP;
    protected Boolean m_isAlive = true;

    public Tank(Tile tile, Board board) {
        m_board = board;
        m_tile = tile;
        m_tile.setTank(this);
    }

    public Boolean move(Direction d) {
        Tile target = m_tile.getNeighbor(d);
        if (target != null) {
            if (target.getTank() != null && !m_board.isSimulationMode()) {
                // COLLISION COURSE!! Die Together! :D
                target.getTank().setAlive(false);
                setAlive(false);
                
                return false;
            }

            m_direction = d;
            m_tile.setTank(null);
            target.setTank(this);
            m_tile = target;
            return true;
        }

        return false;
    }

    public Boolean attack(Direction d) {
        Tile target = m_tile.getNeighbor(d);
        if (target != null) {
            m_direction = d;
            target.setBlasted(true);

            if (!m_board.isSimulationMode()) {
                Tank t = target.getTank();
                if (t != null) {
                    t.setAlive(false);
                }
            }

            return true;
        }

        return false;
    }

    public Direction getDirection() {
        return m_direction;
    }

    public void setDirection(Direction d) {
        m_direction = d;
    }

    public Tile getTile() {
        return m_tile;
    }

    public void setTile(Tile t) {
        m_tile = t;
        t.setTank(this);
    }

    public Boolean isAlive() {
        return m_isAlive;
    }

    public void setAlive(Boolean b) {
        m_isAlive = b;
    }
}
