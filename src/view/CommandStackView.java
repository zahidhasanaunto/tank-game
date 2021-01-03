/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.Iterator;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import model.command.ITankCommand;
import model.command.TankCommandStack;

/**
 *
 * @author bruceoutdoors
 */
public class CommandStackView {
    private JList m_jl;
    private TankCommandStack m_tcs;
    private DefaultListModel<String> m_model;
    
    public CommandStackView(JList jl, TankCommandStack tcs) {
        m_jl = jl;
        m_tcs = tcs;
        m_model = new DefaultListModel<>();
        m_jl.setModel(m_model);
    }
    
    public void updateView(Boolean isMasked) {
        m_model.removeAllElements();
        Integer i = 1;
        Iterator<ITankCommand> iter = m_tcs.getIterator();
        while (iter.hasNext()) {
            ITankCommand itc = iter.next();
            if (isMasked) {
                m_model.add(0,  "Command #" + i.toString());
            } else {
                m_model.add(0, i.toString() + " - " + itc.toString());
            }
            
            i++;
        }
        
        m_jl.setSelectedIndex(0);
    }
}
