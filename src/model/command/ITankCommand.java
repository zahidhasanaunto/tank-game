/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.command;

import model.board.Tile.Direction;

/**
 *
 * @author bruceoutdoors
 */
public interface ITankCommand extends ICommand {

    String getCommandName();

    void execute();

    void undo();

    Direction getDirection();
}
