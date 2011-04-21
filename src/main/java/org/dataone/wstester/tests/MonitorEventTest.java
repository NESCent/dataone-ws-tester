package org.dataone.wstester.tests;

import java.net.MalformedURLException;
import java.net.URL;

import org.dataone.wstester.MNTestInterface;

public class MonitorEventTest extends AbstractImplTest implements
		MNTestInterface {

	public String getName() {
		return "MonitorEventTest";
	}

	public String getDescription() {
		return "Tests whether /monitor/event is implemented";
	}

	public void test(URL aURL) {
		try {
			test(new URL(aURL.toString() + "/monitor/event"), new int[] { 2080,
					2083, 2081, 0 });
		}
		catch (MalformedURLException details) {
			throw new RuntimeException(details);
		}
	}
}