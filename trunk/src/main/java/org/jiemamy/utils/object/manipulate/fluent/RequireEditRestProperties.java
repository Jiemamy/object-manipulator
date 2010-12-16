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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jiemamy.utils.functor.Editor;
import org.jiemamy.utils.functor.core.AbstractFunctor;
import org.jiemamy.utils.functor.core.Editors;
import org.jiemamy.utils.functor.java.lang.Iterables;
import org.jiemamy.utils.object.manipulate.Kind;
import org.jiemamy.utils.object.manipulate.Property;

/**
 * 未指定のプロパティに対する操作の記述。
 * 
 * @version $Date: 2009-11-20 18:19:07 +0900 (金, 20 11 2009) $
 * @author Suguru ARAKAWA
 * @param <D> 操作対象の型
 * @param <S> 操作引数の型
 */
public class RequireEditRestProperties<D, S> {
	
	private Kind<D> destinationKind;
	
	private Kind<S> sourceKind;
	
	private List<PropertyEdit<D, S, ?>> edits;
	

	/**
	 * インスタンスを生成する。
	 * 
	 * @param destinationKind 操作対象のカインド
	 * @param sourceKind 操作引数のカインド
	 * @param edits ここまでに宣言された操作の一覧
	 * @throws IllegalArgumentException 引数に{@code null}が含まれる場合
	 */
	public RequireEditRestProperties(Kind<D> destinationKind, Kind<S> sourceKind, List<PropertyEdit<D, S, ?>> edits) {
		if (destinationKind == null) {
			throw new IllegalArgumentException("destinationKind is null"); //$NON-NLS-1$
		}
		if (sourceKind == null) {
			throw new IllegalArgumentException("sourceKind is null"); //$NON-NLS-1$
		}
		if (edits == null) {
			throw new IllegalArgumentException("edits is null"); //$NON-NLS-1$
		}
		this.destinationKind = destinationKind;
		this.sourceKind = sourceKind;
		this.edits = edits;
	}
	
	/**
	 * 未指定のプロパティがあれば、引数のオブジェクトの同名のプロパティからコピーする。
	 * 
	 * <p>コピーするプロパティが存在しない場合、それらは無視される。</p>
	 * 
	 * <p>この処理によって得られる操作器は安全でない。
	 * 操作の際に{@link ClassCastException}がスローされる場合がある。</p>
	 * 
	 * @return 終了状態
	 */
	public RequireResultFormat<D, S> areCopiedFromArgument() {
		List<PropertyEdit<D, S, ?>> editList = new ArrayList<PropertyEdit<D, S, ?>>(edits);
		List<Property<D, ?>> rest = getRest();
		for (Property<D, ?> destinationProperty : rest) {
			String name = destinationProperty.name();
			Property<S, ?> sourceProperty = sourceKind.property(name);
			if (sourceProperty != null) {
				PropertyEdit<D, S, ?> edit = PropertyEdit.unchecked(destinationProperty, sourceProperty);
				editList.add(edit);
			}
		}
		return new RequireResultFormat<D, S>(toEditor(editList));
	}
	
	/**
	 * 未指定のプロパティがあれば、それらを無視する。
	 * 
	 * @return 終了状態
	 */
	public RequireResultFormat<D, S> areLeft() {
		return new RequireResultFormat<D, S>(toEditor(edits));
	}
	
	/**
	 * 未指定のプロパティがあれば、例外を発生させる。
	 * 
	 * @return 終了状態
	 * @throws IllegalStateException 未指定のプロパティが存在する場合
	 */
	public RequireResultFormat<D, S> areNotAllowed() {
		List<Property<D, ?>> rest = getRest();
		if (rest.isEmpty() == false) {
			throw new IllegalStateException(MessageFormat.format("Properties {0} are not targeted",
					Iterables.each(new AbstractFunctor<String, Property<?, ?>>() {
						
						public String apply(Property<?, ?> property) {
							return property.name();
						}
					}).apply(rest)));
		}
		return areLeft();
	}
	
	private List<Property<D, ?>> getRest() {
		Set<String> declared = new HashSet<String>();
		for (PropertyEdit<?, ?, ?> edit : edits) {
			declared.add(edit.getProperty().name());
		}
		List<Property<D, ?>> results = new ArrayList<Property<D, ?>>();
		Collection<? extends Property<D, ?>> properties = destinationKind.properties();
		for (Property<D, ?> property : properties) {
			if (declared.contains(property.name()) == false) {
				results.add(property);
			}
		}
		return results;
	}
	
	private Editor<D, S> toEditor(List<PropertyEdit<D, S, ?>> editList) {
		List<Editor<D, S>> editors = new ArrayList<Editor<D, S>>();
		for (PropertyEdit<D, S, ?> edit : editList) {
			editors.add(edit.toEditor());
		}
		return Editors.compose(editors);
	}
}
