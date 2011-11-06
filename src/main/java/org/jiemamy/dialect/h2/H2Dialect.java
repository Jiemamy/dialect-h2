/*
 * Copyright 2007-2011 Jiemamy Project and the Others.
 * Created on 2011/10/03
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

import static org.jiemamy.model.datatype.RawTypeCategory.BLOB;
import static org.jiemamy.model.datatype.RawTypeCategory.BOOLEAN;
import static org.jiemamy.model.datatype.RawTypeCategory.CHARACTER;
import static org.jiemamy.model.datatype.RawTypeCategory.CLOB;
import static org.jiemamy.model.datatype.RawTypeCategory.DATE;
import static org.jiemamy.model.datatype.RawTypeCategory.DECIMAL;
import static org.jiemamy.model.datatype.RawTypeCategory.DOUBLE;
import static org.jiemamy.model.datatype.RawTypeCategory.FLOAT;
import static org.jiemamy.model.datatype.RawTypeCategory.INTEGER;
import static org.jiemamy.model.datatype.RawTypeCategory.NUMERIC;
import static org.jiemamy.model.datatype.RawTypeCategory.OTHER;
import static org.jiemamy.model.datatype.RawTypeCategory.REAL;
import static org.jiemamy.model.datatype.RawTypeCategory.SMALLINT;
import static org.jiemamy.model.datatype.RawTypeCategory.TIME;
import static org.jiemamy.model.datatype.RawTypeCategory.TIMESTAMP;
import static org.jiemamy.model.datatype.RawTypeCategory.VARCHAR;

import java.util.HashMap;
import java.util.List;

import com.google.common.collect.Lists;

import org.jiemamy.dialect.AbstractDialect;
import org.jiemamy.dialect.DatabaseMetadataParser;
import org.jiemamy.dialect.DefaultDatabaseMetadataParser;
import org.jiemamy.dialect.DefaultSqlEmitter;
import org.jiemamy.dialect.Dialect;
import org.jiemamy.dialect.Necessity;
import org.jiemamy.dialect.SqlEmitter;
import org.jiemamy.model.datatype.SimpleRawTypeDescriptor;
import org.jiemamy.model.datatype.TypeParameterKey;
import org.jiemamy.validator.CompositeValidator;
import org.jiemamy.validator.Validator;

/**
 * H2Database用 {@link Dialect} 実装クラス。
 * 
 * @version $Id$
 * @author daisuke
 */
@SuppressWarnings("serial")
public class H2Dialect extends AbstractDialect {
	
	private static List<Entry> typeEntries = Lists.newArrayList();
	
	static {
		// FORMAT-OFF
		// CHECKSTYLE:OFF
		typeEntries.add(new Entry(new SimpleRawTypeDescriptor(INTEGER),
				new HashMap<TypeParameterKey<?>, Necessity>() {{
						put(TypeParameterKey.SIZE, Necessity.OPTIONAL);
						put(TypeParameterKey.SERIAL, Necessity.OPTIONAL);
				}}));
		typeEntries.add(new Entry(new SimpleRawTypeDescriptor(INTEGER, "BIGINT"),
				new HashMap<TypeParameterKey<?>, Necessity>() {{
						put(TypeParameterKey.SIZE, Necessity.OPTIONAL);
						put(TypeParameterKey.SERIAL, Necessity.OPTIONAL);
				}}));
		typeEntries.add(new Entry(new SimpleRawTypeDescriptor(SMALLINT),
				new HashMap<TypeParameterKey<?>, Necessity>() {{
						put(TypeParameterKey.SIZE, Necessity.OPTIONAL);
						put(TypeParameterKey.SERIAL, Necessity.OPTIONAL);
				}}));
		typeEntries.add(new Entry(new SimpleRawTypeDescriptor(SMALLINT, "TINYINT"),
				new HashMap<TypeParameterKey<?>, Necessity>() {{
						put(TypeParameterKey.SIZE, Necessity.OPTIONAL);
						put(TypeParameterKey.SERIAL, Necessity.OPTIONAL);
				}}));
		typeEntries.add(new Entry(new SimpleRawTypeDescriptor(NUMERIC),
				new HashMap<TypeParameterKey<?>, Necessity>() {{
						put(TypeParameterKey.PRECISION, Necessity.REQUIRED);
						put(TypeParameterKey.SCALE, Necessity.OPTIONAL);
				}}));
		typeEntries.add(new Entry(new SimpleRawTypeDescriptor(DECIMAL),
				new HashMap<TypeParameterKey<?>, Necessity>() {{
						put(TypeParameterKey.PRECISION, Necessity.REQUIRED);
						put(TypeParameterKey.SCALE, Necessity.OPTIONAL);
				}}));
		typeEntries.add(new Entry(new SimpleRawTypeDescriptor(DECIMAL, "NUMBER"),
				new HashMap<TypeParameterKey<?>, Necessity>() {{
						put(TypeParameterKey.PRECISION, Necessity.REQUIRED);
						put(TypeParameterKey.SCALE, Necessity.OPTIONAL);
				}}));
		typeEntries.add(new Entry(new SimpleRawTypeDescriptor(DECIMAL, "DEC"),
				new HashMap<TypeParameterKey<?>, Necessity>() {{
						put(TypeParameterKey.PRECISION, Necessity.REQUIRED);
						put(TypeParameterKey.SCALE, Necessity.OPTIONAL);
				}}));
		typeEntries.add(new Entry(new SimpleRawTypeDescriptor(DECIMAL, "NUMERIC"),
				new HashMap<TypeParameterKey<?>, Necessity>() {{
						put(TypeParameterKey.PRECISION, Necessity.REQUIRED);
						put(TypeParameterKey.SCALE, Necessity.OPTIONAL);
				}}));
		typeEntries.add(new Entry(new SimpleRawTypeDescriptor(FLOAT)));
		typeEntries.add(new Entry(new SimpleRawTypeDescriptor(REAL)));
		typeEntries.add(new Entry(new SimpleRawTypeDescriptor(DOUBLE)));
		typeEntries.add(new Entry(new SimpleRawTypeDescriptor(BOOLEAN)));
		typeEntries.add(new Entry(new SimpleRawTypeDescriptor(BOOLEAN, "BIT", "BOOL")));
		typeEntries.add(new Entry(new SimpleRawTypeDescriptor(TIME)));
		typeEntries.add(new Entry(new SimpleRawTypeDescriptor(DATE)));
		typeEntries.add(new Entry(new SimpleRawTypeDescriptor(TIMESTAMP)));
		typeEntries.add(new Entry(new SimpleRawTypeDescriptor(TIMESTAMP, "DATETIME")));
		typeEntries.add(new Entry(new SimpleRawTypeDescriptor(TIMESTAMP, "SMALLDATETIME")));
		typeEntries.add(new Entry(new SimpleRawTypeDescriptor(VARCHAR, "VARCHAR2", "NVARCHAR", "NVARCHAR2", "VARCHAR_CASESENSITIVE"),
				new HashMap<TypeParameterKey<?>, Necessity>() {{
					put(TypeParameterKey.SIZE, Necessity.REQUIRED);
			}}));
		typeEntries.add(new Entry(new SimpleRawTypeDescriptor(CHARACTER),
				new HashMap<TypeParameterKey<?>, Necessity>() {{
					put(TypeParameterKey.SIZE, Necessity.REQUIRED);
			}}));
		typeEntries.add(new Entry(new SimpleRawTypeDescriptor(BLOB),
				new HashMap<TypeParameterKey<?>, Necessity>() {{
					put(TypeParameterKey.PRECISION, Necessity.OPTIONAL);
			}}));
		typeEntries.add(new Entry(new SimpleRawTypeDescriptor(CLOB),
				new HashMap<TypeParameterKey<?>, Necessity>() {{
					put(TypeParameterKey.PRECISION, Necessity.OPTIONAL);
			}}));
		typeEntries.add(new Entry(new SimpleRawTypeDescriptor(OTHER, "UUID")));
		// CHECKSTYLE:ON
		// FORMAT-ON
	}
	
	
	/**
	 * インスタンスを生成する。
	 */
	public H2Dialect() {
		super("jdbc:h2:", typeEntries);
	}
	
	public DatabaseMetadataParser getDatabaseMetadataParser() {
		return new DefaultDatabaseMetadataParser(this);
	}
	
	public String getName() {
		return "H2Database";
	}
	
	public SqlEmitter getSqlEmitter() {
		return new DefaultSqlEmitter(this);
	}
	
	@Override
	public Validator getValidator() {
		CompositeValidator validator = (CompositeValidator) super.getValidator();
		validator.getValidators().add(new H2IdentifierValidator());
		return validator;
	}
}
