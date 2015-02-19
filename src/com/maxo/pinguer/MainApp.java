package com.maxo.pinguer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.maxo.pinguer.model.ReadDevices;
import com.maxo.pinguer.model.ObservableDevice;
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
	
	private ObservableList<ObservableDevice> devices = FXCollections.observableArrayList();
	
	private String nameFileSheet = "CCTV";
	private String nameColLocation = "Ubicación";
	private String nameColIP = "IP";
	
	public MainApp()
	{
		
		devices.addAll( new ObservableDevice("Calle 1", "192.168.200.30"), 
						new ObservableDevice("Calle 2", "192.168.200.31"),
						new ObservableDevice("Calle 3", "192.168.200.32"),
						new ObservableDevice("Calle 4", "192.168.200.50")			
					  );
		
	}
	
	public void loadDevicesFromXLS( File file ) throws IOException
	{

		ReadDevices redesXLS = new ReadDevices( file.getAbsolutePath() );
		
		redesXLS.setFileSheet( nameFileSheet );
		redesXLS.inputDevicesColumns( nameColLocation, nameColIP );
		ArrayList<ObservableDevice> cameras = new ArrayList<ObservableDevice>( redesXLS.readXLSFile() );

		devices.clear();
		devices.addAll(cameras);
		
	}
	
	public void setFileSheet( String fileSheet )
	{
		this.nameFileSheet = fileSheet;
	}
	
	public void setColumnIP( String colIP) 
	{
		this.nameColIP = colIP;		
	}
	
	public void setColumnLocation( String colLocation ) 
	{
		this.nameColLocation = colLocation;
	}
	
	public ObservableList<ObservableDevice> getDevices() 
	{
		return devices;
	}
	
	
	@Override
	public void start(Stage primaryStage) 
	{
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Pinguer");
		Image icon = new Image( getClass().getResourceAsStream("Pinguer.jpg") );
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
			controller.setMainApp(this);
			
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
			loader.setLocation( MainApp.class.getResource("view/DeviceOverview2.fxml") );
			AnchorPane deviceOverview = (AnchorPane) loader.load();
			
			rootLayout.setCenter(deviceOverview);
			
			DeviceOverviewController controller = loader.getController();
			controller.setMainApp(this);
			
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
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











