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
package org.jiemamy.utils.object.manipulate.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jiemamy.utils.object.manipulate.Kind;
import org.jiemamy.utils.object.manipulate.Property;
import org.jiemamy.utils.object.manipulate.core.AbstractKind;

/**
 * {@code public}フィールドを基にしたカインド。
 * 
 * @version $Date$
 * @author Suguru ARAKAWA
 * @param <K> フィールドを提供するクラスの型
 */
public class FieldProperties<K> extends AbstractKind<K> {
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param <K> フィールドを提供するクラスの型
	 * @param kindClass フィールドを提供するクラス
	 * @return 生成したカインド
	 * @throws IllegalArgumentException 引数に{@code null}が含まれる場合
	 */
	public static <K>Kind<K> of(Class<K> kindClass) {
		return new FieldProperties<K>(kindClass);
	}
	

	private Class<K> kindClass;
	

	/**
	 * インスタンスを生成する。
	 * 
	 * @param kindClass フィールドを提供するクラス
	 * @throws IllegalArgumentException 引数に{@code null}が含まれる場合
	 */
	public FieldProperties(Class<K> kindClass) {
		if (kindClass == null) {
			throw new IllegalArgumentException("kindClass is null"); //$NON-NLS-1$
		}
		this.kindClass = kindClass;
	}
	
	@Override
	protected Collection<? extends Property<K, ?>> getProperties() {
		List<Property<K, ?>> results = new ArrayList<Property<K, ?>>();
		for (Field field : kindClass.getFields()) {
			int modifiers = field.getModifiers();
			if (Modifier.isStatic(modifiers)) {
				continue;
			}
			results.add(new FieldProperty<K, Object>(field));
		}
		return results;
	}
}
