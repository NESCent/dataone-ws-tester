package org.dataone.wstester.tests;

import java.net.MalformedURLException;
import java.net.URL;

import org.dataone.wstester.MNTestInterface;

public class IsAuthorizedTest extends AbstractImplTest implements
		MNTestInterface {

	public String getName() {
		return "IsAuthorizedImplTest";
	}

	public String getDescription() {
		return "Tests whether /isAuthorized/ is implemented";
	}

	public void test(URL aURL) {
		try {
			test(new URL(aURL.toString() + "/isAuthorized/"), new int[] { 1780,
					1761, 1760, 1800 });
		}
		catch (MalformedURLException details) {
			throw new RuntimeException(details);
		}
	}
}