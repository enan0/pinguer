package com.maxo.pinguer.view;

import java.io.File;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import com.maxo.pinguer.MainApp;
import com.maxo.pinguer.model.DevicesFile;


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
  
    	FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("XLS files (*.xls)", "*.xls");
    	fileChooser.getExtensionFilters().add(extensionFilter);
    	    	
   		File filePath = mainApp.getFilePath();
    	if ( filePath != null )
    		fileChooser.setInitialDirectory( filePath.getParentFile() );
    	
    	File file = fileChooser.showOpenDialog( mainApp.getPrimaryStage() );
    	DevicesFile devicesFiles = new DevicesFile( file.getAbsolutePath() );
    		
    	
    	if (file != null)
    	{
    		try
    		{
    			mainApp.getDevices().clear();
    			mainApp.getDevices().addAll( devicesFiles.loadDevices( ) );
    			mainApp.setFilePath( file );
    			mainApp.setDevicesFile( devicesFiles );
    			System.out.println( mainApp.getDevicesFile().getAttributes() );

    		}
    		catch (Exception e)
    		{
    			System.err.println( "File not found." );
    		}
    	}
    	
    }
    
    

	
	@FXML
	private void handleExport(  )
	{
		/* TODO:
		 * 		Exportar los dispositivos en formato texto ó CSV.
		 * 		Tipo de dispositivo, Ubicación, IP, estado.
		 * 
		 * 		*Todos
		 * 		*Mal funcionamiento
		 * 		
		 *  */
				
	}
	
    
    @FXML
    private void handleAbout() throws IOException
    {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource( "AboutLayout.fxml" ) );
        AnchorPane aboutWindow = (AnchorPane)loader.load();
        
        AboutLayoutController controller = loader.getController();
        controller.setMainWindow(this);
        controller.setBackground();
        
        Stage stage = new Stage();
        stage.initModality( Modality.WINDOW_MODAL );  
        //stage.initStyle( StageStyle.UTILITY );
        
        controller.setStage(stage);
        
        Scene scene = new Scene( aboutWindow );
        stage.setScene( scene );
        stage.setResizable( false );
        stage.setTitle( "About" );
        
        stage.show(); 
    	
    }
    
    @FXML
    private void handlePreferences() throws IOException
    {
    	     	 
    	 FXMLLoader loader = new FXMLLoader(getClass().getResource( "PreferencesLayout.fxml" ) );
    	 AnchorPane preferencesWindow = (AnchorPane)loader.load();
         
         PreferencesLayoutController controller = loader.getController();
         controller.setMainWindow( this );
		 controller.setMainApp( mainApp );

         
         Stage stage = new Stage();
         stage.initModality( Modality.APPLICATION_MODAL );
         
         controller.setStage( stage );
                  
         Scene scene = new Scene( preferencesWindow );
         stage.setScene( scene );
         stage.setResizable( false );
         stage.setTitle( "Preferences" );
         stage.show(); 
    	
    	
    }
    
    @FXML
    private void handleClear()
    {
    	mainApp.getDevices().clear();
    }
    
    @FXML
    private void handleExit()
    {
    	System.exit(0);
    }
    
}
