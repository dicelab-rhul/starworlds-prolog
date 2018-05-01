package uk.ac.rhul.cs.dice.starworlds.prolog.term;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD, ElementType.TYPE, ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Termable {

	String name() default "";

	Class<?> factory() default TermFactory.class;

}
