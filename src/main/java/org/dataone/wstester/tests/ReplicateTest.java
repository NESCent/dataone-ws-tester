package org.dataone.wstester.tests;

import java.net.MalformedURLException;
import java.net.URL;

import org.dataone.wstester.MNTestInterface;

public class ReplicateTest extends AbstractImplTest implements MNTestInterface {

	public String getName() {
		return "ReplicateImplTest";
	}

	public String getDescription() {
		return "Tests whether /replicate is implemented";
	}

	public void test(URL aURL) {
		try {
			test(new URL(aURL.toString() + "/replicate"), new int[] { 2150,
					2153, 2151, 0 });
		}
		catch (MalformedURLException details) {
			throw new RuntimeException(details);
		}
	}
}