package org.dataone.wstester.tests;

import java.net.MalformedURLException;
import java.net.URL;

import org.dataone.wstester.MNTestInterface;

public class MonitorObjectTest extends AbstractImplTest implements
		MNTestInterface {

	public String getName() {
		return "MonitorObjectImplTest";
	}

	public String getDescription() {
		return "Tests whether /monitor/object is implemented";
	}

	public void test(URL aURL) {
		try {
			test(new URL(aURL.toString() + "/monitor/object"), new int[] {
					2060, 2063, 2061, 0 });
		}
		catch (MalformedURLException details) {
			throw new RuntimeException(details);
		}
	}
}