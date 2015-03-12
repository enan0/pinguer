package com.maxo.pinguer.model;

import static org.junit.Assert.*;

import org.junit.Test;



public class ObservableDeviceTest {

	@Test
	public void testGetLocation() 
	{
		String expectedLocation= "Calle 1";
		
		ObservableDevice device = new ObservableDevice("Calle 1", "10.1.1.1");
		
		assertEquals( expectedLocation, device.getLocation().get() );
		
	}

	@Test
	public void testGetIP() 
	{
		String expectedIP = "10.1.1.1";
		
		ObservableDevice device = new ObservableDevice("Calle 1", "10.1.1.1");
		
		assertEquals(expectedIP, device.getIP().get() );
	}

}
