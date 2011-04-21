package org.dataone.wstester.tests;

import java.net.MalformedURLException;
import java.net.URL;

import org.dataone.wstester.MNTestInterface;

public class MonitorStatusTest extends AbstractImplTest implements
		MNTestInterface {

	public String getName() {
		return "MonitorStatusImplTest";
	}

	public String getDescription() {
		return "Tests whether /monitor/status is implemented";
	}

	public void test(URL aURL) {
		try {
			test(new URL(aURL.toString() + "/monitor/status"), new int[] {
					2100, 2103, 2101, 0 });
		}
		catch (MalformedURLException details) {
			throw new RuntimeException(details);
		}
	}
}