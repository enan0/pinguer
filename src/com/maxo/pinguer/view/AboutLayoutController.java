package com.maxo.pinguer.view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class AboutLayoutController
{
	private RootLayoutController mainWindow;
	
	private Stage aboutLayoutStage;
	
	@FXML
	private ImageView background;
	
	@FXML
	private Button exit;
	
	@FXML
	private void handleExit( )
	{
		aboutLayoutStage.close();
	}

	public void setStage(Stage stage) 
	{
		this.aboutLayoutStage = stage;		
	}
	
    public void setMainWindow(RootLayoutController mainWindow)
    {
        this.mainWindow = mainWindow;
    }
    
    public void setBackground()
    {
		Image backgroundImage = new Image( getClass().getResourceAsStream("Duende_fumon.png") );
		//System.out.println( getClass().getResourceAsStream("Duende_fumon.png") );
    	background.setImage( backgroundImage ); 	
    }
}
