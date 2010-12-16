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
package org.jiemamy.utils.object.manipulate;

import org.jiemamy.utils.functor.ApplyException;
import org.jiemamy.utils.functor.Editor;
import org.jiemamy.utils.functor.Functor;

/**
 * オブジェクトが持つプロパティの表現。
 * 
 * @version $Date: 2009-11-20 18:19:07 +0900 (金, 20 11 2009) $
 * @author Suguru ARAKAWA
 * @param <K> プロパティを保持するカインド
 * @param <T> プロパティの型
 */
public interface Property<K, T> extends Functor<T, K>, Editor<K, T> {
	
	/**
	 * 指定のオブジェクトに対し、このプロパティが保持する値を返す。
	 * 
	 * @param object 対象のオブジェクト
	 * @return 対象のオブジェクトが保持するプロパティの値
	 * @throws ApplyException プロパティの値を取得できなかった場合
	 */
	T apply(K object);
	
	/**
	 * 指定のオブジェクトに対し、このプロパティの値を指定の値に変更する。
	 * 
	 * @param object 対象のオブジェクト
	 * @param value 変更する値
	 * @return {@code object}
	 * @throws ApplyException プロパティの値を変更できなかった場合
	 * @throws IllegalArgumentException 引数{@code object}に{@code null}が含まれる場合
	 */
	K edit(K object, T value);
	
	/**
	 * このプロパティの名前を返す。
	 * 
	 * @return このプロパティの名前
	 */
	String name();
}
