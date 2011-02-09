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

import com.sun.mirror.apt.AnnotationProcessorEnvironment;
import com.sun.mirror.declaration.MethodDeclaration;
import com.sun.mirror.type.DeclaredType;
import com.sun.mirror.type.TypeMirror;
import com.sun.mirror.util.Types;

/**
 * Bean styleのgetter/setterペアのプロパティ。
 * 
 * @version $Date$
 * @author Suguru ARAKAWA
 */
@SuppressWarnings("restriction")
public class BeanPropertyModel implements PropertyModel {
	
	/**
	 * 指定のフィールドに対応するプロパティを生成して返す。
	 * 
	 * @param environment 環境オブジェクト
	 * @param name プロパティの名称
	 * @param getter アクセサとして取り扱うメソッド
	 * @param setter モディファイヤとして取り扱うメソッド、存在しない場合は{@code null}
	 * @return 対応するプロパティ
	 */
	public static BeanPropertyModel of(AnnotationProcessorEnvironment environment, String name,
			MethodDeclaration getter, MethodDeclaration setter) {
		Types types = environment.getTypeUtils();
		String getterName = getter.getSimpleName();
		String setterName = setter == null ? null : setter.getSimpleName();
		DeclaredType declaredType = Util.toMirror(environment, getter.getDeclaringType());
		TypeMirror propertyType = Util.boxing(environment, getter.getReturnType());
		String rawDeclaredType = types.getErasure(declaredType).toString();
		String erasedPropertyType = types.getErasure(getter.getReturnType()).toString();
		environment.getMessager().printNotice(getter.getPosition(), "getter of " + name);
		if (setter != null) {
			environment.getMessager().printNotice(setter.getPosition(), "setter of " + name);
		}
		return new BeanPropertyModel(name, getterName, setterName, declaredType, propertyType, rawDeclaredType,
				erasedPropertyType);
	}
	

	private String name;
	
	private String getterName;
	
	private String setterName;
	
	private TypeMirror declaredType;
	
	private TypeMirror propertyType;
	
	private String rawDeclaredType;
	
	private String erasedPropertyType;
	

	private BeanPropertyModel(String name, String getterName, String setterName, TypeMirror declaredType,
			TypeMirror propertyType, String rawDeclaredType, String erasedPropertyType) {
		this.name = name;
		this.getterName = getterName;
		this.setterName = setterName;
		this.declaredType = declaredType;
		this.propertyType = propertyType;
		this.rawDeclaredType = rawDeclaredType;
		this.erasedPropertyType = erasedPropertyType;
	}
	
	public TypeMirror getDeclaredType() {
		return declaredType;
	}
	
	public String getDocReference() {
		if (isReadOnly()) {
			return MessageFormat.format("'{'@link {0}#{1}()'}' (read only)", rawDeclaredType, getterName);
		}
		return MessageFormat.format("'{'@link {0}#{1}()'}', '{'@link {0}#{2}({3})'}'", rawDeclaredType, getterName,
				setterName, erasedPropertyType);
	}
	
	public String getName() {
		return name;
	}
	
	public String getReaderBody(String object) {
		return MessageFormat.format("return {1}.{0}();", //$NON-NLS-1$
				getterName, object);
	}
	
	public TypeMirror getType() {
		return propertyType;
	}
	
	public String getWriterBody(String object, String value) {
		if (isReadOnly()) {
			return MessageFormat.format("throw new UnsupportedOperationException(\"{0}.{1}\");", //$NON-NLS-1$
					declaredType.toString(), name);
		}
		return MessageFormat.format("{1}.{0}({2});", //$NON-NLS-1$
				setterName, object, value);
	}
	
	private boolean isReadOnly() {
		return setterName == null;
	}
}
