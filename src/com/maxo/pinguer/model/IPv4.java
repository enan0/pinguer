package com.maxo.pinguer.model;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;

public class IPv4
{
	
	public static boolean isValid( String ip ) 
	{
		 if ( ! ip.contains(".") ) return false;
		 String[] octetos = ip.split("\\.");
		 if ( octetos.length != 4 ) return false;
		 for( String oct : octetos )
		 {
			 if ( ! ( 0 < Integer.parseInt(oct) && Integer.parseInt(oct) < 256 ) )
				 return false;
		 }
	
		return true;
	}

	
	private static boolean pingWindows( String ip ) throws IOException, InterruptedException
	{
		try {
			
            String command = ( "ping -n 5 -w 2 " + ip );

            Process proc = Runtime.getRuntime().exec(command);

            BufferedReader stdInput = new BufferedReader( new InputStreamReader( proc.getInputStream() ) );

            // read the output from the command
            String s = null;
            int count = 0;
            while ( ( s = stdInput.readLine() ) != null ) 
            {
            	if ( s.toLowerCase().contains("ms") && s.toLowerCase().contains("ttl") )
            		count++;
            }

            proc.waitFor();
            
            if ( count > 3 )
            	return true;
            else
            	return false;
            
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
         
         
	}
	
	private static boolean ping( String ip ) throws IOException
	{
		InetAddress host = InetAddress.getByName( ip );
		try 
		{
			return host.isReachable(15000);
		}
		catch (IOException e)
		{
            e.printStackTrace();
            return false;
		}
	}
	
	
	public static boolean pingIP( String ip ) throws IOException, InterruptedException
	{
        if( System.getProperty("os.name").startsWith("Windows") ) 
        {   
            // For Windows
        	return pingWindows( ip );
        }
        else 
        {
            // For Linux and OSX
            return ping( ip );
        }
		
		
	}
	
}
