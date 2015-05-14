package com.maxo.pinguer.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.maxo.pinguer.MainApp;
import com.maxo.pinguer.model.DevicesFile;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class PreferencesLayoutController implements Initializable
{

	private RootLayoutController mainWindow;
	
	private MainApp mainApp;
	
	private Stage preferencesLayoutStage;
	
	@FXML
	private	TextField textFieldOutputFile;
	
	@FXML
	private CheckBox checkBoxAliveDevices;
	
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

	private boolean isDefaultPreferences( )
	{
		if ( textFieldSheet.getText() != "CCTV" )
			return false;
		else if ( textFieldColLocation.getText() != "Ubicación" )
				return false;
			else if ( textFieldColIP.getText() != "IP" )
				return false;
			else
				return true;
		
	}

	public void initialize( URL location, ResourceBundle resources )
	{	
	}
	
	public void initPreferencesWindow( DevicesFile devicesAttribs ) 
	{
		try
		{
			System.out.println( devicesAttribs.getAttributes( ) );
			
			List<String> attribs = new ArrayList<String>();
			
			attribs = (ArrayList<String>) devicesAttribs.getAttributes( );
			
			textFieldSheet.setText( attribs.get( DevicesFile.SHEET ) );
			textFieldColLocation.setText( attribs.get( DevicesFile.LOCATION ) );
			textFieldColIP.setText( attribs.get( DevicesFile.IP ) );
			
			if ( isDefaultPreferences() )
				checkBoxDefault.setSelected(true);
			else
				checkBoxDefault.setSelected(false);

		}
		catch(Exception e)
		{
			/* TODO: VER XQ ROMPE (el Layout) AL NO HABER ABIERTO UN ARCHIVO ANTES */
			System.err.println( "Primero abra un archivo." );
			System.out.println( preferencesLayoutStage );
			preferencesLayoutStage.close();
		}
	
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
			mainApp.getDevicesFile().setAttributes(
					textFieldSheet.getText(),
					textFieldColIP.getText(),
					textFieldColLocation.getText()				
					);
			
			mainApp.getDevices().clear();
			mainApp.getDevices().addAll( mainApp.getDevicesFile().loadDevices() );
			
			/* TODO: REVISAR PORQUE ACÁ LOS DATOS SON CORRECTOS PERO AL SALIR SE REESTABLECEN LOS VALORES X DEFECTO  */
			System.out.println( mainApp.getDevicesFile().getAttributes() );
			
		}
		catch (Exception e)
		{
			System.err.println("Primero abra un archivo.");
		}
		
		preferencesLayoutStage.close();
		
	}
	
	
	public void setMainApp( MainApp mainApp )
	{
		this.mainApp = mainApp;
	}
	
	public void setStage(Stage stage) 
	{
		this.preferencesLayoutStage = stage;		
	}
	
    public void setMainWindow(RootLayoutController mainWindow)
    {
        this.mainWindow = mainWindow;
    }

	
}
