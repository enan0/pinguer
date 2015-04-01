package com.maxo.pinguer.view;

import java.util.ArrayList;
import java.util.List;

import com.maxo.pinguer.MainApp;
import com.maxo.pinguer.model.DevicesFile;

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

		// List<String> attribs = new ArrayList<String>();
		System.out.println("I'm in");

/*		
		try
		{
			//DevicesFile devFile = mainApp.getDevicesFile();
			attribs = (ArrayList<String>) mainApp.getDevicesFile().getAttributes();
			
			textFieldSheet.setText( attribs.get( DevicesFile.SHEET ) );
			textFieldColLocation.setText( attribs.get( DevicesFile.LOCATION ) );
			textFieldColIP.setText( attribs.get( DevicesFile.IP ) );
			
			System.out.println( attribs.get( DevicesFile.SHEET ) );
			System.out.println( attribs.get( DevicesFile.IP ) );
			System.out.println( attribs.get( DevicesFile.LOCATION ) );
		}
		catch(Exception e)
		{
			System.err.println("Primero abra un archivo.");
			System.out.println(preferencesLayoutStage);
			preferencesLayoutStage.close();
		}
*/			
	}

	
	@FXML
	private void handleTextFieldChange()
	{
		checkBoxDefault.setSelected( false );
		
	}
	
	@FXML
	private void handleApply(  )
	{
		
		try{
			DevicesFile devFile = mainApp.getDevicesFile();
			
			//mainApp.getDevicesFile().setAttributes(
			devFile.setAttributes(
					textFieldSheet.getText(),
					textFieldColIP.getText(),
					textFieldColLocation.getText()				
					);
			
			
			ArrayList<String> attribs = new ArrayList<String>();
			attribs = (ArrayList<String>) mainApp.getDevicesFile().getAttributes();
			
			System.out.println( attribs.get(0) );
			System.out.println( attribs.get(1) );
			System.out.println( attribs.get(2) );
		}
		catch (Exception e)
		{
			System.err.println("Primero abra un archivo.");
		}
		
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
