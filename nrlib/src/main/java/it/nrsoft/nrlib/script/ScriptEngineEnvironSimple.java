package it.nrsoft.nrlib.script;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ScriptEngineEnvironSimple extends ScriptEngineEnviron {
	
	
	private Map<String,Object> storage = new HashMap<String,Object>();

	@Override
	public int size() {
		return storage.size();
	}

	@Override
	public boolean isEmpty() {
		return storage.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return storage.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return storage.containsValue(value);
	}

	@Override
	public Object get(Object key) {
		return storage.get(key);
	}

	@Override
	public Object put(String key, Object value) {
		// TODO Auto-generated method stub
		return storage.put(key, value);
	}

	@Override
	public Object remove(Object key) {
		return storage.remove(key);
	}

	@Override
	public void putAll(Map<? extends String, ? extends Object> m) {
		storage.putAll(m);
	}

	@Override
	public void clear() {
		storage.clear();

	}

	@Override
	public Set<String> keySet() {
		return storage.keySet();
	}

	@Override
	public Collection<Object> values() {
		return storage.values();
	}

	@Override
	public Set<Entry<String, Object>> entrySet() {
		return storage.entrySet();
	}

}
