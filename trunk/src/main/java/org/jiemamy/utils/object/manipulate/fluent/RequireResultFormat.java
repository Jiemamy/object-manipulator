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
import org.jiemamy.utils.functor.Generator;
import org.jiemamy.utils.functor.core.Functors;
import org.jiemamy.utils.object.manipulate.Manipulator;
import org.jiemamy.utils.object.manipulate.core.DelegateManipulator;

/**
 * 構築結果の形式を選択する。
 * 
 * @version $Date: 2009-11-20 18:19:07 +0900 (金, 20 11 2009) $
 * @author Suguru ARAKAWA
 * @param <D> 操作対象の型
 * @param <S> 操作引数の型
 */
public class RequireResultFormat<D, S> {
	
	private Editor<D, S> editor;
	

	/**
	 * インスタンスを生成する。
	 * 
	 * @param editor ここまでに生成した操作器
	 * @throws IllegalArgumentException 引数に{@code null}が含まれる場合
	 */
	public RequireResultFormat(Editor<D, S> editor) {
		if (editor == null) {
			throw new IllegalArgumentException("editor is null"); //$NON-NLS-1$
		}
		this.editor = editor;
	}
	
	/**
	 * これまでの記述によって定義された通りの動作を行う操作器を生成し、返す。
	 * 
	 * @return 生成した操作器
	 */
	public Editor<D, S> createEditor() {
		return editor;
	}
	
	/**
	 * これまでの記述によって定義された通りの動作を行う操作器を生成し、返す。
	 * 
	 * <p>返されるオブジェクトは、指定のファクトリによって生成された値をこの操作器によって変換し返すような、
	 * 関数としての機能も持つ。</p>
	 * 
	 * @param factory 関数として利用する際に初期値を生成するファクトリ
	 * @return 生成した操作器
	 * @throws IllegalArgumentException 引数に{@code null}が含まれる場合
	 */
	public Manipulator<D, S> createManipulator(Generator<? extends D> factory) {
		Functor<D, S> functor = Functors.from(editor, factory);
		return new DelegateManipulator<D, S>(editor, functor);
	}
}
