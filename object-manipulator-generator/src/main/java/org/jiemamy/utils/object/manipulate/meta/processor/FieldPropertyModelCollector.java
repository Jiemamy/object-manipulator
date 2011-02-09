/*
 * Copyright 2007-2009 Jiemamy Project and the Others.
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
package org.jiemamy.utils.object.manipulate.meta.processor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Map;
import java.util.TreeMap;

import com.sun.mirror.apt.AnnotationProcessorEnvironment;
import com.sun.mirror.declaration.FieldDeclaration;
import com.sun.mirror.declaration.Modifier;
import com.sun.mirror.declaration.TypeDeclaration;
import com.sun.mirror.util.DeclarationFilter;

import org.jiemamy.utils.object.manipulate.meta.model.FieldPropertyModel;
import org.jiemamy.utils.object.manipulate.meta.model.PropertyModel;

/**
 * {@link FieldDeclaration}を収集する。
 * 
 * @version $Date$
 * @author Suguru ARAKAWA
 */
@SuppressWarnings("restriction")
public class FieldPropertyModelCollector implements PropertyModelCollector {
	
	private static final DeclarationFilter FILTER = DeclarationFilter.FILTER_PUBLIC.and(DeclarationFilter.getFilter(
			EnumSet.of(Modifier.STATIC)).not());
	
	private AnnotationProcessorEnvironment environment;
	
	private Map<String, FieldPropertyModel> results;
	

	/**
	 * インスタンスを生成する。
	 * 
	 * @param environment 環境オブジェクト
	 */
	public FieldPropertyModelCollector(AnnotationProcessorEnvironment environment) {
		this.environment = environment;
		results = new TreeMap<String, FieldPropertyModel>();
	}
	
	public void collect(TypeDeclaration type) {
		for (FieldDeclaration field : FILTER.filter(type.getFields(), FieldDeclaration.class)) {
			FieldPropertyModel model = FieldPropertyModel.of(environment, field);
			String name = model.getName();
			if (results.containsKey(name) == false) {
				results.put(name, model);
			}
		}
	}
	
	public Collection<? extends PropertyModel> getResults() {
		return new ArrayList<PropertyModel>(results.values());
	}
}
