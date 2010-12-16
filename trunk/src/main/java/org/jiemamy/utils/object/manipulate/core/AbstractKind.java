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
package org.jiemamy.utils.object.manipulate.core;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.jiemamy.utils.object.manipulate.Kind;
import org.jiemamy.utils.object.manipulate.Property;

/**
 * {@link Kind}の骨格実装。
 * 
 * @version $Date: 2009-11-20 18:19:07 +0900 (金, 20 11 2009) $
 * @author Suguru ARAKAWA
 * @param <T> 対応するJava上の型
 */
public abstract class AbstractKind<T> implements Kind<T> {
	
	private Map<String, Property<T, ?>> propertyMap = null;
	

	public final Collection<? extends Property<T, ?>> properties() {
		return prepare().values();
	}
	
	public final Property<T, ?> property(String name) {
		return prepare().get(name);
	}
	
	/**
	 * このカインドが保持するプロパティの一覧を返す。
	 * 
	 * <p>{@link AbstractKind}がプロパティを用意する際に一度だけ呼び出される。</p>
	 * 
	 * @return このカインドが保持するプロパティの一覧
	 */
	protected abstract Collection<? extends Property<T, ?>> getProperties();
	
	private synchronized Map<String, Property<T, ?>> prepare() {
		if (propertyMap != null) {
			return propertyMap;
		}
		propertyMap = new LinkedHashMap<String, Property<T, ?>>();
		for (Property<T, ?> p : getProperties()) {
			propertyMap.put(p.name(), p);
		}
		return propertyMap;
	}
}
