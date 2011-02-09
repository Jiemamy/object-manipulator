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

import java.util.Collection;

/**
 * {@link Property}を保持する型の表現(カインド)。
 * 
 * @version $Date$
 * @author Suguru ARAKAWA
 * @param <T> 対応するJava上の型
 */
public interface Kind<T> {
	
	/**
	 * このカインドが保持する、すべてのプロパティを返す。
	 * 
	 * @return すべてのプロパティ
	 */
	Collection<? extends Property<T, ?>> properties();
	
	/**
	 * このカインドが保持する、指定の名前を持つプロパティを返す。
	 * 
	 * @param name 対象のプロパティの名称
	 * @return 対応するプロパティ、存在しない場合は{@code null}
	 * @throws IllegalArgumentException 引数に{@code null}が含まれる場合
	 */
	Property<T, ?> property(String name);
}
