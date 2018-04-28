package uk.ac.rhul.cs.dice.starworlds.prolog.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import uk.ac.rhul.cs.dice.starworlds.exceptions.StarWorldsRuntimeException;

public class ReflectionUtils {

	public static List<Field> getFieldWithAnnotation(Class<?> cls, Class<? extends Annotation> annotation) {
		List<Field> list = new ArrayList<>();
		Class<?> c = cls;
		while (c != null) {
			for (Field field : c.getDeclaredFields()) {
				if (field.isAnnotationPresent(annotation)) {
					list.add(field);
				}
			}
			c = c.getSuperclass();
		}
		return list;
	}

	/**
	 * Gets a list of the enclosing instances of the given object, the list is ordered from the first enclosing instance
	 * to the last (last being an instance of the highest enclosing class) <br>
	 * Warning - this uses an undocumented "hack" to get the enclosing instance of an object via a synthetic variable
	 * this$n, this may be removed or changed at some point breaking this utility method (Hopefully! if it is changes a
	 * new way of accessing the enclosing instance will be provided!).
	 * 
	 * @param object
	 *            to getEnclosing instances, empty if the object is not an instance of an inner class
	 * @return array of enclosing instances
	 */
	public static Object[] getEnclosingInstances(Object object) {
		if (object == null) {
			return null;
		}
		Class<?> ob = object.getClass();
		if (ob.getEnclosingClass() == null) {
			return new Object[0];
		}
		try {
			List<Class<?>> enclosing = new ArrayList<>();
			Class<?> en = ob;
			do {
				enclosing.add(en);
			} while ((((en = en.getEnclosingClass()).getEnclosingClass()) != null));
			Object[] result = new Object[enclosing.size()];
			for (int i = 0; i < enclosing.size(); i++) {
				Field f = enclosing.get(i).getDeclaredField("this$" + (enclosing.size() - 1 - i));
				f.setAccessible(true);
				result[i] = f.get(object);
				object = result[i];
			}
			return result;
		} catch (NoSuchFieldException e) {
			throw new StarWorldsRuntimeException(
					"Possibly that java has been updated and the hack no longer works? check this$n for inner instances",
					e);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new StarWorldsRuntimeException("Something went wrong getting enclosing instances.", e);
		}
	}

	private static final Set<Class<?>> WRAPPER_TYPES = getWrapperTypes();

	public static boolean isWrapperType(Class<?> clazz) {
		return WRAPPER_TYPES.contains(clazz);
	}

	private static Set<Class<?>> getWrapperTypes() {
		Set<Class<?>> ret = new HashSet<Class<?>>();
		ret.add(String.class);
		ret.add(Boolean.class);
		ret.add(Character.class);
		ret.add(Byte.class);
		ret.add(Short.class);
		ret.add(Integer.class);
		ret.add(Long.class);
		ret.add(Float.class);
		ret.add(Double.class);
		ret.add(Void.class);
		return ret;
	}

}
