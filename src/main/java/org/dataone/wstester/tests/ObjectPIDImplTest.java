package org.dataone.wstester.tests;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.dataone.wstester.MNTestException;
import org.dataone.wstester.MNTestInterface;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.xml.XmlPage;

public class ObjectPIDImplTest extends AbstractImplTest implements
		MNTestInterface {

	private static final Logger LOGGER = Logger
			.getLogger(ObjectPIDImplTest.class.getName());

	public String getName() {
		return "ObjectPIDImplTest";
	}

	public String getDescription() {
		return "Tests whether /object/{pid} is implemented. It gets the object"
				+ " list (/object/) and selects an identifier from that.  Then, it"
				+ " tries to retrieve that record.";
	}

	public void test(URL aURL) {
		String objURL = aURL.toString() + "/object";

		// Zero out any pre-existing exception (not thread-safe obviously)
		myException = null;

		// Run a new test
		try {
			WebClient webClient = new WebClient();
			XmlPage page = webClient.getPage(objURL);
			Iterator<DomNode> iterator = page.getDescendants().iterator();

			while (iterator.hasNext()) {
				DomNode domNode = iterator.next();

				// Grab out first identifier and try that one
				if (domNode.getNodeName().equals("identifier")) {
					String id = iterator.next().getNodeValue();

					objURL = aURL.toString() + "/object/" + id;

					// Just retrieve it; if we can't, exceptions are thrown
					webClient.getPage(objURL);

					break;
				}
			}
		}
		catch (FailingHttpStatusCodeException details) {
			int statusCode = details.getStatusCode();

			switch (statusCode) {
			case 501:
				myException = new MNTestException(501, 1001, "Not implemented");
				break;
			case 400:
				myException = new MNTestException(400, 1002,
						"Invalid Request: " + objURL);
				break;
			case 404:
				myException = new MNTestException(404, 0, "Not Found: "
						+ objURL);
				break;
			default:
				LOGGER.log(Level.SEVERE,
						"UNEXPECTED EXCEPTION: " + details.getMessage(),
						details);

				myException = new MNTestException(statusCode, 0,
						details.getMessage());
			}
		}
		catch (IOException details) {
			// But if we have a general failure, let's report that too...
			myException = new MNTestException(500, 1030, details.getMessage());
		}
	}
}
