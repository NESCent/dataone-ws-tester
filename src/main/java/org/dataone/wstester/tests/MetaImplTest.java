package org.dataone.wstester.tests;

import java.net.MalformedURLException;
import java.net.URL;

import org.dataone.wstester.MNTestInterface;

public class MetaImplTest extends AbstractDetailedTest implements
		MNTestInterface {

	public String getName() {
		return "MetaImplTest";
	}

	public String getDescription() {
		return "Tests whether /meta is implemented";
	}

	public void test(URL aURL) {
		myException = null;
		
		// look in AbstractDetailedTest for more details on getRecordID()
		String id = getRecordID(aURL);
		String url = aURL.toString() + "/meta/" + id;

		try {
			// look in AbstractImplTest for more details on test(url, errors)
			test(new URL(url), new int[] { 1041, 1080, 1090, 1060 });
		}
		catch (MalformedURLException details) {
			throw new RuntimeException(details);
		}
	}
}
