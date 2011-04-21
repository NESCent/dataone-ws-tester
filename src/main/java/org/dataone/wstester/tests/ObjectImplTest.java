package org.dataone.wstester.tests;

import java.net.MalformedURLException;
import java.net.URL;

import org.dataone.wstester.MNTestInterface;

public class ObjectImplTest extends AbstractImplTest implements MNTestInterface {

	public String getName() {
		return "ObjectImplTest";
	}

	public String getDescription() {
		return "Tests whether /object is implemented";
	}

	public void test(URL aURL) {
		try {
			test(new URL(aURL.toString() + "/object"), new int[] { 1001, 1002,
					1030, 0 });
		}
		catch (MalformedURLException details) {
			throw new RuntimeException(details);
		}
	}
}
