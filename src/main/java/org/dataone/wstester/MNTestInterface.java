package org.dataone.wstester;

import java.net.URL;

/**
 * A simple test interface that the <code>TestRunnerServlet</code> will use to
 * interact with individual tests run against the DataONE Member Node Web
 * Services interface.
 */
public interface MNTestInterface {

	/**
	 * Run the test against the supplied service URL.
	 * 
	 * @param aURL A DataONE MN Web service target
	 */
	public void test(URL aURL);
	
	/**
	 * Returns the short name of the Member Node test.
	 * 
	 * @return The short name of the Member Node test
	 */
	public String getName();

	/**
	 * Returns the longer, human-friendly description of the Member Node test.
	 * 
	 * @return The longer, human-friendly description of the Member Node test
	 */
	public String getDescription();

	/**
	 * Returns the type of test; constant type values are defined in
	 * <code>MNTestConstants</code>. This is used for sorting the order in which
	 * tests are run, though tests should run independent of other tests.
	 * 
	 * @return The type of Member Node test
	 */
	public int getType();

	/**
	 * Returns a boolean indicating whether the test was successful or not.
	 * 
	 * @return A boolean indicating whether the test was successfully or not
	 */
	public boolean wasSuccessful();

	/**
	 * Returns an exception if the test failed or, if the test completed successfully, a null.
	 * 
	 * @return An exception with more details about why the test was not successful
	 */
	public MNTestException getException();

}
