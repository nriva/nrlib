package it.nrsoft.nrlib;


import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

import it.nrsoft.nrlib.util.StringUtil;



public class UnitTestStringUtil {

	@Test
	public final void test() {

		
		Map<String,String> map = StringUtil.dictionarySplit("valore1, , valore3", ",", "", new String[] {"key1","key2", "key3"});
		
		assertEquals("valore1", map.get("key1"));
		assertNull(map.get("key2"));
		assertEquals("valore3", map.get("key3"));
		
		
		map = StringUtil.dictionarySplit("key1=valore1, key3= valore3", ",", "=", null);
		
		
		assertEquals("valore1", map.get("key1"));
		assertNull(map.get("key2"));
		assertEquals("valore3", map.get("key3"));
		
		
		map = StringUtil.dictionarySplit("chiave1=valore1, chiave3= valore3", ",", "=", new String[] {"key1","key2", "key3"});
		
		assertEquals(0, map.size());
		
		
		
		assertEquals("**********", StringUtil.newString('*', 10));
		assertEquals("----------", StringUtil.newString("-", 10));
	}

}
