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
package org.jiemamy.utils.object.manipulate.fluent;

import org.jiemamy.utils.functor.Editor;
import org.jiemamy.utils.functor.Functor;
import org.jiemamy.utils.functor.core.Editors;
import org.jiemamy.utils.object.manipulate.Property;

/**
 * プロパティの操作に関するオブジェクト。
 * 
 * @version $Date$
 * @author Suguru ARAKAWA
 * @param <D> プロパティを保持するカインド
 * @param <S> オブジェクト全体を操作する引数の型
 * @param <T> プロパティの型
 */
public class PropertyEdit<D, S, T> {
	
	/**
	 * プロパティの型が不明の場合に、未チェックで代入するようなこのクラスのインスタンスを提供する。
	 * 
	 * @param <D> プロパティを保持するカインド
	 * @param <S> オブジェクト全体を操作する引数の型
	 * @param property 操作対象のプロパティ
	 * @param functor 操作対象に渡される引数を変換する関数
	 * @return 生成したインスタンス
	 * @throws IllegalArgumentException 引数に{@code null}が含まれる場合
	 */
	@SuppressWarnings("unchecked")
	public static <D, S>PropertyEdit<D, S, ?> unchecked(Property<? super D, ?> property, Functor<?, ? super S> functor) {
		Property<? super D, Object> unchecked = (Property<? super D, Object>) property;
		return new PropertyEdit<D, S, Object>(unchecked, functor);
	}
	

	private Property<? super D, T> property;
	
	private Functor<? extends T, ? super S> functor;
	

	/**
	 * インスタンスを生成する。
	 * 
	 * @param property 操作対象のプロパティ
	 * @param functor 操作対象に渡される引数を変換する関数
	 * @throws IllegalArgumentException 引数に{@code null}が含まれる場合
	 */
	public PropertyEdit(Property<? super D, T> property, Functor<? extends T, ? super S> functor) {
		if (property == null) {
			throw new IllegalArgumentException("property is null"); //$NON-NLS-1$
		}
		if (functor == null) {
			throw new IllegalArgumentException("functor is null"); //$NON-NLS-1$
		}
		this.property = property;
		this.functor = functor;
	}
	
	/**
	 * 操作対象のプロパティを返す。
	 * 
	 * @return 操作対象のプロパティ
	 * @throws IllegalArgumentException　引数に{@code null}が含まれる場合
	 */
	public Property<? super D, T> getProperty() {
		return this.property;
	}
	
	/**
	 * 操作をカプセル化した操作器を返す。
	 * 
	 * @return この操作を実際に行う操作器
	 * @throws IllegalArgumentException 引数に{@code null}が含まれる場合
	 */
	public Editor<D, S> toEditor() {
		return Editors.compose(property, functor);
	}
}
