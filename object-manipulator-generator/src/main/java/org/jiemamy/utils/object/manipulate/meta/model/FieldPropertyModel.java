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
import com.sun.mirror.declaration.FieldDeclaration;
import com.sun.mirror.declaration.Modifier;
import com.sun.mirror.declaration.TypeDeclaration;
import com.sun.mirror.type.DeclaredType;
import com.sun.mirror.type.TypeMirror;

/**
 * フィールドを対象としたプロパティ。
 * 
 * @version $Date: 2009-11-21 23:34:28 +0900 (土, 21 11 2009) $
 * @author Suguru ARAKAWA
 */
@SuppressWarnings("restriction")
public class FieldPropertyModel implements PropertyModel {
	
	/**
	 * 指定のフィールドに対応するプロパティを生成して返す。
	 * 
	 * @param environment 環境オブジェクト
	 * @param field プロパティとして取り扱うフィールド
	 * @return 対応するプロパティ
	 */
	public static FieldPropertyModel of(AnnotationProcessorEnvironment environment, FieldDeclaration field) {
		TypeDeclaration decl = field.getDeclaringType();
		DeclaredType declaredType = Util.toMirror(environment, decl);
		String name = field.getSimpleName();
		TypeMirror propertyType = field.getType();
		return new FieldPropertyModel(name, declaredType, decl.getQualifiedName(), Util.boxing(environment,
				propertyType), field.getModifiers().contains(Modifier.FINAL));
	}
	

	private String name;
	
	private TypeMirror declaredType;
	
	private TypeMirror propertyType;
	
	private String rawDeclaredType;
	
	private boolean readOnly;
	

	private FieldPropertyModel(String name, TypeMirror declaredType, String rawDeclaredType, TypeMirror propertyType,
			boolean readOnly) {
		this.name = name;
		this.declaredType = declaredType;
		this.propertyType = propertyType;
		this.rawDeclaredType = rawDeclaredType;
		this.readOnly = readOnly;
	}
	
	public TypeMirror getDeclaredType() {
		return declaredType;
	}
	
	public String getDocReference() {
		return MessageFormat.format("'{'@link {0}#{1}'}'{2}", rawDeclaredType, name, readOnly ? " (read only)" : "");
	}
	
	public String getName() {
		return name;
	}
	
	public String getReaderBody(String object) {
		return MessageFormat.format("return {1}.{0};", //$NON-NLS-1$
				name, object);
	}
	
	public TypeMirror getType() {
		return propertyType;
	}
	
	public String getWriterBody(String object, String value) {
		if (readOnly) {
			return MessageFormat.format("throw new UnsupportedOperationException(\"{0}.{1}\");", //$NON-NLS-1$
					declaredType.toString(), name);
		}
		return MessageFormat.format("{1}.{0} = {2};", //$NON-NLS-1$
				name, object, value);
	}
	
	@Override
	public String toString() {
		return MessageFormat.format("{2} {1}.{0}", getType(), getDeclaredType(), getName());
	}
}
