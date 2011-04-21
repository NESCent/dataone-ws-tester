package org.dataone.wstester.tests;

import java.net.MalformedURLException;
import java.net.URL;

import org.dataone.wstester.MNTestInterface;

public class ErrorTest extends AbstractImplTest implements MNTestInterface {

	public String getName() {
		return "ErrorImplTest";
	}

	public String getDescription() {
		return "Tests whether /error is implemented";
	}

	public void test(URL aURL) {
		try {
			test(new URL(aURL.toString() + "/error"), new int[] { 2160, 2163,
					2161, 0 });
		}
		catch (MalformedURLException details) {
			throw new RuntimeException(details);
		}
	}
}
