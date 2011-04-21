package org.dataone.wstester;

import nu.xom.Attribute;
import nu.xom.Element;

/**
 * An exception to be thrown when a Member Node test fails or is not yet
 * implemented.
 */
public class MNTestException {

	private String myName;
	
	private int myErrorCode;

	private int myDetailCode;

	private String myDescription;
	
	private String myPID;

	/**
	 * Creates a new Member Node Test exception.
	 * 
	 * @param aErrorCode The type of error the exception represents
	 * @param aDetailCode The detail code for the specific error
	 * @param aErrorMessage A descriptive, human-friendly error message
	 * @param aPID A PID for an object that is causing a problem
	 */
	public MNTestException(int aErrorCode, int aDetailCode, String aDescription, String aPID) {
		myErrorCode = aErrorCode;
		myDetailCode = aDetailCode;
		myDescription = aDescription;
		myPID = aPID;
	}
	
	/**
	 * Creates a new Member Node Test exception.
	 * 
	 * @param aErrorCode The type of error the exception represents
	 * @param aDetailCode The detail code for the specific error
	 * @param aErrorMessage A descriptive, human-friendly error message
	 */
	public MNTestException(int aErrorCode, int aDetailCode, String aDescription) {
		myErrorCode = aErrorCode;
		myDetailCode = aDetailCode;
		myDescription = aDescription;
	}
	
	/**
	 * Returns A descriptive, human-friendly error message.
	 * 
	 * @return A descriptive, human-friendly error message
	 */
	public String getDescription() {
		return myDescription;
	}
	
	public boolean hasPID() {
		return myPID != null;
	}
	
	public String getPID() {
		return myPID;
	}

	/**
	 * Returns an integer value error code that tells the error code group into
	 * which the error falls.
	 * 
	 * @return An integer value error code
	 */
	public int getErrorCode() {
		return myErrorCode;
	}

	/**
	 * Returns an integer value detail code that tells more about which type of
	 * failure within the error code group.
	 * 
	 * @return An integer value detail code
	 */
	public int getDetailCode() {
		return myDetailCode;
	}

	
	public String toXML() {
		Element error = new Element("error");
		Element description = new Element("description");
		Element traceInfo = new Element("trace");
		
		error.addAttribute(new Attribute("name", myName));
		error.addAttribute(new Attribute("errorCode", Integer.toString(myErrorCode)));
		error.addAttribute(new Attribute("detailCode", Integer.toString(myDetailCode)));
		error.addAttribute(new Attribute("pid", myPID));

		error.appendChild(description);
		//error.appendChild(traceInfo);

		return error.toXML();

		/*
		<error name='NotFound'
		       errorCode='404'
		       detailCode='1020.1'
		       pid='123XYZ'>
		  <description>The specified object does not exist on this node.</description>
		  <traceInformation>
		    method: mn.get
		    hint: http://cn.dataone.org/cn/resolve/123XYZ
		  </traceInformation>
		</error>
		*/
	}
}
