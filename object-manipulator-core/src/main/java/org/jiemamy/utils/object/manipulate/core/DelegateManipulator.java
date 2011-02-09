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

import org.jiemamy.utils.functor.Editor;
import org.jiemamy.utils.functor.Functor;
import org.jiemamy.utils.functor.core.Functors;
import org.jiemamy.utils.object.manipulate.Manipulator;

/**
 * {@link Editor}, {@link Functor}にそれぞれの処理を移譲する{@link Manipulator}の実装。
 * 
 * @version $Date$
 * @author Suguru ARAKAWA
 * @param <D> 操作対象のオブジェクトの型
 * @param <S> 操作引数の型
 */
public class DelegateManipulator<D, S> implements Manipulator<D, S> {
	
	private Editor<D, ? super S> editor;
	
	private Functor<? extends D, ? super S> functor;
	

	/**
	 * インスタンスを生成する。
	 * 
	 * @param editor {@link #edit(Object, Object)}に利用する操作器
	 * @param functor {@link #apply(Object)}に利用する関数
	 * @throws IllegalArgumentException 引数に{@code null}が含まれる場合
	 */
	public DelegateManipulator(Editor<D, ? super S> editor, Functor<? extends D, ? super S> functor) {
		if (editor == null) {
			throw new IllegalArgumentException("editor is null"); //$NON-NLS-1$
		}
		if (functor == null) {
			throw new IllegalArgumentException("functor is null"); //$NON-NLS-1$
		}
		this.editor = editor;
		this.functor = functor;
	}
	
	public <R>Functor<R, S> andThen(Functor<? extends R, ? super D> chain) {
		return Functors.compose(chain, functor);
	}
	
	public D apply(S argument) {
		return functor.apply(argument);
	}
	
	public D edit(D target, S argument) {
		return editor.edit(target, argument);
	}
}
