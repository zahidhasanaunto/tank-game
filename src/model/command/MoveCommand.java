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
public class MoveCommand implements ITankCommand {

    private final Tank m_tank;
    private final Direction m_direction;
    private final Direction m_oppositeDirection;
    private final Direction m_prevDirection;

    public MoveCommand(Tank t, Direction d) {
        m_tank = t;
        m_direction = d;
        m_oppositeDirection = Tile.getOppositeDirection(m_direction);
        m_prevDirection = t.getDirection();
    }

    @Override
    public String getCommandName() {
        return "MOVE";
    }

    @Override
    public void execute() {
        m_tank.move(m_direction);
    }

    @Override
    public void undo() {
        m_tank.move(m_oppositeDirection);
        m_tank.setDirection(m_prevDirection);
    }

    @Override
    public Tile.Direction getDirection() {
        return m_direction;
    }

    @Override
    public String toString() {
        return "Move " + Tile.getDirectionStr(m_direction);
    }
}
