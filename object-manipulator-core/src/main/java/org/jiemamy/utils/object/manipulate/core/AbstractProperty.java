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

import java.text.MessageFormat;

import org.jiemamy.utils.functor.ApplyException;
import org.jiemamy.utils.functor.EditException;
import org.jiemamy.utils.functor.core.AbstractFunctor;
import org.jiemamy.utils.object.manipulate.Property;

/**
 * {@link Property}の骨格実装。
 * 
 * @version $Date$
 * @author Suguru ARAKAWA
 * @param <K> プロパティを保持するカインド
 * @param <T> プロパティの型
 */
public abstract class AbstractProperty<K, T> extends AbstractFunctor<T, K> implements Property<K, T> {
	
	private String name;
	

	/**
	 * インスタンスを生成する。
	 * 
	 * @param name このプロパティの名前
	 * @throws IllegalArgumentException 引数に{@code null}が含まれる場合
	 */
	public AbstractProperty(String name) {
		if (name == null) {
			throw new IllegalArgumentException("name is null"); //$NON-NLS-1$
		}
		this.name = name;
	}
	
	public final T apply(K object) {
		if (object == null) {
			throw new ApplyException("object is null"); //$NON-NLS-1$
		}
		try {
			return get(object);
		} catch (Exception e) {
			throw new ApplyException(MessageFormat.format("{1}#{0}.get()", name(), object), e);
		}
	}
	
	public final K edit(K object, T value) {
		if (object == null) {
			throw new IllegalArgumentException("object is null"); //$NON-NLS-1$
		}
		try {
			set(object, value);
			return object;
		} catch (Exception e) {
			throw new EditException(MessageFormat.format("{1}#{0}.set({1})", name(), object, value), e);
		}
	}
	
	public String name() {
		return name;
	}
	
	/**
	 * 指定のオブジェクトに対し、このプロパティが保持する値を返す。
	 * 
	 * <p>このメソッドが{@link #apply(Object)}から呼び出されたばあい、
	 * {@code object}が{@code null}でないことが保証される。</p>
	 * 
	 * @param object 対象のオブジェクト
	 * @return 対象のオブジェクトが保持するプロパティの値
	 * @throws Exception プロパティの値を取得できなかった場合
	 */
	protected abstract T get(K object) throws Exception;
	
	/**
	 * 指定のオブジェクトに対し、このプロパティの値を指定の値に変更する。
	 * 
	 * <p>このメソッドが{@link #edit(Object, Object)}から呼び出されたばあい、
	 * {@code object}が{@code null}でないことが保証される。</p>
	 * 
	 * @param object 対象のオブジェクト
	 * @param value 変更する値
	 * @throws Exception プロパティの値を変更できなかった場合
	 */
	protected abstract void set(K object, T value) throws Exception;
}
