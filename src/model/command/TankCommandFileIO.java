/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.command;

import java.io.File;
import model.board.Tile;
import model.command.AttackCommand;
import model.command.ITankCommand;
import model.command.MoveCommand;
import model.tank.Tank;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

/**
 *
 * @author bruceoutdoors
 */
public class TankCommandFileIO {

    private Tank m_tank;

    public TankCommandFileIO(Tank t) {
        m_tank = t;
    }

    public TankCommandStack read(String path, int size) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        String filecontents = new String(encoded, Charset.defaultCharset());
        filecontents = filecontents.trim();
        String commandStrs[] = filecontents.split(" ");
        TankCommandStack tcs = new TankCommandStack(size);

        for (String c : commandStrs) {
            if ("AT".equals(c)) {
                tcs.addAndExecute(new AttackCommand(m_tank, Tile.Direction.TOP));
            } else if ("AB".equals(c)) {
                tcs.addAndExecute(new AttackCommand(m_tank, Tile.Direction.BOTTOM));
            } else if ("AR".equals(c)) {
                tcs.addAndExecute(new AttackCommand(m_tank, Tile.Direction.RIGHT));
            } else if ("AL".equals(c)) {
                tcs.addAndExecute(new AttackCommand(m_tank, Tile.Direction.LEFT));
            } else if ("MT".equals(c)) {
                tcs.addAndExecute(new MoveCommand(m_tank, Tile.Direction.TOP));
            } else if ("MB".equals(c)) {
                tcs.addAndExecute(new MoveCommand(m_tank, Tile.Direction.BOTTOM));
            } else if ("MR".equals(c)) {
                tcs.addAndExecute(new MoveCommand(m_tank, Tile.Direction.RIGHT));
            } else if ("ML".equals(c)) {
                tcs.addAndExecute(new MoveCommand(m_tank, Tile.Direction.LEFT));
            }
        }
        return tcs;
    }

    public void write(TankCommandStack tcs, String path) throws FileNotFoundException, IOException {
        String commandStr = "";

        Iterator<ITankCommand> iter = tcs.getIterator();
        while (iter.hasNext()) {
            ITankCommand itc = iter.next();

            if (itc instanceof MoveCommand) {
                switch (itc.getDirection()) {
                    case BOTTOM:
                        commandStr += "MB"; 
                        break;
                    case TOP:
                        commandStr += "MT";
                        break;
                    case RIGHT:
                        commandStr += "MR";
                        break;
                    case LEFT:
                        commandStr += "ML";
                        break;
                }
            } else if (itc instanceof AttackCommand) {
                switch (itc.getDirection()) {
                    case BOTTOM:
                        commandStr += "AB";
                        break;
                    case TOP:
                        commandStr += "AT";
                        break;
                    case RIGHT:
                        commandStr += "AR";
                        break;
                    case LEFT:
                        commandStr += "AL";
                        break;
                }
            } else {
                throw new RuntimeException("Unhandled tank command!");
            }
            commandStr += " ";
        }
        String fullpath = Paths.get(path).toString();
        File file = new File(fullpath);
        file.getParentFile().mkdirs();
        file.createNewFile();
        try (PrintWriter out = new PrintWriter(fullpath)) {
            out.println(commandStr);
        }
    }
}
