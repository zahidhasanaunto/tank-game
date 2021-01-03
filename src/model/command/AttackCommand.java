/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.command;

import model.tank.Tank;
import model.board.Tile;
import model.board.Tile.Direction;

/**
 *
 * @author bruceoutdoors
 */
public class AttackCommand implements ITankCommand {

    private final Tank m_tank;
    private final Direction m_direction;
    private final Direction m_prevDirection;

    public AttackCommand(Tank t, Direction d) {
        m_tank = t;
        m_direction = d;
        m_prevDirection = t.getDirection();
    }

    @Override
    public String getCommandName() {
        return "ATTACK";
    }

    @Override
    public void execute() {
        m_tank.attack(m_direction);
    }

    @Override
    public void undo() {
        Tile t = m_tank.getTile().getNeighbor(m_direction);
        if (t != null) t.setBlasted(false);
        m_tank.setDirection(m_prevDirection);
    }

    @Override
    public Tile.Direction getDirection() {
        return m_direction;
    }
    
    @Override 
    public String toString() {
        return "Attack " + Tile.getDirectionStr(m_direction) + " Tile";
    }

}
