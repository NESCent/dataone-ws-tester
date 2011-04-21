package org.dataone.wstester.tests;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.dataone.wstester.MNConstants;
import org.dataone.wstester.MNTestException;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;

public abstract class AbstractImplTest implements MNConstants {

	private static final Logger LOGGER = Logger
			.getLogger(AbstractImplTest.class.getName());

	protected MNTestException myException;

	public int getType() {
		return IMPLEMENTATION_TEST;
	}

	public boolean wasSuccessful() {
		return myException == null ? true : false;
	}

	public MNTestException getException() {
		return myException;
	}

	/**
	 * Run the test.
	 * 
	 * @param aURL A service to test
	 * @param aDetailCode An int array with three values: codes for not
	 *        implemented, invalid request, and general failure
	 */
	protected void test(URL aURL, int[] aDetailCode) {
		// Zero out any pre-existing exception (not thread-safe obviously)
		myException = null;

		// Run a new test
		try {
			new WebClient().getPage(aURL);
		}
		catch (FailingHttpStatusCodeException details) {
			int statusCode = details.getStatusCode();

			switch (statusCode) {
			case 501:
				myException = new MNTestException(501, aDetailCode[0],
						"Not implemented");
				break;
			case 400:
				myException = new MNTestException(400, aDetailCode[1],
						"Invalid Request: " + aURL.toString());
				break;
			case 404:
				myException = new MNTestException(404, aDetailCode[3],
						"Not Found: " + aURL.toString());
				break;
			default:
				LOGGER.log(Level.SEVERE, "UNEXPECTED ERROR: " + statusCode,
						details);

				myException = new MNTestException(500, aDetailCode[2],
						details.getMessage());
			}
		}
		catch (IOException details) {
			// But if we have a general failure, let's report that too...
			myException = new MNTestException(500, aDetailCode[2],
					details.getMessage());
		}
	}
}
