package org.dataone.wstester.tests;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.logging.Logger;

import org.dataone.wstester.MNConstants;
import org.dataone.wstester.MNTestException;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.xml.XmlPage;

public abstract class AbstractDetailedTest extends AbstractImplTest {

	private static final Logger LOGGER = Logger
			.getLogger(AbstractDetailedTest.class.getName());

	public int getType() {
		return DETAILED_TEST;
	}

	protected String getRecordID(URL aURL) {
		String objURL = aURL.toString() + "/object";

		// Run a new test
		try {
			WebClient webClient = new WebClient();
			XmlPage page = webClient.getPage(objURL);
			Iterator<DomNode> iterator = page.getDescendants().iterator();

			while (iterator.hasNext()) {
				DomNode domNode = iterator.next();

				// Grab out first identifier and try that one
				if (domNode.getNodeName().equals("identifier")) {
					return iterator.next().getNodeValue();
				}
			}
		}
		catch (FailingHttpStatusCodeException details) {
			switch (details.getStatusCode()) {
			case 501:
				myException = new MNTestException(501, 1001, "Not implemented");
				break;
			case 400:
				myException = new MNTestException(400, 1002,
						"Invalid Request: " + objURL.toString());
				break;
			}
		}
		catch (IOException details) {
			// But if we have a general failure, let's report that too...
			myException = new MNTestException(500, 1030, details.getMessage());
		}

		return null;
	}
}
