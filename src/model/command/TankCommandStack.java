/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.command;

import model.command.ITankCommand;
import java.util.Iterator;
import java.util.Stack;

/**
 *
 * @author bruceoutdoors
 */
public class TankCommandStack {
    private final Stack<ITankCommand> m_historyStack;
    private int m_size; 
    
    public TankCommandStack(int size) {
        m_size = size;
        m_historyStack = new Stack<>();
    }
    
    public void addAndExecute(ITankCommand command) {
        if (isFull()) {
            throw new RuntimeException("Command stack is full!");
        }
        command.execute();
        m_historyStack.add(command);
    }
    
    public void undo() {
        m_historyStack.pop().undo();
    }
    
    public Iterator<ITankCommand> getIterator() {
        return m_historyStack.iterator();
    }
    
    public Boolean isEmpty() {
        return m_historyStack.empty();
    }
    
    public Boolean isFull() {
        return m_size == m_historyStack.size();
    }
    
    public int currentSize() {
        return m_historyStack.size();
    }
    
    public void clear() {
        m_historyStack.clear();
    }
    
    public int maxSize() {
        return m_size;
    }
}
