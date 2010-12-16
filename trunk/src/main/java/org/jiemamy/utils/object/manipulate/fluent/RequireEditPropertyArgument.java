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

import org.jiemamy.utils.functor.Functor;
import org.jiemamy.utils.functor.Generator;
import org.jiemamy.utils.functor.core.Functors;
import org.jiemamy.utils.object.manipulate.Property;

/**
 * 個々のプロパティの操作引数が要求されている場面。
 * 
 * @version $Date: 2009-11-20 18:19:07 +0900 (金, 20 11 2009) $
 * @author Suguru ARAKAWA
 * @param <D> 操作対象の型
 * @param <S> 操作引数の型
 * @param <P> 操作対象のプロパティの定義型
 */
public class RequireEditPropertyArgument<D, S, P> {
	
	private RequireEditProperty<D, S> environment;
	
	private Property<? super D, P> editing;
	

	/**
	 * インスタンスを生成する。
	 * 
	 * @param environment 操作環境
	 * @param editing 操作中のプロパティ
	 * @throws IllegalArgumentException 引数に{@code null}が含まれる場合
	 */
	public RequireEditPropertyArgument(RequireEditProperty<D, S> environment, Property<? super D, P> editing) {
		if (environment == null) {
			throw new IllegalArgumentException("environment is null"); //$NON-NLS-1$
		}
		if (editing == null) {
			throw new IllegalArgumentException("editing is null"); //$NON-NLS-1$
		}
		this.environment = environment;
		this.editing = editing;
	}
	
	/**
	 * 指定の関数に対して引数オブジェクトを渡した結果が、対象のプロパティに代入されることを宣言する。
	 * 
	 * @param functor 代入する値を生成する関数
	 * @return そのほかのプロパティの指定
	 * @throws IllegalArgumentException 引数に{@code null}が含まれる場合
	 */
	public RequireEditProperty<D, S> is(Functor<? extends P, ? super S> functor) {
		if (functor == null) {
			throw new IllegalArgumentException("functor is null"); //$NON-NLS-1$
		}
		PropertyEdit<D, S, P> edit = new PropertyEdit<D, S, P>(editing, functor);
		return environment.chain(edit);
	}
	
	/**
	 * 指定の生成器が生成する値が、対象のプロパティに代入されることを宣言する。
	 * 
	 * @param generator 代入する値を生成する生成器
	 * @return そのほかのプロパティの指定
	 * @throws IllegalArgumentException 引数に{@code null}が含まれる場合
	 */
	public RequireEditProperty<D, S> is(Generator<? extends P> generator) {
		if (generator == null) {
			throw new IllegalArgumentException("generator is null"); //$NON-NLS-1$
		}
		Functor<? extends P, Object> functor = Functors.from(generator);
		return is(functor);
	}
}
