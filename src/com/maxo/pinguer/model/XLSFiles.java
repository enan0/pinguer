package com.maxo.pinguer.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.prefs.Preferences;

import com.maxo.pinguer.model.ReadDevices;
import com.maxo.pinguer.MainApp;

public class XLSFiles 
{
	private static String nameFileSheet = "CCTV";
	private static String nameColLocation = "Ubicación";
	private static String nameColIP = "IP";
	
	private static MainApp mainApp;


	public static void loadDevicesFromXLS( File file ) throws IOException
	{

		ReadDevices redesXLS = new ReadDevices( file.getAbsolutePath() );
		
		redesXLS.setFileSheet( nameFileSheet );
		redesXLS.inputDevicesColumns( nameColLocation, nameColIP );
		ArrayList<ObservableDevice> cameras = new ArrayList<ObservableDevice>( redesXLS.readXLSFile() );

		setXLSFilePath(file);
		
		//System.out.println(cameras.size());
		
		mainApp.getDevices().clear();
		mainApp.getDevices().addAll(cameras);
		
	}
	
	public static File getXLSFilePath()
	{
		Preferences prefs = Preferences.userNodeForPackage( XLSFiles.class); //mainApp.getClass() );
		String filePath = prefs.get("filePath", null);
		
		if ( filePath != null )
			return new File(filePath);
		else
			return null;
	}
	
	public static void setXLSFilePath( File file ) 
	{
		Preferences prefs = Preferences.userNodeForPackage( XLSFiles.class); //mainApp.getClass() );
		if ( file != null )
			prefs.put("filePath", file.getPath() );
		else
			prefs.remove("filePath");
		
	}
	
	
	public static void setXLSDetails( String fileSheet, String columnIP, String columnLocation) 
	{
		nameFileSheet = fileSheet;
		nameColIP = columnIP;
		nameColLocation = columnLocation;
	}
	
	public static ArrayList<String> getXLSDetails( ) 
	{
		ArrayList<String> details = new ArrayList<String>();
		
		details.add( nameFileSheet );
		details.add( nameColIP );
		details.add( nameColLocation );
		
		return details; 
		
	}
	
	public static void setMainApp( MainApp _mainApp )
	{
		mainApp = _mainApp;
		
	}
}
