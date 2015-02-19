package com.maxo.pinguer.view;

import com.maxo.pinguer.MainApp;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class PreferencesLayoutController 
{

	private RootLayoutController mainWindow;
	
	private MainApp mainApp;
	
	private Stage preferencesLayoutStage;
	
	@FXML
	private TextField textFieldsheet;
	
	@FXML
	private TextField textFieldColLocation;
	
	@FXML
	private TextField textFieldColIP;
	
	@FXML
	private CheckBox checkBoxDefault;
	
	@FXML
	private Button btnApply;
	
    public void setMainWindow(RootLayoutController mainWindow)
    {
        this.mainWindow = mainWindow;
    }
	
	@FXML
	private void defaultPreferences()
	{
		if ( checkBoxDefault.isSelected() )
		{
			textFieldsheet.setText("CCTV");
			
			textFieldColLocation.setText("Ubicación");
			
			textFieldColIP.setText("IP");
		}
	}

	@FXML
	private void handleTextFieldChange()
	{
		checkBoxDefault.setSelected(false);
		
	}
	
	@FXML
	private void handleApply()
	{
		
		mainApp.setFileSheet(textFieldsheet.getText());
		mainApp.setColumnLocation(textFieldColLocation.getText());
		mainApp.setColumnIP(textFieldColIP.getText());
		
		
		System.out.println( textFieldsheet.getText() );
		System.out.println( textFieldColLocation.getText() );
		System.out.println( textFieldColIP.getText() );
		
		preferencesLayoutStage.close();
		
	}
	
	public void setMainApp(MainApp mainApp)
	{
		this.mainApp = mainApp;
	}
	
	public void setStage(Stage stage) 
	{
		this.preferencesLayoutStage = stage;		
	}
	
}
