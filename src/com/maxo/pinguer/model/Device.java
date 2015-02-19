package com.maxo.pinguer.model;


import java.io.IOException;

import com.maxo.pinguer.model.IPv4;

public class Device extends IPv4
{
	private String location;
	private String ip;

	public Device( String location, String ip )
	{
		this.location = location;
		this.ip = ip;
	}
		
	public String getLocation()
	{
		return this.location;
	}
	
	public String getIP()
	{
		return this.ip;
	}
	
	public boolean isAlive() throws IOException, InterruptedException
	{
		return IPv4.pingIP( this.ip );
	}

}
