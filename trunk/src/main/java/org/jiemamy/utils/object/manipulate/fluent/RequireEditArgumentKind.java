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

import org.jiemamy.utils.object.manipulate.Kind;

/**
 * 操作引数のカインドを指定する。
 * 
 * @version $Date: 2009-11-20 18:19:07 +0900 (金, 20 11 2009) $
 * @author Suguru ARAKAWA
 * @param <D> 操作引数の型
 */
public class RequireEditArgumentKind<D> {
	
	private Kind<D> destinationKind;
	

	/**
	 * インスタンスを生成する。
	 * 
	 * @param destinationKind 操作対象のオブジェクトのカインド
	 * @throws IllegalArgumentException 引数に{@code null}が含まれる場合
	 */
	public RequireEditArgumentKind(Kind<D> destinationKind) {
		if (destinationKind == null) {
			throw new IllegalArgumentException("destinationKind is null"); //$NON-NLS-1$
		}
		this.destinationKind = destinationKind;
	}
	
	/**
	 * 操作引数のカインドを指定する。
	 * 
	 * @param <S> 操作引数の型
	 * @param editArgument オブジェクトの操作をする際に引数に利用するカインド
	 * @return IllegalArgumentException 引数に{@code null}が含まれる場合
	 */
	public <S>RequireEditProperty<D, S> using(Kind<S> editArgument) {
		if (editArgument == null) {
			throw new IllegalArgumentException("editArgument is null"); //$NON-NLS-1$
		}
		return new RequireEditProperty<D, S>(destinationKind, editArgument);
	}
}
