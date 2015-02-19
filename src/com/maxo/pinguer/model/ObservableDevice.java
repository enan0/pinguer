package com.maxo.pinguer.model;


import java.io.IOException;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import com.maxo.pinguer.model.IPv4;

public class ObservableDevice extends IPv4
{
	private final StringProperty location;
	private final StringProperty ip;
	private final BooleanProperty alive;

	public ObservableDevice( String location, String ip )
	{
		this.location = new SimpleStringProperty(location);
		this.ip = new SimpleStringProperty(ip);
		this.alive = new SimpleBooleanProperty(false);
	}
		
	public StringProperty getLocation()
	{
		return this.location;
	}
	
	public StringProperty getIP()
	{
		return this.ip;
	}
	
	/*
	public StringProperty isAlive() throws IOException, InterruptedException
	{	
		try
		{
			return new SimpleStringProperty( String.valueOf( IPv4.pingIP( this.ip.get() ) ) );
		}
		catch (Exception e)
		{
			//e.printStackTrace();
            return new SimpleStringProperty( "false" );
		}
	
	}
	*/
	
	public void isAlive() throws IOException, InterruptedException
	{
		this.alive.setValue( IPv4.pingIP( this.ip.get() ) );	
	}

	public BooleanProperty getAlive() 
	{
		return this.alive;
	}
	
}





