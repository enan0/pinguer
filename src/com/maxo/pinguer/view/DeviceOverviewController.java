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
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.paint.Color;


public class DeviceOverviewController 
{
	private MainApp mainApp;
	
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
	
	private static boolean isRefreshing;
	
	private Task<ObservableList<ObservableDevice>> task;
	

	public DeviceOverviewController()
	{
		task = createTask();

	}
	
	private void showDeviceDetails( ObservableDevice device )
	{
		if ( device != null  )
		{
			labelLocation.setText( device.getLocation().get() );
			labelIP.setText( device.getIP().get() );
			
			Boolean alive = device.getAlive().get();
			String status;
			if ( alive )
				status = "OK.";
			else
				status = "APAGADO.";
			labelStatus.setText( status );
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
		{	
			
			dev.isAlive();
			
			/* TODO: AUTOSCROLL AL ACTUALIZAR, en realidad resaltar por cual va */
			//deviceTable.scrollTo(dev);
		}

	}
	
	
	private Task<ObservableList<ObservableDevice>> createTask()
	{
		final Task<ObservableList<ObservableDevice>> task = new Task<ObservableList<ObservableDevice>>() 
				{
					 @Override 
					 protected ObservableList<ObservableDevice> call() throws InterruptedException, IOException
					 {
						 for ( ObservableDevice dev : mainApp.getDevices() )
							{	
							 	if ( isCancelled() )
							 	{
							 		updateMessage("Cancelado.");
							 		break;
							 	}
							 	
								dev.isAlive();
								System.out.println( dev.getIP() );
								
								/* TODO: AUTOSCROLL AL ACTUALIZAR, en realidad resaltar por cual va */
								//deviceTable.scrollTo(dev);
							}
						 return null;
					 }
				};

				task.stateProperty().addListener( new ChangeListener<Worker.State>() 
				{
					 @Override public void changed(ObservableValue<? extends Worker.State> observableValue, Worker.State oldState, Worker.State newState) 
					 {
						 
						if ( newState == Worker.State.RUNNING )
						{
							loadingInd.progressProperty().bind( task.progressProperty() );
							loadingInd.setVisible(true);
							btnRefresh.setText("Cancel");
						}
						 
						if ( newState == Worker.State.READY )
						{
							loadingInd.setVisible(false);
							isRefreshing = false;
							btnRefresh.setText("Refresh");				 
						}
					
						if ( newState == Worker.State.CANCELLED )
						{
							 loadingInd.setVisible(false);
							 isRefreshing = false;
							 btnRefresh.setText("Refresh");
						}
					 }
				});
		
		return task;
		
	}
	
	@FXML
	private void handleRefresh()
	{
		/* TODO: CANCELA PERO NO SE PUEDE VOLVER A CORRER EL TASK */
		
		if ( mainApp.getDevices().isEmpty() )
			return;
		else
		{
			if ( task.isRunning() )
			{
				System.out.println( "por cancelar" );
				task.cancel( true );
			}
			else
			{	
				//new Thread(task).start();
				Thread th = new Thread( task );
				th.start();
				
				
			}	
		}

	}

	
	public void showDevicesLength(  ) 
	{

		if ( ! mainApp.getDevices().isEmpty() )
		{
			labelCantDevices.setText("Cant. " + mainApp.getDevices().size() );
		}
		
		System.out.println( mainApp.getDevices().size() );

		System.out.println( columnLocation.getTableView().getItems().size() );

		
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
			
		
		loadingInd.setVisible(false);
		
		deviceTable.getSelectionModel().selectedItemProperty().addListener( (observable, oldValue, newValue)
				-> showDeviceDetails(newValue) );

		
	}
	
	
    public void changelistener(final TableColumn<ObservableDevice, String> listerColumn) 
    {
    	System.out.println( listerColumn.getColumns().size() );
    	/*
        listerColumn.widthProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                System.out.print(listerColumn.getText() + "  ");
                System.out.println(t1);
            }
        });
        */
    }
	
	
	public void setMainApp( MainApp mainApp )
	{
		this.mainApp = mainApp;
		
		deviceTable.setItems( mainApp.getDevices() );
		
		deviceTable.setPlaceholder( new Label( "Developed by Maxo \n\n  File  > Load File" ) );
		
		/* TODO: REVISAR EL TEMA DEL BACKGROUND PARA LA LISTA VACIA
		Image image = new Image( getClass().getResourceAsStream("Duende_fumon.png") );
		BackgroundImage backgroundImage = new BackgroundImage( image, null, null, null, null);
		Background backG = new Background( backgroundImage );
		deviceTable.setBackground( backG );
		*/
	}
	
}
