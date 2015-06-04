package com.maxo.pinguer;

import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

import com.maxo.pinguer.model.ObservableDevice;
import com.maxo.pinguer.model.DevicesFile;
import com.maxo.pinguer.view.DeviceOverviewController;
import com.maxo.pinguer.view.RootLayoutController;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application 
{

	private Stage primaryStage;
	private BorderPane rootLayout;
	
	private ObservableList<ObservableDevice> devices = FXCollections.observableArrayList( );
	private DevicesFile devicesFile;
	
	public MainApp()
	{/*
		devices.addAll(
				new ObservableDevice("Calle 1", "10.1.1.1"),
				new ObservableDevice("Calle 2", "10.1.1.2"),
				new ObservableDevice("Calle 3", "10.1.1.3")			
				);	
	 */
	}
	
	
	public ObservableList<ObservableDevice> getDevices() 
	{
		return devices;
	}
	
	
	public void setDevicesFile( DevicesFile file ) 
	{
		this.devicesFile = file;
	}
	
	
	public DevicesFile getDevicesFile( ) 
	{
		return devicesFile;
	}	
	
	@Override
	public void start(Stage primaryStage) 
	{
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle( "Pinguer" );
		Image icon = new Image( getClass().getResourceAsStream( "Pinguer.jpg" ) );
		this.primaryStage.getIcons().add(icon);
		
		initRootLayout();
		
		showDeviceOverview();
	}

	public void initRootLayout()
	{

		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation( MainApp.class.getResource("view/RootLayout.fxml") );
			rootLayout = (BorderPane) loader.load();
			
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			
			RootLayoutController controller = loader.getController();
			controller.setMainApp( this );
			
			primaryStage.show();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
			
	}
	
	public void showDeviceOverview()
	{
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation( MainApp.class.getResource( "view/DeviceOverview.fxml" ) );
			AnchorPane deviceOverview = (AnchorPane) loader.load();
			
			rootLayout.setCenter(deviceOverview);
			
			DeviceOverviewController controller = loader.getController();
			controller.setMainApp( this );
			
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}
	
	
	public void setFilePath( File file ) 
	{
		Preferences prefs = Preferences.userNodeForPackage( DevicesFile.class);
		if ( file != null )
			prefs.put("filePath", file.getPath() );
		else
			prefs.remove("filePath");	
	}
	
	
	public File getFilePath()
	{
		Preferences prefs = Preferences.userNodeForPackage( DevicesFile.class);
		String filePath = prefs.get("filePath", null);
		
		if ( filePath != null )
			return new File(filePath);
		else
			return null;
	}
	
	
	public Stage getPrimaryStage()
	{
		return primaryStage;
	}
	
	public static void main(String[] args) 
	{
		launch(args);
	}
}

