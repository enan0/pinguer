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

public class XLSFiles 
{
	private String nameFileSheet;
	private String nameColLocation;
	private String nameColIP;
	
	private String inputFile;


	public XLSFiles( )
	{
		this.nameFileSheet = "CCTV";
		this.nameColLocation = "Ubicación";
		this.nameColIP = "IP";
	}
	
	public void setInputFile( String inputFile ) 
	{
		this.inputFile = inputFile;
	}
	
	
	public Collection<ObservableDevice> loadDevicesFromXLS( ) throws IOException
	{
		ArrayList<ObservableDevice> cameras = new ArrayList<ObservableDevice>( readXLSFile() );
		
		File file = new File( this.inputFile );
		setXLSFilePath( file );
		
		//System.out.println( cameras.size( ) );
				
		return cameras;
	}
	
	
	public File getXLSFilePath()
	{
		Preferences prefs = Preferences.userNodeForPackage( XLSFiles.class);
		String filePath = prefs.get("filePath", null);
		
		if ( filePath != null )
			return new File(filePath);
		else
			return null;
	}
	
	
	public void setXLSFilePath( File file ) 
	{
		Preferences prefs = Preferences.userNodeForPackage( XLSFiles.class);
		if ( file != null )
			prefs.put("filePath", file.getPath() );
		else
			prefs.remove("filePath");
		
	}
	
	
	public void setXLSDetails( String fileSheet, String columnIP, String columnLocation ) 
	{
		this.nameFileSheet = fileSheet;
		this.nameColIP = columnIP;
		this.nameColLocation = columnLocation;
	}
	
	
	public Collection<String> getXLSDetails()
	{
		Collection<String> details = new ArrayList<String>();
		
		details.add( this.nameFileSheet );
		details.add( this.nameColIP );
		details.add( this.nameColLocation );
		
		return details; 
		
	}
	
	
	private  ArrayList<ObservableDevice> readXLSFile( ) throws IOException  
	{
		ArrayList<ObservableDevice> devices = new ArrayList<>();  
		  
	    File inputWorkbook = new File( this.inputFile );
	    Workbook w;
	    
		//Establezco el encoding del workbook para que me tome correctamente las tildes :D
		WorkbookSettings wSettings = new WorkbookSettings();
		wSettings.setEncoding( "iso-8859-1" );
		
		try 
		{
		  w = Workbook.getWorkbook( inputWorkbook, wSettings );
		  // Busco la Hoja CCTV o Carteles
		  Sheet sheetDevices = w.getSheet( this.nameFileSheet );
		
		  // Busco la Celda con el IP
		  Cell cellIP = sheetDevices.findCell( this.nameColIP );
		  int colIP = cellIP.getColumn();
		  Cell[] columnIP = sheetDevices.getColumn(colIP);
		
		  // Busco la Celda con la Ubicacion
		  Cell cellUbic = sheetDevices.findCell( this.nameColLocation );
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
