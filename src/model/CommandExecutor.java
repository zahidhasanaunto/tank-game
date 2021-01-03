/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import model.command.TankCommandStack;
import model.command.ITankCommand;
import model.tank.Tank;
import model.board.Board;
import java.util.Iterator;

/**
 *
 * @author bruceoutdoors
 */
public class CommandExecutor {

    public enum GameState {
        NONE,
        STILLEXECUTING,
        PLAYERWIN,
        ENEMYWIN,
        DRAW,
        DOUBLEKILL
    }

    private int m_currentStep = 0;
    private TankCommandStack m_playerStack;
    private TankCommandStack m_enemyStack;
    private Iterator<ITankCommand> m_playerCommandIterator;
    private Iterator<ITankCommand> m_enemyCommandIterator;
    private Tank m_enemyTank;
    private Tank m_playerTank;
    private Board m_board;

    public CommandExecutor(Board b, TankCommandStack playerStack, TankCommandStack enemyStack) {
        m_playerStack = playerStack;
        m_enemyStack = enemyStack;
        m_enemyTank = b.getEnemyTank();
        m_playerTank = b.getPlayerTank();
        m_board = b;

        m_playerCommandIterator = m_playerStack.getIterator();
        m_enemyCommandIterator = m_enemyStack.getIterator();
    }

    public GameState step() {
        // begining. Do nothing.
        if (m_currentStep == 0) {
            m_currentStep++;
            return GameState.STILLEXECUTING;
        }
        
        if (m_playerCommandIterator.hasNext()) {
            // remove past attacks
            m_board.resetBlasts();

            ITankCommand playerCommand = m_playerCommandIterator.next();
            ITankCommand enemyCommand = m_enemyCommandIterator.next();
            playerCommand.execute();
            enemyCommand.execute();
            m_currentStep++;
            
            // Attacks don't happen simultaneously. So we just hack it here. 
            // Who's going to notice right? Hahaha...
            if (m_playerTank.getTile().isBlasted()) m_playerTank.setAlive(false);
            if (m_enemyTank.getTile().isBlasted()) m_enemyTank.setAlive(false);

            if (!m_playerTank.isAlive() && !m_enemyTank.isAlive()) {
                return GameState.DOUBLEKILL;
            } else if (!m_playerTank.isAlive()) {
                return GameState.ENEMYWIN;
            } else if (!m_enemyTank.isAlive()) {
                return GameState.PLAYERWIN;
            } else if (!m_playerCommandIterator.hasNext() 
                    && m_enemyTank.isAlive() 
                    && m_playerTank.isAlive()) {
                return GameState.DRAW;
            }

            return GameState.STILLEXECUTING;
        } else {
            throw new RuntimeException("Nothing more to step. Hello??");
        }
    }

    public void restart() {
        m_board.resetBoard();
        m_currentStep = 0;

        m_playerCommandIterator = m_playerStack.getIterator();
        m_enemyCommandIterator = m_enemyStack.getIterator();
    }
    
    public int getCurrentStep() {
        return m_currentStep;
    }
}
