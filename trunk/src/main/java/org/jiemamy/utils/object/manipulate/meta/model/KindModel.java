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
package org.jiemamy.utils.object.manipulate.meta.model;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.sun.mirror.apt.AnnotationProcessorEnvironment;
import com.sun.mirror.declaration.ClassDeclaration;
import com.sun.mirror.declaration.ConstructorDeclaration;
import com.sun.mirror.declaration.EnumDeclaration;
import com.sun.mirror.declaration.Modifier;
import com.sun.mirror.declaration.PackageDeclaration;
import com.sun.mirror.declaration.TypeDeclaration;
import com.sun.mirror.declaration.TypeParameterDeclaration;
import com.sun.mirror.type.DeclaredType;

/**
 * カインドをモデル化したオブジェクト。
 * 
 * @version $Date: 2009-11-21 23:34:28 +0900 (土, 21 11 2009) $
 * @author Suguru ARAKAWA
 */
@SuppressWarnings("restriction")
public class KindModel {
	
	/**
	 * 指定の型に宣言された情報をカインドのモデルとして取り扱う。
	 * 
	 * @param environment 環境オブジェクト
	 * @param type 対象の型
	 * @param properties このカインドに含まれるプロパティ一覧
	 * @return 対応するモデル
	 */
	public static KindModel of(AnnotationProcessorEnvironment environment, TypeDeclaration type,
			Collection<PropertyModel> properties) {
		DeclaredType mirror = Util.toMirror(environment, type);
		return new KindModel(type, mirror, properties);
	}
	

	private TypeDeclaration type;
	
	private DeclaredType mirror;
	
	private Collection<PropertyModel> properties;
	

	private KindModel(TypeDeclaration type, DeclaredType mirror, Collection<PropertyModel> properties) {
		this.type = type;
		this.mirror = mirror;
		this.properties = properties;
	}
	
	/**
	 * このカインドのパッケージ情報を返す。
	 * 
	 * @return カインドのパッケージ情報
	 */
	public PackageDeclaration getPackage() {
		return type.getPackage();
	}
	
	/**
	 * このカインドが公開するプロパティの一覧を返す。
	 * 
	 * @return このカインドが公開するプロパティの一覧
	 */
	public Collection<PropertyModel> getProperties() {
		return properties;
	}
	
	/**
	 * このカインドの元になる型の、型引数を消去した情報を返す。
	 * 
	 * @return 元になる型の消去
	 */
	public String getSourceRawType() {
		return type.getQualifiedName();
	}
	
	/**
	 * このカインドの元になる型を返す。
	 * 
	 * @return このカインドの元になる型
	 */
	public DeclaredType getSourceType() {
		return mirror;
	}
	
	/**
	 * カインドをあらわすクラスの単純名を返す。
	 * 
	 * @return カインドをあらわすクラスの単純名
	 */
	public String getTargetSimpleName() {
		return type.getSimpleName() + "Kind";
	}
	
	/**
	 * 元となる型の型引数宣言一覧を返す。
	 * 
	 * @return 元となる型の型引数宣言一覧
	 */
	public List<TypeParameterDeclaration> getTypeParameters() {
		return new ArrayList<TypeParameterDeclaration>(type.getFormalTypeParameters());
	}
	
	/**
	 * このカインドの対象とするクラスが、引数なしのコンストラクタを公開する場合に{@code true}を返す。
	 * 
	 * @return 引数なしのコンストラクタを公開する場合に{@code true}
	 */
	public boolean hasPublicDefaultConstructor() {
		if ((type instanceof ClassDeclaration) == false) {
			return false;
		}
		if (type instanceof EnumDeclaration) {
			return false;
		}
		ClassDeclaration klass = (ClassDeclaration) type;
		Collection<Modifier> modifiers = klass.getModifiers();
		if (modifiers.contains(Modifier.ABSTRACT)) {
			return false;
		}
		
		for (ConstructorDeclaration ctor : klass.getConstructors()) {
			if (ctor.getParameters().isEmpty() == false) {
				continue;
			}
			if (ctor.getModifiers().contains(Modifier.PUBLIC) == false) {
				return false;
			}
			return true;
		}
		
		if (klass.getConstructors().isEmpty() == false) {
			return false;
		}
		return type.getModifiers().contains(Modifier.PUBLIC);
	}
	
	@Override
	public String toString() {
		return MessageFormat.format("{0}'{'{1}'}'", //$NON-NLS-1$
				mirror, properties);
	}
}
