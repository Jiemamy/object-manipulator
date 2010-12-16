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

import java.util.LinkedList;

import org.jiemamy.utils.object.manipulate.Kind;

/**
 * {@link PropertyEdit}のチェインを保持する。
 * 
 * @version $Date: 2009-11-20 18:19:07 +0900 (金, 20 11 2009) $
 * @author Suguru ARAKAWA
 * @param <D> プロパティを保持する{@link Kind}
 * @param <S> オブジェクト全体を操作する引数の型
 */
public class PropertyEditNode<D, S> {
	
	private PropertyEditNode<D, S> previous;
	
	private PropertyEdit<D, S, ?> value;
	

	/**
	 * インスタンスを生成する。
	 * 
	 * @param previous 直前のノード、先頭の場合は{@code null}
	 * @param value 保持する値
	 * @throws IllegalArgumentException 引数に{@code null}が含まれる場合
	 */
	public PropertyEditNode(PropertyEditNode<D, S> previous, PropertyEdit<D, S, ?> value) {
		if (value == null) {
			throw new IllegalArgumentException("value is null"); //$NON-NLS-1$
		}
		this.previous = previous;
		this.value = value;
	}
	
	/**
	 * この一連のノードが保持する要素をリストに格納して返す。
	 * 
	 * <p>返されるリストは、先頭から順に要素が格納されている。</p>
	 * 
	 * @return それぞれのノード要素を格納したリスト
	 */
	public LinkedList<PropertyEdit<D, S, ?>> toList() {
		LinkedList<PropertyEdit<D, S, ?>> results = new LinkedList<PropertyEdit<D, S, ?>>();
		PropertyEditNode<D, S> node = this;
		while (node != null) {
			results.addFirst(node.value);
			node = node.previous;
		}
		return results;
	}
}
