package uk.ac.rhul.cs.dice.starworlds.prolog.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class InheritanceMap<V> implements Map<Class<?>, V> {

	private Map<Class<?>, V> map = new HashMap<>();

	@Override
	public void clear() {
		map.clear();
	}

	@Override
	public V compute(Class<?> key, BiFunction<? super Class<?>, ? super V, ? extends V> remappingFunction) {
		return map.compute(key, remappingFunction);
	}

	@Override
	public V computeIfAbsent(Class<?> key, Function<? super Class<?>, ? extends V> mappingFunction) {
		return map.computeIfAbsent(key, mappingFunction);
	}

	@Override
	public V computeIfPresent(Class<?> key, BiFunction<? super Class<?>, ? super V, ? extends V> remappingFunction) {
		return map.computeIfPresent(key, remappingFunction);
	}

	@Override
	public boolean containsKey(Object key) {
		Class<?> cls = (Class<?>) key;
		do {
			if (map.containsKey(cls)) {
				return true;
			}
			cls = cls.getSuperclass();
		} while (cls != null);
		return false;
	}

	@Override
	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	@Override
	public Set<java.util.Map.Entry<Class<?>, V>> entrySet() {
		return map.entrySet();
	}

	@Override
	public boolean equals(Object o) {
		return map.equals(o);
	}

	@Override
	public void forEach(BiConsumer<? super Class<?>, ? super V> action) {
		map.forEach(action);
	}

	@Override
	public V get(Object key) {
		Class<?> cls = (Class<?>) key;
		V get = null;
		do {
			if ((get = map.get(cls)) != null) {
				return get;
			}
			cls = cls.getSuperclass();
		} while (cls != null);
		return get;
	}

	@Override
	public V getOrDefault(Object key, V defaultValue) {
		Class<?> cls = (Class<?>) key;
		V get = null;
		do {
			if ((get = map.get(cls)) != null) {
				return get;
			}
			cls = cls.getSuperclass();
		} while (cls != null);
		return defaultValue;
	}

	@Override
	public int hashCode() {
		return map.hashCode();
	}

	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public Set<Class<?>> keySet() {
		return map.keySet();
	}

	@Override
	public V merge(Class<?> key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
		return map.merge(key, value, remappingFunction);
	}

	@Override
	public V put(Class<?> key, V value) {
		return map.put(key, value);
	}

	@Override
	public void putAll(Map<? extends Class<?>, ? extends V> m) {
		map.putAll(m);
	}

	@Override
	public V putIfAbsent(Class<?> key, V value) {
		return map.putIfAbsent(key, value);
	}

	@Override
	public boolean remove(Object key, Object value) {
		return map.remove(key, value);
	}

	@Override
	public V remove(Object key) {
		return map.remove(key);
	}

	@Override
	public boolean replace(Class<?> key, V oldValue, V newValue) {
		return map.replace(key, oldValue, newValue);
	}

	@Override
	public V replace(Class<?> key, V value) {
		return map.replace(key, value);
	}

	@Override
	public void replaceAll(BiFunction<? super Class<?>, ? super V, ? extends V> function) {
		map.replaceAll(function);
	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public Collection<V> values() {
		return map.values();
	}

}
