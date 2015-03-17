package com.maxo.pinguer.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.prefs.Preferences;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;

public class ReadDevices 
{
	public static final int SHEET = 0;
	public static final int IP = 1;
	public static final int LOCATION = 2;
	
	private static String nameFileSheet = "CCTV";
	private static String nameColLocation = "Ubicación";
	private static String nameColIP = "IP";
	
	private static String inputFile;

	/*
	public XLSFiles( )
	{
		this.nameFileSheet = "CCTV";
		this.nameColLocation = "Ubicación";
		this.nameColIP = "IP";
	}
	*/
	
	public static void setInputFile( String _inputFile ) 
	{
		inputFile = _inputFile;
	}
	
	
	public static Collection<ObservableDevice> loadDevicesFromXLS( ) throws IOException
	{
		ArrayList<ObservableDevice> devices = new ArrayList<ObservableDevice>( readXLSFile() );
		
		File file = new File( inputFile );
		setXLSFilePath( file );
		
		System.out.println( devices.size( ) );
				
		return devices;
	}
	
	
	public static File getXLSFilePath()
	{
		Preferences prefs = Preferences.userNodeForPackage( ReadDevices.class);
		String filePath = prefs.get("filePath", null);
		
		if ( filePath != null )
			return new File(filePath);
		else
			return null;
	}
	
	
	public static void setXLSFilePath( File file ) 
	{
		Preferences prefs = Preferences.userNodeForPackage( ReadDevices.class);
		if ( file != null )
			prefs.put("filePath", file.getPath() );
		else
			prefs.remove("filePath");
		
	}
	
	
	public static void setXLSAttributes( String fileSheet, String columnIP, String columnLocation ) 
	{
		nameFileSheet = fileSheet;
		nameColIP = columnIP;
		nameColLocation = columnLocation;
	}
	
	
	public static Collection<String> getXLSAttributes()
	{
		Collection<String> details = new ArrayList<String>();
		
		details.add( nameFileSheet );
		details.add( nameColIP );
		details.add( nameColLocation );
		
		return details; 
		
	}
	
	
	private  static ArrayList<ObservableDevice> readXLSFile( ) throws IOException  
	{
		ArrayList<ObservableDevice> devices = new ArrayList<>();  
		  
	    File inputWorkbook = new File( inputFile );
	    Workbook w;
	    
		//Establezco el encoding del workbook para que me tome correctamente las tildes :D
		WorkbookSettings wSettings = new WorkbookSettings();
		wSettings.setEncoding( "iso-8859-1" );
		
		try 
		{
		  w = Workbook.getWorkbook( inputWorkbook, wSettings );
		  // Busco la Hoja CCTV o Carteles
		  Sheet sheetDevices = w.getSheet( nameFileSheet );
		
		  // Busco la Celda con el IP
		  Cell cellIP = sheetDevices.findCell( nameColIP );
		  int colIP = cellIP.getColumn();
		  Cell[] columnIP = sheetDevices.getColumn(colIP);
		
		  // Busco la Celda con la Ubicacion
		  Cell cellUbic = sheetDevices.findCell( nameColLocation );
		  int colUbic = cellUbic.getColumn();
		  Cell[] columnUbic = sheetDevices.getColumn(colUbic);
		  
		  // Guardo los datos leídos en una lista
		      for ( int i=0; i<columnIP.length; i++ )
		      {
		    	  if ( CellType.EMPTY != columnUbic[i].getType() & CellType.EMPTY != columnIP[i].getType() )
		    	  {
		    		  String location = columnUbic[i].getContents();
		    		  String ip = columnIP[i].getContents();
		    		  if ( IPv4.isValid(ip) )
		    		  {
		    			  devices.add( new ObservableDevice(location, ip) );
		    		  }
		    	  }
		      }
		
		      w.close();
		
		    } catch (BiffException e) 
		    {
		      System.err.println( e.getMessage() );
		      e.printStackTrace();
		    }
		    
			//retorno la lista con los datos leídos.
		    return devices;
	}

	
}
