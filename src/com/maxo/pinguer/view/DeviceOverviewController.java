package com.maxo.pinguer.view;

import java.io.IOException;

import com.maxo.pinguer.MainApp;
import com.maxo.pinguer.model.ObservableDevice;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.paint.Color;


public class DeviceOverviewController 
{
	@FXML
	private TableView<ObservableDevice> deviceTable;
	
	@FXML
	private TableColumn<ObservableDevice, String> columnLocation;
	
	@FXML
	private TableColumn<ObservableDevice, String> columnIP;
	
	@FXML
	private TableColumn<ObservableDevice, Boolean> columnStatus;
	
	@FXML
	private Label labelCantDevices;
	
	@FXML
	private Label labelLocation;
	
	@FXML
	private Label labelIP;
	
	@FXML
	private Label labelStatus;
	
	@FXML
	private Button btnRefresh;
	
	@FXML
	private ProgressBar loadingBar;
	
	@FXML
	private ProgressIndicator loadingInd;
	
	private MainApp mainApp;
	private static boolean isRefreshing;
	

	public DeviceOverviewController()
	{
		
	}
	
	private void showDeviceDetails( ObservableDevice device )
	{
		if ( device != null  )
		{
			labelLocation.setText( device.getLocation().get() );
			labelIP.setText( device.getIP().get() );
			Boolean alive = device.getAlive().get();
			labelStatus.setText( String.valueOf( alive ) );
		}
		else
		{
			labelLocation.setText( "Location:" );
			labelIP.setText( "IP:" );
			labelStatus.setText( "Status." );
		}
		
	}

	
	private void refresh() throws IOException, InterruptedException
	{
		for ( ObservableDevice dev : mainApp.getDevices() )
			dev.isAlive();
	}
	
	@FXML
	private void handleRefresh()
	{

		if ( ! isRefreshing )
			isRefreshing = true;
		else
			return;
					
		loadingInd.setVisible(true);
		//btnRefresh.setText("Cancel");
		
		 final Task<ObservableList<ObservableDevice>> task = new Task<ObservableList<ObservableDevice>>() 
		 {
			 @Override 
			 protected ObservableList<ObservableDevice> call() throws InterruptedException, IOException
			 {
				 refresh();
				 return null;
			 }
		 };
		 
		 loadingInd.progressProperty().bind( task.progressProperty() );
		 
		 task.stateProperty().addListener( new ChangeListener<Worker.State>() {
			 @Override public void changed(ObservableValue<? extends Worker.State> observableValue, Worker.State oldState, Worker.State newState) 
			 {
				 if ( newState == Worker.State.SUCCEEDED ) 
				 {
					 loadingInd.setVisible(false);
					 isRefreshing = false;
					 //btnRefresh.setText("Refresh");
					 
				 }
			 }
		});
		 

		 new Thread(task).start();
	}

	
	private void showDevicesLength() 
	{
		//Integer cantDevices = mainApp.getDevices().size();
		//ObservableDevice dev = mainApp.getDevices().get(0);
		//labelCantDevices.setText( String.valueOf(cantDevices) );
		//labelCantDevices.setText( String.valueOf(cantDevices) );
		//System.out.println( cantDevices );
	}
	
	@FXML
	public void initialize( ) throws IOException, InterruptedException
	{
		
		columnLocation.setCellValueFactory(cellData -> cellData.getValue().getLocation());
		columnIP.setCellValueFactory(cellData -> cellData.getValue().getIP());
		columnStatus.setCellValueFactory(cellData -> cellData.getValue().getAlive() );
		
		columnStatus.setCellFactory( column -> 
			{ return new TableCell<ObservableDevice, Boolean>()
					{ 	@Override
						protected void updateItem(Boolean item, boolean empty)
						{
							super.updateItem(item, empty);
							
							if (item == null || empty)
							{
								setText(null);
								setStyle("");
							}
							else
							{
								if ( item == true )
								{
									setText("OK");
									setTextFill(Color.BLACK);
									setStyle("-fx-background-color: green");
								}
								else
								{
									setText("APAGADO");
									setTextFill(Color.BLACK);
									setStyle("-fx-background-color: red");
								}
							}
						}
				
					};
		});
		
		//showDevicesLength();
		
		loadingInd.setVisible(false);
		
		deviceTable.getSelectionModel().selectedItemProperty().addListener( (observable, oldValue, newValue)
				-> showDeviceDetails(newValue) );
				
	}
	
	public void setMainApp( MainApp mainApp )
	{
		this.mainApp = mainApp;
		
		deviceTable.setItems( mainApp.getDevices() );
	}
	
}
