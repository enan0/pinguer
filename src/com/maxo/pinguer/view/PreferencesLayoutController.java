package com.maxo.pinguer.view;

import java.util.ArrayList;

import com.maxo.pinguer.MainApp;
import com.maxo.pinguer.model.ReadDevices;

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
	private TextField textFieldSheet;
	
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
			textFieldSheet.setText("CCTV");
			
			textFieldColLocation.setText("Ubicación");
			
			textFieldColIP.setText("IP");
		}

	}

	
	@FXML
	private void initialize( )
	{

		ArrayList<String> attribs = new ArrayList<String>();
		
		attribs = (ArrayList<String>) ReadDevices.getXLSAttributes();

		textFieldSheet.setText( attribs.get( ReadDevices.SHEET ) );
		textFieldColLocation.setText( attribs.get( ReadDevices.LOCATION ) );
		textFieldColIP.setText( attribs.get( ReadDevices.IP ) );
		
		System.out.println( attribs.get( ReadDevices.SHEET ) );
		System.out.println( attribs.get( ReadDevices.IP ) );
		System.out.println( attribs.get( ReadDevices.LOCATION ) );
		
	}

	
	@FXML
	private void handleTextFieldChange()
	{
		checkBoxDefault.setSelected(false);
		
	}
	
	@FXML
	private void handleApply(  )
	{
		ReadDevices.setXLSAttributes(
				textFieldSheet.getText(), 
				textFieldColLocation.getText(), 
				textFieldColIP.getText()
				);
		
		/*
		ArrayList<String> details = new ArrayList<String>();
		details = (ArrayList<String>) XLSFiles.getXLSDetails();
		
		System.out.println( details.get(0) );
		System.out.println( details.get(1) );
		System.out.println( details.get(2) );
		*/
		
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
