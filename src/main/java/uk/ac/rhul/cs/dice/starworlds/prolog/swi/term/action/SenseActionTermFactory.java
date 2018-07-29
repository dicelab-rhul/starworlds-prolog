package uk.ac.rhul.cs.dice.starworlds.prolog.swi.term.action;

import io.github.lukehutch.fastclasspathscanner.scanner.ScanResult;

import java.util.List;

import org.jpl7.Term;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.SenseAction;
import uk.ac.rhul.cs.dice.starworlds.environment.ambient.filter.Filter;
import uk.ac.rhul.cs.dice.starworlds.prolog.swi.term.GlobalTermFactory;
import uk.ac.rhul.cs.dice.starworlds.prolog.swi.term.SWITermFactory;
import uk.ac.rhul.cs.dice.starworlds.prolog.term.Termable;

public class SenseActionTermFactory extends SWITermFactory {

	static {
		GlobalTermFactory gtf = GlobalTermFactory.getInstance(); // initialise it if it is not already
		ScanResult result = gtf.getScanResult();
		List<String> list = result.getNamesOfClassesImplementing(Filter.class);
		try {
			for (String s : list) {
				Class<?> cls = Class.forName(s);
				System.out.println("Found filter: " + cls);
				if (!cls.isAnnotationPresent(Termable.class)) {
					// gtf.declareFactory(cls, gtf.new ObjectFactory()); // the filter was not marked as Termable.. add
					// it
					// anyway
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static final String SENSEACTIONTERMNAME = "sense";

	@Override
	public Term toTerm(Object arg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object fromTerm(Term term) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Term toTerm(Object arg, Class<?>[] typeinfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object fromTerm(Term term, Class<?>[] typeinfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<?> getObjectClass() {
		return SenseAction.class;
	}

}
