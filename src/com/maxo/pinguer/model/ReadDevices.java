package com.maxo.pinguer.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;

public class ReadDevices {

	private String inputFile;
	private String sheet;
	private String column1;
	private String column2;
	
	public ReadDevices( String inputFile ) 
	{
		this.inputFile = inputFile;
	}
	
	public void setFileSheet( String sheet )
	{
		this.sheet = sheet;
	}
	  
	public void inputDevicesColumns( String deviceLocation, String deviceIP)
	{
		this.column1 = deviceIP;
		this.column2 = deviceLocation;
	}
  
	public ArrayList<ObservableDevice> readXLSFile( ) throws IOException  
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
		  Sheet sheetDevices = w.getSheet( sheet );
		
		  // Busco la Celda con el IP
		  Cell cellIP = sheetDevices.findCell( column1 );
		  int colIP = cellIP.getColumn();
		  Cell[] columnIP = sheetDevices.getColumn(colIP);
		
		  // Busco la Celda con la Ubicacion
		  Cell cellUbic = sheetDevices.findCell( column2 );
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
