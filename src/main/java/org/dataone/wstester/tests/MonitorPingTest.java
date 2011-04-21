package org.dataone.wstester.tests;

import java.net.MalformedURLException;
import java.net.URL;

import org.dataone.wstester.MNTestInterface;

public class MonitorPingTest extends AbstractImplTest implements
		MNTestInterface {

	public String getName() {
		return "MonitorPingImplTest";
	}

	public String getDescription() {
		return "Tests whether /monitor/ping is implemented";
	}

	public void test(URL aURL) {
		try {
			test(new URL(aURL.toString() + "/monitor/ping"), new int[] { 2041,
					2044, 2042, 0 });
		}
		catch (MalformedURLException details) {
			throw new RuntimeException(details);
		}
	}
}