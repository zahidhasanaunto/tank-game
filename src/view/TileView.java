/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Observer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import model.AssetManager;
import model.ImageTool;
import model.tank.PlayerTank;
import model.tank.Tank;
import model.board.Tile;
import model.board.Tile.Direction;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**
 *
 * @author bruceoutdoors
 */
public class TileView extends JButton implements MouseListener {

    private Tile m_tile;
    private AssetManager m_am;
    private Timer m_blastTimer;
    private TankCommandPopupMenu m_popup;

    public TileView(Observer ob, Tile t) throws IOException {
        m_tile = t;
        addMouseListener(this);
        setEnabled(false);
        m_am = AssetManager.getInstance();
        setBorder(new LineBorder(Color.GRAY));
        setDisabledIcon(m_am.DEFAULT_TILE);
        setIcon(m_am.DEFAULT_TILE);
        m_blastTimer = new Timer();
//        TankCommandPopupMenu ttt = new TankCommandPopupMenu();
//        ttt.set
//        setComponentPopupMenu(ttt);
        m_popup = new TankCommandPopupMenu(ob, m_tile);
    }

    public void updateTile() {
        setEnableTile(false);
        Tank tank = m_tile.getTank();
        if (tank != null) {
            setEnableTile(false);
            Direction d = tank.getDirection();
            int rotation = 0;

            switch (d) {
                case LEFT:
                    rotation = 90;
                    break;
                case TOP:
                    rotation = 180;
                    break;
                case RIGHT:
                    rotation = 270;
                    break;
            }
            ImageIcon img = m_am.ENEMY_TILE;
            if (tank instanceof PlayerTank) {
                img = m_am.PLAYER_TILE;
            }
            if (rotation != 0) {
                Image m = ImageTool.rotate(img.getImage(), rotation);
                setDisabledIcon(new ImageIcon(m));
            } else {
                setDisabledIcon(img);
            }
        } else if (m_tile.getPlayerCommands() != null) {
            setEnableTile(true);
            
        }

        if (m_tile.isBlasted()) {
            if (tank != null) {
                setIcon(m_am.EXPLOSION_TILE);
                setDisabledIcon(m_am.EXPLOSION_TILE);
            } else {
                m_tile.setBlasted(false);
                Boolean wasEnabled = isEnabled();
                setEnableTile(false);
                setIcon(m_am.EXPLOSION_TILE);
                setDisabledIcon(m_am.EXPLOSION_TILE);

                m_blastTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        setEnableTile(wasEnabled);
                    }
                }, 500);
            }

        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!isEnabled()) {
            return;
        }
        
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!isEnabled()) {
            return;
        }
        
        if (m_tile.getPlayerCommands() != null) {
            m_popup.showPopup(this, e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!isEnabled()) {
            return;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (!isEnabled()) {
            return;
        }
        setIcon(m_am.ONHOVER_TILE);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (!isEnabled()) {
            return;
        }
        setIcon(m_am.ACTIVE_TILE);
    }

    public void setEnableTile(Boolean b) {
        if (b) {
            setEnabled(true);
            setIcon(m_am.ACTIVE_TILE);
        } else {
            setEnabled(false);
            setIcon(m_am.DEFAULT_TILE);
            setDisabledIcon(m_am.DEFAULT_TILE);
        }
    }

}
