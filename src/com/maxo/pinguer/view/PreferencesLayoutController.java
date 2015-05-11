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



	public void initialize( URL location, ResourceBundle resources )
	{
		/*  TODO: VER COMO GADORCHA PASARLE ATRIBUTOS A ESTO TALES COMO mainApp y preferencesLayoutStage  */


		textFieldSheet.setText( "CCTV" );
		textFieldColLocation.setText( "Ubicación" );
		textFieldColIP.setText( "IP" );

/*
		try
		{
			System.out.println( mainApp.getDevicesFile().getAttributes() );
			List<String> attribs = new ArrayList<String>();
			
			DevicesFile devFile = mainApp.getDevicesFile();
			attribs = (ArrayList<String>) devFile.getAttributes();
			
			textFieldSheet.setText( attribs.get( DevicesFile.SHEET ) );
			textFieldColLocation.setText( attribs.get( DevicesFile.LOCATION ) );
			textFieldColIP.setText( attribs.get( DevicesFile.IP ) );

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
		
		//System.out.println( mainApp.getDevicesFile().getAttributes() );
		try{
			DevicesFile devFile = mainApp.getDevicesFile();
			
			mainApp.getDevicesFile().setAttributes(
			//devFile.setAttributes(
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

	
}
