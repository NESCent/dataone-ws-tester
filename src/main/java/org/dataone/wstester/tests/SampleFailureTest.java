package org.dataone.wstester.tests;

import java.net.MalformedURLException;
import java.net.URL;

import org.dataone.wstester.MNTestInterface;

public class SampleFailureTest extends AbstractImplTest implements
		MNTestInterface {

	public String getName() {
		return "SampleFailureTest";
	}

	public String getDescription() {
		return "Tests whether /meta responds - this should fail (e.g., we're testing that the display of failure works)";
	}

	public void test(URL aURL) {
		try {
			test(new URL(aURL.toString() + "/meta"), new int[] { 1041, 1080,
					1090, 0 });
		}
		catch (MalformedURLException details) {
			throw new RuntimeException(details);
		}
	}
}
