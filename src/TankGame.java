
import java.awt.Color;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import model.CommandExecutor;
import model.CommandExecutor.GameState;
import model.board.Board;
import model.command.ITankCommand;
import model.command.TankCommandStack;
import model.board.Tile;
import model.command.TankCommandFileIO;
import view.CommandStackView;
import view.TankCommandPopupMenu;
import view.TileView;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author darkzgothic
 */
public class TankGame extends javax.swing.JFrame implements Observer {
    
    final String ENEMY_MEMORY_PATH = "saved/enemymoves.txt";
    final String PLAYER_MEMORY_PATH = "saved/playermoves.txt";
    
    private int ROW = 8;
    private int COL = 8;
    private int MAX_COMMANDS = 18;
    private Board m_board;
    private Tile[][] m_tilesArr;
    private TileView[][] m_boardButtons;
    private TankCommandPopupMenu m_popup;
    private TankCommandStack m_playerCommandStack;
    private TankCommandStack m_enemyCommandStack;
    private CommandStackView m_playerCommandView;
    private CommandStackView m_enemyCommandView;
    private CommandExecutor m_executor;
    private Timer m_executorTimer;
    private JFrame m_this = this;
    private Boolean m_isProcessingCommandStack = false;
    private Boolean m_hasWon = false;
    private Integer m_numTries = 0;
    
    private class executeStepTask extends TimerTask {
        
        @Override
        public void run() {
            GameState state = m_executor.step();
            
            redrawBoard();
            
            if (m_executor.getCurrentStep() == 1) {
                playerCommandDisplay.clearSelection();
                enemyCommandDisplay.clearSelection();
            } else {
                int displayIdx = MAX_COMMANDS - m_executor.getCurrentStep() + 1;
                playerCommandDisplay.setSelectedIndex(displayIdx);
                enemyCommandDisplay.setSelectedIndex(displayIdx);
            }
            
            if (state == GameState.DRAW) {
                JOptionPane.showMessageDialog(m_this, "It's a draw!");
            } else if (state == GameState.DOUBLEKILL) {
                JOptionPane.showMessageDialog(m_this, "You both died");
            } else if (state == GameState.ENEMYWIN) {
                JOptionPane.showMessageDialog(m_this, "You got killed by the AI!");
            } else if (state == GameState.PLAYERWIN) {
                m_hasWon = true;
                attemptCountLbl.setForeground(Color.RED);
                JOptionPane.showMessageDialog(m_this, "Congrats! You beat in AI after "
                        + m_numTries.toString()
                        + " attempts!");
            }
            
            if (state != GameState.STILLEXECUTING) {
                if (!m_hasWon) {
                    m_numTries++;
                }
                
                m_executorTimer.cancel();
                // pause 1 second:
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException ex) {
                    Logger.getLogger(TankGame.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                undoBtn.setEnabled(true);
                returnBackToSimulationMode();
            }
        }
        
    }

    /**
     * Creates new form TankGame
     *
     * @throws java.io.IOException
     */
    public TankGame() throws IOException {
        
        initComponents();
        m_board = new Board(ROW, COL);
        m_tilesArr = m_board.getBoardArr();
        m_boardButtons = new TileView[ROW][COL];
        board.setLayout(new GridLayout(ROW, COL));
        setResizable(false);
        for (Integer r = 0; r < ROW; r++) {
            for (Integer c = 0; c < COL; c++) {
                TileView jb = new TileView(this, m_tilesArr[r][c]);
                m_boardButtons[r][c] = jb;
                board.add(jb);
            }
        }
        
        setupEnemyTank();
        setupPlayerTank();
        
        redrawBoard();
    }
    
    public void redrawBoard() {
        Integer movesRemain = MAX_COMMANDS - m_playerCommandStack.currentSize();
        attemptCountLbl.setText(m_numTries.toString());
        if (m_hasWon) {
            youWonLbl.setVisible(true);
        } else {
            youWonLbl.setVisible(false);
        }
        
        if (m_board.isSimulationMode()) {
            simulationMsg.setVisible(false);
            if (m_playerCommandStack.isFull()) {
                m_board.clearPlayerMoves();
                executeBtn.setEnabled(true);
            } else {
                executeBtn.setEnabled(false);
                m_board.getPlayerTank().updatePlayerMoves();
            }
            
            movesRemainLbl.setText(movesRemain.toString());
            if (movesRemain == 0) {
                movesRemainLbl.setForeground(Color.RED);
            } else {
                movesRemainLbl.setForeground(new Color(0, 153, 0));
            }
        } else {
            simulationMsg.setVisible(true);
        }
        
        for (Integer r = 0; r < ROW; r++) {
            for (Integer c = 0; c < COL; c++) {
                int w = m_boardButtons[r][c].getWidth();
                m_boardButtons[r][c].updateTile();
            }
        }
        
        m_playerCommandView.updateView(false);
        m_enemyCommandView.updateView(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        board = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        playerCommandDisplay = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        enemyCommandDisplay = new javax.swing.JList<>();
        jLabel3 = new javax.swing.JLabel();
        undoBtn = new javax.swing.JButton();
        executeBtn = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        movesRemainLbl = new javax.swing.JLabel();
        simulationMsg = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        attemptCountLbl = new javax.swing.JLabel();
        youWonLbl = new javax.swing.JLabel();
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        newGameMenuItem = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        howPlayMenuItem = new javax.swing.JMenu();
        aboutMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Robot War by DrakZGothiC");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        board.setBackground(new java.awt.Color(153, 255, 255));
        board.setLayout(null);

        jPanel1.setLayout(new java.awt.BorderLayout(0, 5));

        playerCommandDisplay.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "1 - Move LEFT", "2 - Attack RIGHT", "3 - Move TOP", "4 - Move TOP", "..." };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        playerCommandDisplay.setEnabled(false);
        jScrollPane1.setViewportView(playerCommandDisplay);

        jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jLabel1.setText("Player Moves");
        jPanel1.add(jLabel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setLayout(new java.awt.BorderLayout(0, 5));

        enemyCommandDisplay.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Command #1", "Command #2", "Command #3", "Command #4", "Command #5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        enemyCommandDisplay.setEnabled(false);
        jScrollPane3.setViewportView(enemyCommandDisplay);

        jPanel2.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        jLabel3.setText("Enemy Moves");
        jPanel2.add(jLabel3, java.awt.BorderLayout.PAGE_START);

        undoBtn.setText("Undo");
        undoBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                undoBtnActionPerformed(evt);
            }
        });

        executeBtn.setText("Execute!");
        executeBtn.setEnabled(false);
        executeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                executeBtnActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Moves Remaining:");

        movesRemainLbl.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        movesRemainLbl.setForeground(new java.awt.Color(0, 153, 0));
        movesRemainLbl.setText("15");

        simulationMsg.setForeground(new java.awt.Color(255, 0, 51));
        simulationMsg.setText("Executing moves... please wait...");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Number of attempts:");

        attemptCountLbl.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        attemptCountLbl.setText("0");

        youWonLbl.setForeground(new java.awt.Color(51, 51, 255));
        youWonLbl.setText("You are won the game. Stick around or start a new one");

        jMenu3.setText("File");

        newGameMenuItem.setText("New Game...");
        newGameMenuItem.setActionCommand("");
        newGameMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newGameMenuItemActionPerformed(evt);
            }
        });
        jMenu3.add(newGameMenuItem);

        jMenuBar2.add(jMenu3);

        jMenu4.setText("Help");

        howPlayMenuItem.setText("What to do ar?");
        jMenu4.add(howPlayMenuItem);

        aboutMenuItem.setText("About");
        jMenu4.add(aboutMenuItem);

        jMenuBar2.add(jMenu4);

        setJMenuBar(jMenuBar2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(movesRemainLbl)
                                .addGap(18, 18, 18)
                                .addComponent(executeBtn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(undoBtn))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(board, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(8, 8, 8))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(simulationMsg)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(attemptCountLbl)
                        .addGap(18, 18, 18)
                        .addComponent(youWonLbl)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(attemptCountLbl)
                    .addComponent(youWonLbl))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(movesRemainLbl)
                    .addComponent(executeBtn)
                    .addComponent(undoBtn))
                .addGap(1, 1, 1)
                .addComponent(simulationMsg)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(board, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void newGameMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newGameMenuItemActionPerformed
        int answer = JOptionPane.showConfirmDialog(m_this, "Are you sure you want to create new game? This will restart the AI moves!");
        if (answer == JOptionPane.YES_OPTION) {
            try {
                File file = new File(Paths.get(PLAYER_MEMORY_PATH).toString());
                file.delete();
                file = new File(Paths.get(ENEMY_MEMORY_PATH).toString());
                file.delete();
            } catch (Exception e) {
                
            }
            m_hasWon = false;
            m_numTries = 0;
            attemptCountLbl.setForeground(Color.BLACK);
            setupEnemyTank();
            setupPlayerTank();
            redrawBoard();
        }

    }//GEN-LAST:event_newGameMenuItemActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        saveGameToFiles();
    }//GEN-LAST:event_formWindowClosing

    private void executeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_executeBtnActionPerformed
        undoBtn.setEnabled(false);
        m_executor = new CommandExecutor(m_board, m_playerCommandStack, m_enemyCommandStack);
        executeBtn.setEnabled(false);
        m_board.setSimulationMode(false);
        m_board.resetBoard();
        
        m_executorTimer = new Timer();
        m_executorTimer.scheduleAtFixedRate(new executeStepTask(), 0, 1000);
    }//GEN-LAST:event_executeBtnActionPerformed

    private void undoBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_undoBtnActionPerformed
        m_playerCommandStack.undo();
        redrawBoard();
    }//GEN-LAST:event_undoBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JLabel attemptCountLbl;
    private javax.swing.JPanel board;
    private javax.swing.JList<String> enemyCommandDisplay;
    private javax.swing.JButton executeBtn;
    private javax.swing.JMenu howPlayMenuItem;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel movesRemainLbl;
    private javax.swing.JMenuItem newGameMenuItem;
    private javax.swing.JList<String> playerCommandDisplay;
    private javax.swing.JLabel simulationMsg;
    private javax.swing.JButton undoBtn;
    private javax.swing.JLabel youWonLbl;
    // End of variables declaration//GEN-END:variables

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof ITankCommand) {
            onITankCommand((ITankCommand) arg);
        }
    }
    
    private void onITankCommand(ITankCommand itc) {
        if (m_board.isSimulationMode()) {
            if (!m_playerCommandStack.isFull()) {
                m_playerCommandStack.addAndExecute(itc);
            }
            
        }
        
        if (!m_isProcessingCommandStack) {
            redrawBoard();
        }
    }
    
    private void setupEnemyTank() {
        m_isProcessingCommandStack = true;
        TankCommandFileIO io = new TankCommandFileIO(m_board.getEnemyTank());
        
        try {
            m_enemyCommandStack = io.read(ENEMY_MEMORY_PATH, MAX_COMMANDS);
            assert m_enemyCommandStack.currentSize() == MAX_COMMANDS;
        } catch (IOException ex) {
            m_enemyCommandStack = m_board.getEnemyTank().randGenerateMoves(MAX_COMMANDS);
        }
        
        m_board.resetBoard();
        m_enemyCommandView = new CommandStackView(enemyCommandDisplay, m_enemyCommandStack);
        
        m_board.resetBlasts();
        m_isProcessingCommandStack = false;
    }
    
    private void saveGameToFiles() {
        TankCommandFileIO ioEnemy = new TankCommandFileIO((m_board.getEnemyTank()));
        TankCommandFileIO ioPlayer = new TankCommandFileIO((m_board.getPlayerTank()));
        
        try {
            ioEnemy.write(m_enemyCommandStack, ENEMY_MEMORY_PATH);
            ioPlayer.write(m_playerCommandStack, PLAYER_MEMORY_PATH);
        } catch (Exception ex) {
            Logger.getLogger(TankGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void setupPlayerTank() {
        m_isProcessingCommandStack = true;
        TankCommandFileIO io = new TankCommandFileIO(m_board.getPlayerTank());
        
        try {
            m_playerCommandStack = io.read(PLAYER_MEMORY_PATH, MAX_COMMANDS);
        } catch (IOException ex) {
            m_playerCommandStack = new TankCommandStack(MAX_COMMANDS);
        }
        
        m_playerCommandView = new CommandStackView(playerCommandDisplay, m_playerCommandStack);
        m_board.resetBlasts();
        
        m_isProcessingCommandStack = false;
    }
    
    private void returnBackToSimulationMode() {
        m_board.setSimulationMode(true);
        m_isProcessingCommandStack = true;
        m_board.resetBoard();
        
        Iterator<ITankCommand> iter = m_playerCommandStack.getIterator();
        while (iter.hasNext()) {
            ITankCommand itc = iter.next();
            itc.execute();
        }
        
        m_board.resetBlasts();
        m_isProcessingCommandStack = false;
        redrawBoard();
    }
    
}
