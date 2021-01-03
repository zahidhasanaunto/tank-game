/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import model.command.ITankCommand;
import model.board.Tile;

/**
 *
 * @author bruceoutdoors
 */
public class TankCommandPopupMenu extends JPopupMenu {

    private Tile m_tile;
    private ObservableTankCommandPopup m_observablepopup;

    TankCommandPopupMenu(Observer ob, Tile tile) {
        m_tile = tile;
        m_observablepopup = new ObservableTankCommandPopup();
        m_observablepopup.addObserver(ob);
    }

    public void showPopup(Component invoker, MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            if (m_tile.getPlayerCommands() != null) {
                removeAll();
                for (ITankCommand itc : m_tile.getPlayerCommands()) {
                    JMenuItem item = new JMenuItem(itc.getCommandName());
                    add(item);
                    item.setHorizontalTextPosition(JMenuItem.RIGHT);
                    item.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent event) {
                            System.out.println("Popup menu item ["
                                    + event.getActionCommand() + "] was pressed.");
                            m_observablepopup.notifyObservers(itc);
                        }
                    });
                }

                show(invoker, e.getX(), e.getY());
            }

        }
    }

    private class ObservableTankCommandPopup extends Observable {

        public ObservableTankCommandPopup() {

        }

        // what is changed for? Annoyance. Guh.
        @Override
        public void notifyObservers(Object arg) {
            setChanged();
            super.notifyObservers(arg);
        }
    }
}
