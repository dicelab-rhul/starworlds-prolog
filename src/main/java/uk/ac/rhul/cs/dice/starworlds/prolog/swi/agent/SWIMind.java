package uk.ac.rhul.cs.dice.starworlds.prolog.swi.agent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.jpl7.Atom;
import org.jpl7.Compound;
import org.jpl7.Integer;
import org.jpl7.Query;
import org.jpl7.Term;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.EnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.entities.Agent;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.Mind;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.ComponentKey;
import uk.ac.rhul.cs.dice.starworlds.exceptions.StarWorldsRuntimeException;
import uk.ac.rhul.cs.dice.starworlds.perception.Perception;
import uk.ac.rhul.cs.dice.starworlds.prolog.actions.ActionEntry;
import uk.ac.rhul.cs.dice.starworlds.prolog.entities.agent.mind.PrologMind;
import uk.ac.rhul.cs.dice.starworlds.prolog.exceptions.PrologRunTimeException;
import uk.ac.rhul.cs.dice.starworlds.prolog.swi.term.GlobalTermFactory;
import uk.ac.rhul.cs.dice.starworlds.prolog.term.TermFactory;
import uk.ac.rhul.cs.dice.starworlds.prolog.utils.SWIUtils;
import uk.ac.rhul.cs.dice.starworlds.prolog.utils.SWIUtils.TermCollector;
import uk.ac.rhul.cs.dice.starworlds.utils.StringUtils;

public class SWIMind<K extends ComponentKey> extends PrologMind<Term, K> {

	public static final String SWIPLEXTENSION = ".pl";

	private static final String RESOURCEPATH = "src/main/resources/";
	private static final String MODULEMANAGERNAME = "SWIPrologModuleManager.pl";
	private static final String DEFAULTVISIBLEPREDICATES = "[perceive/2, decide/2]";
	private String visiblePredicates = DEFAULTVISIBLEPREDICATES;

	static {
		File path = new File(RESOURCEPATH);
		File outfile = new File(RESOURCEPATH + MODULEMANAGERNAME);
		if (!path.exists()) {
			path.mkdirs();
		}
		boolean exists = false;
		try {
			exists = !outfile.createNewFile();
		} catch (IOException e) {
			throw new StarWorldsRuntimeException("Failed to create SWI prolog module manager file: "
					+ outfile.getAbsolutePath(), e);
		}
		if (!exists) {
			String managerResource = "/" + RESOURCEPATH + MODULEMANAGERNAME;
			InputStream in = SWIMind.class.getResourceAsStream(managerResource);
			if (in != null) {
				try (OutputStream out = new FileOutputStream(outfile)) {
					int numRead;
					byte[] buffer = new byte[1024];
					while ((numRead = in.read(buffer)) >= 0) {
						out.write(buffer, 0, numRead);
					}

				} catch (FileNotFoundException e) {
					throw new StarWorldsRuntimeException("Failed to find SWI prolog module manager file: " + outfile);
				} catch (IOException e) {
					throw new StarWorldsRuntimeException("Failed to read/write SWI prolog module manager.", e);
				}
			} else {
				throw new StarWorldsRuntimeException("Failed to get resource: " + managerResource);
			}
		}
		new Query("consult", new Atom(RESOURCEPATH + MODULEMANAGERNAME)).allSolutions();
	}

	protected String module;

	public SWIMind(String mindModule) {
		super(GlobalTermFactory.getInstance());
		this.module = this.newMind(mindModule);
	}

	public SWIMind(String mindModule, String[] visiblePredicates) {
		super(GlobalTermFactory.getInstance());
		this.visiblePredicates = constructVisiblePredicates(visiblePredicates);
		this.module = this.newMind(mindModule);
	}

	private String constructVisiblePredicates(String[] predicates) {
		StringBuilder builder = new StringBuilder("[");
		if (predicates.length > 0) {
			builder.append(predicates[0]);
		} else {

		}
		for (int i = 1; i < predicates.length; i++) {
			builder.append(", ").append(predicates[i]);
		}
		builder.append("]");
		return builder.toString();
	}

	protected String newMind(String sourceModule) {
		String query = new StringBuilder("new_mind(").append(getVisiblePredicates()).append(",").append(sourceModule)
				.append(",M)").toString();
		Query q = new Query(query);
		Term module = q.allSolutions()[0].get("M");
		System.out.println("Successfully created a SWIProlog mind from source module: " + sourceModule
				+ ", mind module: " + module + " with visible predicates: " + getVisiblePredicates());
		return module.name();
	}

	@Override
	public void cycle() {
		// perceive
		Collection<Perception> perceptions = this.getBody().perceive();
		System.out.print("Agent" + this.getId() + " perceives: " + StringUtils.collectionToString(perceptions));
		List<Term> terms = perceptions.stream().map(x -> this.toTerm((Perception) x)).collect(Collectors.toList());
		Term term = SWIUtils.toList(terms);
		System.out.println("PERCEPTION: " + term);
		StringBuilder builder = new StringBuilder(module).append(":perceive(").append(term.toString()).append(",")
				.append(getTime()).append(")");
		Query perceive = new Query(builder.toString());
		perceive.allSolutions();
		// decide
		builder = new StringBuilder(module).append(":decide(Actions,").append(getTime()).append(")");
		Query decide = new Query(builder.toString());
		Map<String, Term>[] solutions = decide.allSolutions();
		// what to do about multiple solutions?
		System.out.println(Arrays.toString(solutions));
		List<Term> actions = SWIUtils
				.visitList(solutions[0].get("Actions"), new TermCollector<>(new ArrayList<Term>()));
		Map<K, ? extends EnvironmentalAction> attempt = actions.stream().map(this::getActionEntry)
				.collect(Collectors.toMap(ActionEntry::getKey, ActionEntry::getAction));
		System.out.print("Agent" + this.getId() + " attempts:  " + StringUtils.mapToString(attempt));
		this.getBody().attempt(attempt);
	}

	/**
	 * Calls the given predicate under this {@link Agent}s module. - recall that only predicates that are visible can be
	 * called.
	 * 
	 * @param term
	 *            : to call
	 */
	protected void call(String term) {
		StringBuilder builder = new StringBuilder(module).append(":").append(term);
		Query q = new Query(builder.toString());
		System.out.println("---> Calling: " + q);
		q.allSolutions();
	}

	/**
	 * Calls the given predicate under this {@link Agent}s module. - recall that only predicates that are visible can be
	 * called.
	 * 
	 * @param term
	 *            : to call
	 */
	protected void call(Term term) {
		Query q = new Query(new Compound(":", new Term[] { new Atom(this.module), term }));
		System.out.println("---> Calling: " + q);
		q.allSolutions();
	}

	/**
	 * Calls the perceive predicate under this {@link Agent}s module with a single perception and a given time.
	 * 
	 * @param percept
	 * @param time
	 */
	protected void perceive(String percept, Long time) {
		StringBuilder builder = new StringBuilder(module).append(":perceive([").append(percept).append("],")
				.append(time).append(")");
		Query query = new Query(builder.toString());
		query.allSolutions();
	}

	/**
	 * Calls the perceive predicate under this {@link Agent}s module with a single perception.
	 * 
	 * @param percept
	 */
	protected void perceive(Term percept) {
		perceive(percept.toString(), getTime());
	}

	/**
	 * Calls the perceive predicate under this {@link Agent}s module with a single perception and a given time.
	 * 
	 * @param percept
	 * @param time
	 */
	protected void perceive(Term percept, Long time) {
		perceive(percept.toString(), time);
	}

	/**
	 * Constructs an {@link ActionEntry} from a prolog term, the default format for an attempt term is the following:
	 * Component(Actuator, Action). <br>
	 * 1. Component is the term name associated with the {@link ComponentKey} class being used by this mind. <br>
	 * 2. Actuator is the term representing the {@link ComponentKey} instance (i.e. Component(Actuator) will be
	 * converted into a java object using the provided {@link TermFactory}. <br>
	 * 3. Action is the term that will be converted into the {@link EnvironmentalAction} object. <br>
	 * 
	 * The method contains an unchecked cast which will throw a {@link ClassCastException} is the wrong type of
	 * {@link ComponentKey} was constructed after conversion. The term returned by prolog must respect the
	 * {@link ComponentKey} type of its associated java {@link Mind}.
	 * 
	 * @param term
	 *            : to convert to an {@link ActionEntry}
	 * @return the {@link ActionEntry} constructed as a result of converting the given term to a {@link ComponentKey}
	 *         and {@link EnvironmentalAction}.
	 * @throws ClassCastException
	 *             if the {@link ComponentKey} term given by prolog results in an incorrect conversion.
	 */
	@SuppressWarnings("unchecked")
	public ActionEntry<K, EnvironmentalAction> getActionEntry(Term term) {
		Object key = this.fromTerm(new Compound(term.name(), new Term[] { new Atom(term.arg(1).name()) }));
		Object action = this.fromTerm(term.arg(2));
		// we cannot make this cast safe because it depends on what the is returned from prolog
		return new ActionEntry<>((K) key, (EnvironmentalAction) action);
	}

	private Long getTime() {
		return this.getBody().getEnvironment().getPhysics().getTime();
	}

	public String getVisiblePredicates() {
		return this.visiblePredicates;
	}

	/**
	 * Constructs a formated visible predicate string. The input arrays should be of the same length.
	 * 
	 * @param predicate
	 * @param arity
	 * @return the formatted visible predicate string
	 */
	public static String constructVisiblePredicates(String[] predicate, Integer[] arity) {
		StringBuilder builder = new StringBuilder("[");
		if (predicate.length == arity.length) {
			for (int i = 0; i < predicate.length; i++) {
				builder.append(predicate).append("/").append(arity);
			}
			builder.append("]");
			return builder.toString();
		} else {
			throw new PrologRunTimeException("predicate and arity must be of the same length, " + predicate.length
					+ "!=" + arity.length);
		}
	}

	public static String declareMind(String sourcePath) {
		String source = sourcePath;
		File file = new File(sourcePath);
		if (file.exists()) {
			if (!file.isAbsolute()) {
				source = file.getAbsolutePath();
			}
			source = source.toLowerCase().replace('\\', '/');
			String query = new StringBuilder("declare_mind('").append(source).append("',M)").toString();
			Query q = new Query(query);
			Term module = q.allSolutions()[0].get("M");
			System.out.println("Successfully added mind module: " + module.name() + " from file: "
					+ System.lineSeparator() + "    " + source);
			return module.name();
		}
		throw new PrologRunTimeException("Failed to declare new mind, file: " + source + " not found.");
	}
}
