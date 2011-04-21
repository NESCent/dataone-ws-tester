package org.dataone.wstester;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nu.xom.Attribute;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Nodes;
import nu.xom.Serializer;

public class TestRunnerServlet extends HttpServlet {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	// private static final Logger LOGGER =
	// LoggerFactory.getLogger(TestRunnerServlet.class);

	private static final String TESTS_DIR = "/WEB-INF/tests";
	private static final String RESULTS_FILE = "/results.html";
	private static List<MNTestInterface> TESTS;

	@Override
	protected void doGet(HttpServletRequest aReq, HttpServletResponse aResp)
			throws ServletException, IOException {
		String resultsPgPath = getServletContext().getRealPath(RESULTS_FILE);
		ServletOutputStream outStream = getServletOutputStream(aResp);
		Serializer serializer = new Serializer(outStream);
		Builder builder = new Builder(false);

		serializer.setIndent(4); // pretty-print output

		try {
			Document resultsDoc = builder.build(new File(resultsPgPath));
			Iterator<MNTestInterface> iterator = TESTS.iterator();
			Nodes nodes = resultsDoc.query("//div[@class = 'template']");
			String strURL = aReq.getParameter("url");
			URL url;
			
			if (strURL.endsWith("/")) {
				url = new URL(strURL.substring(0, strURL.length() - 1));
			}
			else {
				url = new URL(aReq.getParameter("url"));
			}

			if (nodes.size() > 0) {
				Element resultDiv = (Element) nodes.get(0);
				Element body = (Element) resultDiv.getParent();

				resultDiv.detach(); // We're just using it as a template

				// For now, just a simple iteration across them
				// but in the future, we could sort tests by type
				while (iterator.hasNext()) {
					Element div = (Element) resultDiv.copy();
					MNTestInterface tester = iterator.next();
					Element table = new Element("table");
					Element tr = new Element("tr");
					Element name = new Element("th");
					Element description = new Element("td");
					Element exception = new Element("td");

					tester.test(url);

					if (tester.wasSuccessful()) {
						div.addAttribute(new Attribute("class", "green"));
					}
					else {
						MNTestException details = tester.getException();

						if (details == null) {
							div.addAttribute(new Attribute("class", "grey"));
						}
						else if (details.getErrorCode() == 501) {
							div.addAttribute(new Attribute("class", "yellow"));
						}
						else {
							div.addAttribute(new Attribute("class", "red"));
						}
					}

					name.appendChild(tester.getName());
					tr.appendChild(name);

					description.appendChild(tester.getDescription());
					tr.appendChild(description);

					if (!tester.wasSuccessful()) {
						MNTestException details = tester.getException();
						StringBuilder buffer = new StringBuilder();

						// fail 'gracefully' if the test's code is broken
						if (details == null) {
							buffer.append("THIS TEST IS BROKEN - DEV PLEASE FIX ME");
						}
						else {
							buffer.append(details.getErrorCode());

							if (details.getDetailCode() != -1) {
								buffer.append(':');
								buffer.append(details.getDetailCode());
							}

							buffer.append(" - ");
							buffer.append(details.getDescription());

							if (details.getPID() != null) {
								buffer.append(' ');
								buffer.append(details.getPID());
							}
						}

						exception.appendChild(buffer.toString());
					}
					else {
						exception.appendChild("");
					}

					tr.appendChild(exception);

					table.appendChild(tr);
					div.appendChild(table);
					body.appendChild(div);
				}
			}

			serializer.write(resultsDoc);
		}
		catch (MalformedURLException details) {
			aResp.sendError(HttpServletResponse.SC_NOT_FOUND,
					aReq.getParameter("url")
							+ " does not seem to be a valid URL");
		}
		catch (IOException details) {
			throw details;
		}
		catch (Exception details) {
			throw new ServletException(details);
		}
		finally {
			outStream.close();
		}
	}

	@Override
	public void init() throws ServletException {
		super.init();

		TESTS = new ArrayList<MNTestInterface>();

		try {
			String cfgPath = getServletContext().getRealPath(TESTS_DIR);
			BufferedReader reader = new BufferedReader(new FileReader(cfgPath));
			String test;

			while ((test = reader.readLine()) != null) {
				if (test.startsWith("#"))
					continue;
				if (test.trim().equals(""))
					continue;

				log("Trying to load " + test);

				Class<?> clazz = Class.forName(test.trim());
				Object object = clazz.newInstance();

				if (object instanceof MNTestInterface) {
					TESTS.add((MNTestInterface) object);
				}
				else {
					log(test + " does not implement MNTestInterface");
				}
			}
		}
		catch (Exception details) {
			throw new ServletException(details);
		}
		
		// We don't need to see INFO output from underlying HTML client library
		System.setProperty("log4j.logger.com.gargoylesoftware.htmlunit.WebClient", "ERROR");
		Logger.getLogger("com.gargoylesoftware.htmlunit.WebClient").setLevel(Level.OFF);
	}

	private ServletOutputStream getServletOutputStream(
			HttpServletResponse aResponse) throws IOException {
		aResponse.setContentType("text/html; charset=UTF-8");
		return aResponse.getOutputStream();
	}
}
