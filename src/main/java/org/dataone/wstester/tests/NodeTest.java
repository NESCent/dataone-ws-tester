package org.dataone.wstester.tests;

import java.net.MalformedURLException;
import java.net.URL;

import org.dataone.wstester.MNTestInterface;

public class NodeTest extends AbstractImplTest implements MNTestInterface {

	public String getName() {
		return "NodeImplTest";
	}

	public String getDescription() {
		return "Tests whether /node is implemented";
	}

	public void test(URL aURL) {
		try {
			test(new URL(aURL.toString() + "/node"), new int[] { 2160, 2163,
					2162, 0 });
		}
		catch (MalformedURLException details) {
			throw new RuntimeException(details);
		}
	}
}