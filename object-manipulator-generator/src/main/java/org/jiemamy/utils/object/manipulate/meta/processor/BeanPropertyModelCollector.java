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
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.sun.mirror.apt.AnnotationProcessorEnvironment;
import com.sun.mirror.declaration.MethodDeclaration;
import com.sun.mirror.declaration.Modifier;
import com.sun.mirror.declaration.ParameterDeclaration;
import com.sun.mirror.declaration.TypeDeclaration;
import com.sun.mirror.type.TypeMirror;
import com.sun.mirror.type.VoidType;

import org.jiemamy.utils.object.manipulate.meta.model.BeanPropertyModel;
import org.jiemamy.utils.object.manipulate.meta.model.PropertyModel;

/**
 * Bean形式の{@link MethodDeclaration}を収集する。
 * 
 * @version $Date$
 * @author Suguru ARAKAWA
 */
@SuppressWarnings("restriction")
public class BeanPropertyModelCollector implements PropertyModelCollector {
	
	private static String stripVerb(String name, String verb) {
		if (name.startsWith(verb) == false) {
			return null;
		}
		if (name.length() == verb.length()) {
			return null;
		}
		char first = name.charAt(verb.length());
		if (Character.isJavaIdentifierStart(first) == false) {
			return null;
		}
		return Character.toLowerCase(first) + name.substring(verb.length() + 1);
	}
	

	private AnnotationProcessorEnvironment environment;
	
	private Map<String, BeanPropertyModel> results;
	

	/**
	 * インスタンスを生成する。
	 * 
	 * @param environment 環境オブジェクト
	 */
	public BeanPropertyModelCollector(AnnotationProcessorEnvironment environment) {
		this.environment = environment;
		results = new TreeMap<String, BeanPropertyModel>();
	}
	
	public void collect(TypeDeclaration type) {
		Collection<? extends MethodDeclaration> methods = type.getMethods();
		Map<NameAndType, MethodDeclaration> getters = findGetters(methods);
		if (getters.isEmpty()) {
			return;
		}
		
		Map<NameAndType, MethodDeclaration> setters = findSetters(methods);
		for (Map.Entry<NameAndType, MethodDeclaration> entry : getters.entrySet()) {
			NameAndType signature = entry.getKey();
			if (results.containsKey(signature.name)) {
				continue;
			}
			MethodDeclaration getter = entry.getValue();
			MethodDeclaration setter = setters.get(signature);
			BeanPropertyModel property = BeanPropertyModel.of(environment, signature.name, getter, setter);
			results.put(signature.name, property);
		}
	}
	
	public Collection<? extends PropertyModel> getResults() {
		return new ArrayList<PropertyModel>(results.values());
	}
	
	private Map<NameAndType, MethodDeclaration> findGetters(Collection<? extends MethodDeclaration> methods) {
		Map<NameAndType, MethodDeclaration> corresponds = new HashMap<NameAndType, MethodDeclaration>();
		for (MethodDeclaration method : methods) {
			NameAndType getter = getAsGetter(method);
			if (getter != null) {
				corresponds.put(getter, method);
			}
		}
		return corresponds;
	}
	
	private Map<NameAndType, MethodDeclaration> findSetters(Collection<? extends MethodDeclaration> methods) {
		Map<NameAndType, MethodDeclaration> corresponds = new HashMap<NameAndType, MethodDeclaration>();
		for (MethodDeclaration method : methods) {
			NameAndType setter = getAsSetter(method);
			if (setter != null) {
				corresponds.put(setter, method);
			}
		}
		return corresponds;
	}
	
	private NameAndType getAsGetter(MethodDeclaration method) {
		if (method.getModifiers().contains(Modifier.PUBLIC) == false) {
			return null;
		}
		if (method.getModifiers().contains(Modifier.STATIC)) {
			return null;
		}
		if (method.getFormalTypeParameters().isEmpty() == false) {
			return null;
		}
		if (method.getReturnType() instanceof VoidType) {
			return null;
		}
		if (method.getParameters().isEmpty() == false) {
			return null;
		}
		String name;
		if (method.getSimpleName().startsWith("is")) {
			name = stripVerb(method.getSimpleName(), "is");
		} else {
			name = stripVerb(method.getSimpleName(), "get");
		}
		if (name == null) {
			return null;
		}
		return new NameAndType(name, method.getReturnType());
	}
	
	private NameAndType getAsSetter(MethodDeclaration method) {
		String name = stripVerb(method.getSimpleName(), "set");
		if (name == null) {
			return null;
		}
		if (method.getModifiers().contains(Modifier.PUBLIC) == false) {
			return null;
		}
		if (method.getModifiers().contains(Modifier.STATIC)) {
			return null;
		}
		if (method.getFormalTypeParameters().isEmpty() == false) {
			return null;
		}
		if ((method.getReturnType() instanceof VoidType) == false) {
			return null;
		}
		if (method.getParameters().size() != 1) {
			return null;
		}
		ParameterDeclaration first = method.getParameters().iterator().next();
		return new NameAndType(name, first.getType());
	}
	

	private static class NameAndType {
		
		public final String name;
		
		public final TypeMirror type;
		

		NameAndType(String name, TypeMirror type) {
			this.name = name;
			this.type = type;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			NameAndType other = (NameAndType) obj;
			if (name.equals(other.name) == false) {
				return false;
			}
			if (type.equals(other.type) == false) {
				return false;
			}
			return true;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + name.hashCode();
			result = prime * result + type.hashCode();
			return result;
		}
	}
}
