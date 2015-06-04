package com.maxo.pinguer.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.maxo.pinguer.MainApp;
import com.maxo.pinguer.model.DevicesFile;
import com.sun.xml.internal.ws.org.objectweb.asm.Label;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;

public class PreferencesLayoutController implements Initializable
{
	//private RootLayoutController mainWindow;
	
	private MainApp mainApp;
	
	private Stage preferencesLayoutStage;
	
	@FXML
	private TextField textFieldSheet;
	
	@FXML
	private TextField textFieldColumnLocation;
	
	@FXML
	private TextField textFieldColumnIP;
	
	@FXML
	private	TextField textFieldOutputFile;
	
	@FXML
	private CheckBox checkBoxDefault;
	
	@FXML
	private CheckBox checkBoxAliveDevices;
	
	@FXML
	private Button btnApply;

	
	@FXML
	private void defaultPreferences()
	{
		if ( checkBoxDefault.isSelected() )
		{
			textFieldSheet.setText("CCTV");
			textFieldColumnLocation.setText("Ubicación");
			textFieldColumnIP.setText("IP");
		}
	}

	
	private boolean areDefaultPreferences( )
	{
		if ( textFieldSheet.getText() != "CCTV" )
			return false;
		else if ( textFieldColumnLocation.getText() != "Ubicación" )
				return false;
			else if ( textFieldColumnIP.getText() != "IP" )
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
			//System.out.println( devicesAttribs.getAttributes( ) );
			
			List<String> attribs = new ArrayList<String>();
			
			attribs = (ArrayList<String>) devicesAttribs.getAttributes( );
			
			textFieldSheet.setText( attribs.get( DevicesFile.SHEET ) );
			textFieldColumnLocation.setText( attribs.get( DevicesFile.LOCATION ) );
			textFieldColumnIP.setText( attribs.get( DevicesFile.IP ) );
			
			/* TODO: SIEMPRE inicializan las defaults en FALSE */
			if ( areDefaultPreferences() )
				checkBoxDefault.setSelected(true);
			else
				checkBoxDefault.setSelected(false);
		}
		catch(Exception e)
		{
			/* TODO: VER XQ ROMPE (el Layout) AL NO HABER ABIERTO UN ARCHIVO ANTES */
			System.err.println( "Primero abra un archivo." );
			System.out.println( preferencesLayoutStage );
			//preferencesLayoutStage.close();
		}
	}

	
	@FXML
	private void handleTextFieldChange()
	{
		checkBoxDefault.setSelected( false );
		
	}
	
	
	private void applyLoadFile( )
	{
		try{
			mainApp.getDevicesFile().setAttributes(
					textFieldSheet.getText(),
					textFieldColumnIP.getText(),
					textFieldColumnLocation.getText()				
					);
			
			mainApp.getDevices().clear();
			mainApp.getDevices().addAll( mainApp.getDevicesFile().loadDevices() );
	
		}
		catch (Exception e)
		{
			System.err.println("Primero abra un archivo.");
		}
	}
	
	
	private void applyExportFile( )
	{
		String outputFile = new String( textFieldOutputFile.getText() );
		mainApp.getDevicesFile().modifyExportFileName(outputFile);
	
	}
	
	@FXML
	private void handleApply(  )
	{
		try{
			mainApp.getDevicesFile().setAttributes(
					textFieldSheet.getText(),
					textFieldColumnIP.getText(),
					textFieldColumnLocation.getText()				
					);
						
			mainApp.getDevices().clear();
			mainApp.getDevices().addAll( mainApp.getDevicesFile().loadDevices() );
	
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
	
	/*
    public void setMainWindow(RootLayoutController mainWindow)
    {
        this.mainWindow = mainWindow;
    }
	 */
	
}
