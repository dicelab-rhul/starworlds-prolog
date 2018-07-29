package uk.ac.rhul.cs.dice.starworlds.prolog.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import uk.ac.rhul.cs.dice.starworlds.exceptions.StarWorldsRuntimeException;

public class TestConsult {

	private static final String RESOURCEPATH = "src/main/resources/";
	private static final String MODULEMANAGERNAME = "SWIPrologModuleManager.pl";

	public static void main(String[] args) {
		File path = new File(RESOURCEPATH);
		File outfile = new File(RESOURCEPATH + MODULEMANAGERNAME);
		if (!path.exists()) {
			path.mkdirs();
		}
		boolean notExists = false;
		try {
			notExists = outfile.createNewFile();
		} catch (IOException e) {
			throw new StarWorldsRuntimeException("Failed to create SWI prolog module manager file: "
					+ outfile.getAbsolutePath(), e);
		}
		// if (notExists) {
		InputStream in = TestConsult.class.getResourceAsStream("src/resources/" + MODULEMANAGERNAME);
		System.out.println(in);
		try (OutputStream out = new FileOutputStream(outfile)) {
			int numRead;
			byte[] buffer = new byte[1024];
			while ((numRead = in.read(buffer)) >= 0) {
				System.out.println(new String(buffer));
				out.write(buffer, 0, numRead);
			}
		} catch (FileNotFoundException e) {
			throw new StarWorldsRuntimeException("Failed to find SWI prolog module manager file: " + outfile);
		} catch (IOException e) {
			throw new StarWorldsRuntimeException("Failed to read/write SWI prolog module manager.", e);
		}
		// }

		// new Query("consult", new Atom("hello(X)")).allSolutions();
	}
}
