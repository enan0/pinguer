package com.maxo.pinguer.view;

import java.io.File;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import com.maxo.pinguer.MainApp;

public class RootLayoutController 
{
	private MainApp mainApp;
	
    public void setMainApp(MainApp mainApp) 
    {
        this.mainApp = mainApp;
    }
	
    @FXML
    private void handleOpen()
    {
    	FileChooser fileChooser = new FileChooser();
    	
    	FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XLS files (*.xls)", "*.xls");
    	fileChooser.getExtensionFilters().add(extFilter);
    	
    	File file = fileChooser.showOpenDialog( mainApp.getPrimaryStage() );
    	
    	if (file != null)
    	{
    		try
    		{
    			mainApp.loadDevicesFromXLS(file);
    		}
    		catch (Exception e)
    		{
    			System.err.println("File not found.");
    		}
    	}
    	
    }
    
    @FXML
    private void handleAbout() throws IOException
    {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("AboutLayout2.fxml"));
        AnchorPane aboutWindow = (AnchorPane)loader.load();
        //aboutWindow.setStyle("-fx-background-image: url('file://Duende_fumon.png');");
        
        AboutLayoutController controller = loader.getController();
        controller.setMainWindow(this);
        controller.setBackground();
        
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);  
        
        controller.setStage(stage);
        
        Scene scene = new Scene(aboutWindow);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("About");
        
        stage.show(); 
    	
    }
    
    @FXML
    private void handlePreferences() throws IOException
    {
       
    	 FXMLLoader loader = new FXMLLoader(getClass().getResource("PreferencesLayout.fxml"));
         AnchorPane preferencesWindow = (AnchorPane)loader.load();
         
         PreferencesLayoutController controller = loader.getController();
         controller.setMainWindow(this);
         controller.setMainApp(mainApp);
         
         Stage stage = new Stage();
         stage.initModality(Modality.APPLICATION_MODAL);
         
         controller.setStage(stage);
         
         Scene scene = new Scene(preferencesWindow);
         stage.setScene(scene);
         stage.setResizable(false);
         stage.setTitle("Preferences");
         stage.show(); 
    	
    	
    }
    
    @FXML
    private void handleExit()
    {
    	System.exit(0);
    }
    
}
