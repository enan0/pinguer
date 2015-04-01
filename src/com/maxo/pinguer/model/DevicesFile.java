package com.maxo.pinguer.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;

public class DevicesFile 
{
	public static final int SHEET = 0;
	public static final int IP = 1;
	public static final int LOCATION = 2;
	
	private String nameFileSheet; // = "CCTV";
	private String nameColLocation; // = "Ubicación";
	private String nameColIP; // = "IP";
	
	private String inputFile;

	
	public DevicesFile( String inputFile )
	{
		this.inputFile = inputFile;
		
		this.nameFileSheet = "CCTV";
		this.nameColLocation = "Ubicación";
		this.nameColIP = "IP";
	}
	

	public void setAttributes( String fileSheet, String columnIP, String columnLocation ) 
	{
		this.nameFileSheet = fileSheet;
		this.nameColIP = columnIP;
		this.nameColLocation = columnLocation;
	}
	
	
	public List<String> getAttributes()
	{
		List<String> details = new ArrayList<String>();
		
		details.add( this.nameFileSheet );
		details.add( this.nameColIP );
		details.add( this.nameColLocation );
		
		return details; 
		
	}
	
	
	public  List<ObservableDevice> loadDevices( ) throws IOException  
	{
		List<ObservableDevice> devices = new ArrayList<>();  
		  
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
