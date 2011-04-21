package org.dataone.wstester.tests;

import java.net.MalformedURLException;
import java.net.URL;

import org.dataone.wstester.MNTestInterface;

public class AccessRulesTest extends AbstractImplTest implements
		MNTestInterface {

	public String getName() {
		return "AccessRulesImplTest";
	}

	public String getDescription() {
		return "Tests whether /accessRules/ is implemented";
	}

	public void test(URL aURL) {
		try {
			test(new URL(aURL.toString() + "/accessRules/"), new int[] { 4401,
					4402, 4430, 4400 });
		}
		catch (MalformedURLException details) {
			throw new RuntimeException(details);
		}
	}
}
