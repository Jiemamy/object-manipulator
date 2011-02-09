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

import org.jiemamy.utils.object.manipulate.core.AbstractProperty;

/**
 * フィールドが表現するプロパティ。
 * 
 * @version $Date$
 * @author Suguru ARAKAWA
 * @param <K> プロパティを保持するカインド
 * @param <T> プロパティの型
 */
public class FieldProperty<K, T> extends AbstractProperty<K, T> {
	
	private static Field check(Field field) {
		if (field == null) {
			throw new IllegalArgumentException("field is null"); //$NON-NLS-1$
		}
		return field;
	}
	

	private Field field;
	

	/**
	 * インスタンスを生成する。
	 * @param field 対象のプロパティを表現するフィールド
	 * @throws IllegalArgumentException 引数に{@code null}が含まれる場合
	 */
	public FieldProperty(Field field) {
		super(check(field).getName());
		this.field = field;
	}
	
	@Override
	protected T get(K object) throws Exception {
		@SuppressWarnings("unchecked")
		T value = (T) field.get(object);
		return value;
	}
	
	@Override
	protected void set(K object, T value) throws Exception {
		field.set(object, value);
	}
}
