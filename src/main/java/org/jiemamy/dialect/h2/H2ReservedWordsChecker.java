/*
 * Copyright 2007-2012 Jiemamy Project and the Others.
 * Created on 2008/08/24
 *
 * This file is part of Jiemamy.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.jiemamy.dialect.h2;

import java.util.Arrays;
import java.util.List;

import org.jiemamy.dialect.ReservedWordsChecker;

/**
 * H2Databaseの予約語をチェックするクラス。
 * 
 * @author daisuke
 */
public class H2ReservedWordsChecker implements ReservedWordsChecker {
	
	private static final List<String> RESERVED_WORDS = Arrays.asList("CROSS", "CURRENT_DATE", "CURRENT_TIME",
			"CURRENT_TIMESTAMP", "DISTINCT", "EXCEPT", "EXISTS", "FALSE", "FOR", "FROM", "FULL", "GROUP", "HAVING",
			"INNER", "INTERSECT", "IS", "JOIN", "LIKE", "LIMIT", "MINUS", "NATURAL", "NOT", "NULL", "ON", "ORDER",
			"PRIMARY", "ROWNUM", "SELECT", "SYSDATE", "SYSTIME", "SYSTIMESTAMP", "TODAY", "TRUE", "UNION", "UNIQUE",
			"WHERE");
	
	
	public boolean isReserved(String name) {
		return RESERVED_WORDS.contains(name);
	}
	
}
