/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.board;

import model.tank.EnemyTank;
import model.tank.PlayerTank;
import model.board.Tile.Direction;

/**
 *
 * @author bruceoutdoors
 */
public class Board {

    private final Tile[][] m_boardArr;
    private final int m_row;
    private final int m_col;
    private PlayerTank m_playerTank;
    private EnemyTank m_enemyTank;
    private Boolean m_isSimulationMode = false;

    public Board(int row, int col) {
        m_boardArr = new Tile[row][col];
        m_row = row;
        m_col = col;

        for (int i = 0; i < m_row; i++) {
            for (int j = 0; j < m_col; j++) {
                m_boardArr[i][j] = new Tile(this, i, j);
            }
        }
        
        m_playerTank = new PlayerTank(m_boardArr[m_row - 1][m_col - 1], this);
        m_enemyTank = new EnemyTank(m_boardArr[0][0], this);
        m_playerTank.setDirection(Direction.LEFT);
        m_enemyTank.setDirection(Direction.RIGHT);
        
        setSimulationMode(true);
        
        m_playerTank.updatePlayerMoves();
    }
    
    public void resetBoard() {
        for (int i = 0; i < m_row; i++) {
            for (int j = 0; j < m_col; j++) {
                m_boardArr[i][j].setBlasted(false);
                m_boardArr[i][j].setTank(null);
            }
        }
        
        m_playerTank.setAlive(true);
        m_enemyTank.setAlive(true);
        m_playerTank.setTile(m_boardArr[m_row - 1][m_col - 1]);
        m_enemyTank.setTile(m_boardArr[0][0]);
        
        if (m_isSimulationMode) {
            m_enemyTank.getTile().setTank(null);
        }
    }

    public Tile getNeighborTile(Tile t, Direction d) {
        int r = t.getRow();
        int c = t.getCol();

        try {
            switch (d) {
                case TOP:
                    return m_boardArr[r - 1][c];
                case BOTTOM:
                    return m_boardArr[r + 1][c];
                case LEFT:
                    return m_boardArr[r][c - 1];
                case RIGHT:
                    return m_boardArr[r][c + 1];
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            return null;
        }

        return null;
    }

    public int getRowCount() {
        return m_row;
    }

    public int getColumnCount() {
        return m_col;
    }

    public Tile[][] getBoardArr() {
        return m_boardArr;
    }
    
    public PlayerTank getPlayerTank() {
        return m_playerTank;
    }
    
    public EnemyTank getEnemyTank() {
        return m_enemyTank;
    }
    
    public void resetBlasts() {
        for (int i = 0; i < m_row; i++) {
            for (int j = 0; j < m_col; j++) {
                m_boardArr[i][j].setBlasted(false);
            }
        }
    }
    
    public void clearPlayerMoves() {
        for (int i = 0; i < m_row; i++) {
            for (int j = 0; j < m_col; j++) {
                m_boardArr[i][j].setPlayerCommands(null);
            }
        }
    }
    
    public void setSimulationMode(Boolean b) {
        m_isSimulationMode = b;
        
        // hide enemy tank during simulation
        if (m_isSimulationMode) {
            m_enemyTank.getTile().setTank(null);
        } else {
            m_enemyTank.getTile().setTank(m_enemyTank);
            clearPlayerMoves();
        }
    }
    
    public Boolean isSimulationMode() {
        return m_isSimulationMode;
    }
}
