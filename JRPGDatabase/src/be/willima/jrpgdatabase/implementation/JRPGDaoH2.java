/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.willima.jrpgdatabase.implementation;

import be.willima.jrpgdatabase.JRPGDao;
import be.willima.jrpgdatabase.model.JRPGProject;
import be.willima.jrpgdatabase.model.JRPGTile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.h2.engine.Constants;

public class JRPGDaoH2 implements JRPGDao {

    private final ResourceBundle sqlRB;

    // Folder names //
    private final String folderGraphics = "Graphics";
    //// In Graphics folder ////
    private final String folderTiles = "Tiles";
    private final String folderAnimations = "Animations";
    private final String folderCharacters = "Characters";
    private final String folderSystemGraphics = "System";
    private final String folderEnvironment = "Environment";
    //// End of in Graphics folder ////
    private final String folderAudio = "Audio";
    //// In Audio folder ////
    private final String folderBackgroundAudio = "Background";
    private final String folderAudioEffects = "Effects";
    //// End of in Audio folder ////
    private final String folderFonts = "Fonts";
    private final String folderData = "Data";
    private final String folderScripts = "Scripts";
    private final String folderTemp = "Temp";
    // End of folder names

    // Constants
    private final String connectionStringBase = "jdbc:h2:";
    private final String databaseExtension = ".jrpg";
    private final String databaseName = "game" + databaseExtension;
    private final String tempDatabaseName = folderTemp + File.separator + databaseName;
    private final String connectionStringOptionExists = ";IFEXISTS=TRUE";
    private final String connectionStringOptions = ";MV_STORE=TRUE";
    private final String username = "sa";
    private final String password = "sa";

    // The active project fields
    private JRPGProject activeProject = null;
    private File activeProjectFolder = null;
    // End of the active project fields

    public JRPGDaoH2() throws ClassNotFoundException {
        Class.forName("org.h2.Driver");
        sqlRB = ResourceBundle.getBundle("be.willima.jrpgdatabase.implementation.h2sql");
    }

    @Override
    public String getInfo() {
        return "This is the h2database DAO, used for the application.";
    }

    @Override
    public DaoError createNewProject(String projectFolderURI, String projectTitle, String gameTitle) {
        // TODO get folder structure from somewhere else? final String's?
        initProjectFolder(projectFolderURI);
        if (activeProjectFolder != null) {
            activeProject = new JRPGProject(this, projectTitle, gameTitle, 8, 4, null);
            initProjectDatabase();
            return DaoError.NO_ERROR;
        } else {
            return DaoError.CREATE_ERROR;
        }
    }

    @Override
    public DaoError loadProjectByProjectFileURI(String projectFileURI) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DaoError loadProjectByProjectFolderURI(String projectFolderURI) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public JRPGTile getTile(int tileID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DaoError saveActiveProject() {
        if (activeProjectFolder != null) {
            System.out.println("Saving ...");
            File tempDbFile = new File(activeProjectFolder, tempDatabaseName + Constants.SUFFIX_MV_FILE);
            File dbFile = new File(activeProjectFolder, databaseName);
            try {
                Files.copy(tempDbFile.toPath(), dbFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
                Logger.getLogger(JRPGDaoH2.class.getName()).log(Level.SEVERE, null, ex);
            }
            return DaoError.NO_ERROR;
        } else {
            return DaoError.NO_SAVE;
        }
    }

    @Override
    public JRPGProject getActiveProject() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DaoError closeActiveProject() {
        if (activeProject != null) {
            File tempFolder = new File(activeProjectFolder, folderTemp);
            clearDirectory(tempFolder);
            // TODO ...
        }
        return DaoError.NO_ERROR;
    }

    private void clearDirectory(File directory) {
        if (directory.exists() && directory.isDirectory()) {
            for (File dir : directory.listFiles()) {
                clearDirectory(dir);
                dir.delete();
            }
        }
    }

    private void initProjectFolder(String projectFolderURI) {
        File projectFolder = new File(projectFolderURI);
        if (projectFolder.mkdir()) {
            System.out.println("Project folder was created succesfully!");
            System.out.println("Creating subfolders ...");
            // TODO create subfolders
            File graphicsFolder = new File(projectFolder, folderGraphics);
            if (graphicsFolder.mkdir()) {
                File tilesFolder = new File(graphicsFolder, folderTiles);
                tilesFolder.mkdir();
                File animationsFolder = new File(graphicsFolder, folderAnimations);
                animationsFolder.mkdir();
                File charactersFolder = new File(graphicsFolder, folderCharacters);
                charactersFolder.mkdir();
                File systemFolder = new File(graphicsFolder, folderSystemGraphics);
                systemFolder.mkdir();
                File environmentFolder = new File(graphicsFolder, folderEnvironment);
                environmentFolder.mkdir();
            }
            File audioFolder = new File(projectFolder, folderAudio);
            if (audioFolder.mkdir()) {
                File backgroundFolder = new File(audioFolder, folderBackgroundAudio);
                backgroundFolder.mkdir();
                File effectsFolder = new File(audioFolder, folderAudioEffects);
                effectsFolder.mkdir();
            }
            File fontsFolder = new File(projectFolder, folderFonts);
            if (fontsFolder.mkdir()) {
                // ...
            }
            File dataFolder = new File(projectFolder, folderData);
            if (dataFolder.mkdir()) {
                // ...
            }
            File scriptsFolder = new File(projectFolder, folderScripts);
            if (scriptsFolder.mkdir()) {
                // ...
            }
            activeProjectFolder = projectFolder;
        } else {
            System.out.println("Failed to create project folder!");
        }
    }

    private void initProjectDatabase() {
//        System.out.println(getConnectionString(projectFolderURI));
        createTables();
        initSettingsTable();
//        try {
//            Connection conn = getConnection();
//
//            conn.close();
//        } catch (SQLException ex) {
//            Logger.getLogger(JRPGDaoH2.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    private void createTables() {
        System.out.println("Creating tables ...");
        try (Connection conn = getConnection()) {
            try (Statement stm = conn.createStatement()) {
                stm.execute(sqlRB.getString("CREATE_TABLE_PROJECTSETTINGS"));
                stm.execute(sqlRB.getString("CREATE_TABLE_MAPS"));
                stm.execute(sqlRB.getString("CREATE_TABLE_SPRITES"));
                stm.execute(sqlRB.getString("CREATE_TABLE_TILES"));
            } catch (SQLException ex) {
                Logger.getLogger(JRPGDaoH2.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(JRPGDaoH2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initSettingsTable() {
        try (Connection conn = getConnection()) {
            try (PreparedStatement pstm = conn.prepareStatement(sqlRB.getString("INSERT_PROJECTSETTING"))) {
                pstm.setString(1, "GAME_TITLE");
                pstm.setString(2, activeProject.getGameTitle());
                pstm.execute();
                pstm.setString(1, "PROJECT_TITLE");
                pstm.setString(2, activeProject.getProjectTitle());
                pstm.execute();
            } catch (SQLException ex) {
                Logger.getLogger(JRPGDaoH2.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(JRPGDaoH2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    // TODO !!!
//    private boolean validateProjectDirectory(String projectDirectoryURI) {
//        return true;
//    }
    private Connection getConnection() throws SQLException {
        Connection conn;
        try {
            conn = DriverManager.getConnection(getConnectionString(false), username, password);
        } catch (SQLException ex) {
            System.out.println("Database doesn't exist yet, making a new one...");
            conn = DriverManager.getConnection(getConnectionString(true), username, password);
        }
        return conn;
    }

    private String getConnectionString(boolean isInit) {
        File temp = new File(activeProjectFolder, tempDatabaseName);
        String connStr = connectionStringBase + temp.getAbsolutePath() + (isInit ? "" : connectionStringOptionExists) + connectionStringOptions;
        System.out.println("getConnectionString: " + connStr);
        return connStr;
    }

}
