package org.dataone.wstester.tests;

import java.net.MalformedURLException;
import java.net.URL;

import org.dataone.wstester.MNTestInterface;

public class LogTest extends AbstractImplTest implements MNTestInterface {

	public String getName() {
		return "LogImplTest";
	}

	public String getDescription() {
		return "Tests whether /log is implemented";
	}

	public void test(URL aURL) {
		try {
			test(new URL(aURL.toString() + "/log"), new int[] { 1461, 1480,
					1460, 0 });
		}
		catch (MalformedURLException details) {
			throw new RuntimeException(details);
		}
	}
}