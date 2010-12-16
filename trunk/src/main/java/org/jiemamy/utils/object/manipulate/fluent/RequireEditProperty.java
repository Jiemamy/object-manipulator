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

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;

import org.jiemamy.utils.object.manipulate.Kind;
import org.jiemamy.utils.object.manipulate.Property;

/**
 * 操作対象にする個々のプロパティを指定する。
 * 
 * @version $Date: 2009-11-20 18:19:07 +0900 (金, 20 11 2009) $
 * @author Suguru ARAKAWA
 * @param <D> 操作対象の型
 * @param <S> 操作引数の型
 */
public class RequireEditProperty<D, S> {
	
	private Kind<D> destinationKind;
	
	private Kind<S> sourceKind;
	
	private PropertyEditNode<D, S> history;
	

	/**
	 * インスタンスを生成する。
	 * 
	 * @param destinationKind 操作対象のカインド
	 * @param sourceKind 操作引数のカインド
	 * @throws IllegalArgumentException 引数に{@code null}が含まれる場合
	 */
	public RequireEditProperty(Kind<D> destinationKind, Kind<S> sourceKind) {
		if (destinationKind == null) {
			throw new IllegalArgumentException("destinationKind is null"); //$NON-NLS-1$
		}
		if (sourceKind == null) {
			throw new IllegalArgumentException("sourceKind is null"); //$NON-NLS-1$
		}
		this.destinationKind = destinationKind;
		this.sourceKind = sourceKind;
	}
	
	/**
	 * これまでに{@code that()}で指定されていないプロパティ全てを指定する。
	 * 
	 * @return 残りのプロパティに対する操作の指定
	 */
	public RequireEditRestProperties<D, S> otherProperties() {
		List<PropertyEdit<D, S, ?>> edits;
		if (history == null) {
			edits = Collections.emptyList();
		} else {
			edits = history.toList();
		}
		return new RequireEditRestProperties<D, S>(destinationKind, sourceKind, edits);
	}
	
	/**
	 * 操作対象のプロパティを指定する。
	 * 
	 * @param <P> プロパティの型
	 * @param property プロパティ
	 * @return このプロパティに代入する値の設定
	 * @throws IllegalArgumentException 引数に{@code null}が含まれる場合
	 */
	public <P>RequireEditPropertyArgument<D, S, P> that(Property<? super D, P> property) {
		if (property == null) {
			throw new IllegalArgumentException("property is null"); //$NON-NLS-1$
		}
		return new RequireEditPropertyArgument<D, S, P>(this, property);
	}
	
	/**
	 * 操作対象のプロパティをプロパティ名で指定する。
	 * 
	 * <p>このメソッドを利用した場合、プロパティの設定時に型のミスマッチが発生する場合がある。</p>
	 * 
	 * @param propertyName 対象のプロパティ名
	 * @return このプロパティに代入する値の設定
	 * @throws IllegalArgumentException 引数に{@code null}が含まれる場合、または指定の名前のプロパティが存在しない場合
	 */
	@SuppressWarnings("unchecked")
	public RequireEditPropertyArgument<D, S, Object> that(String propertyName) {
		if (propertyName == null) {
			throw new IllegalArgumentException("propertyName is null"); //$NON-NLS-1$
		}
		Property<D, ?> property = destinationKind.property(propertyName);
		if (property == null) {
			throw new IllegalArgumentException(MessageFormat.format("The property {0} is not found", propertyName,
					property));
		}
		Property<D, Object> unchecked = (Property<D, Object>) property;
		return that(unchecked);
	}
	
	RequireEditProperty<D, S> chain(PropertyEdit<D, S, ?> edit) {
		RequireEditProperty<D, S> next = new RequireEditProperty<D, S>(destinationKind, sourceKind);
		next.history = new PropertyEditNode<D, S>(this.history, edit);
		return next;
	}
}
