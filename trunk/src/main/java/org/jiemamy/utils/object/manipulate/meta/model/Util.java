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

import java.util.ArrayList;
import java.util.List;

import com.sun.mirror.apt.AnnotationProcessorEnvironment;
import com.sun.mirror.declaration.TypeDeclaration;
import com.sun.mirror.declaration.TypeParameterDeclaration;
import com.sun.mirror.type.DeclaredType;
import com.sun.mirror.type.PrimitiveType;
import com.sun.mirror.type.TypeMirror;
import com.sun.mirror.type.TypeVariable;
import com.sun.mirror.util.Types;

/**
 * このパッケージで利用するユーティリティ群。
 * 
 * @version $Date: 2009-11-21 23:34:28 +0900 (土, 21 11 2009) $
 * @author Suguru ARAKAWA
 */
@SuppressWarnings("restriction")
final class Util {
	
	/**
	 * 指定の型にボクシング変換を行い、変換できない場合はそのまま返す。
	 * 
	 * @param environment 環境オブジェクト
	 * @param type 対象の型
	 * @return 対象の型のラッパー型、存在しない場合は引数の型そのもの
	 */
	public static TypeMirror boxing(AnnotationProcessorEnvironment environment, TypeMirror type) { // CHECKSTYLE IGNORE THIS LINE
		if ((type instanceof PrimitiveType) == false) {
			return type;
		}
		PrimitiveType p = (PrimitiveType) type;
		switch (p.getKind()) {
			case BOOLEAN:
				return getType(environment, Boolean.class);
			case BYTE:
				return getType(environment, Byte.class);
			case CHAR:
				return getType(environment, Character.class);
			case DOUBLE:
				return getType(environment, Double.class);
			case FLOAT:
				return getType(environment, Float.class);
			case INT:
				return getType(environment, Integer.class);
			case LONG:
				return getType(environment, Long.class);
			case SHORT:
				return getType(environment, Short.class);
			default:
				throw new AssertionError(p);
		}
	}
	
	/**
	 * 指定の型宣言に対する型を返す。
	 * 
	 * <p>指定の型宣言に型引数が含まれている場合、返される型はそれらの型引数をもとにした
	 * 型変数でパラメータ化される。</p>
	 * 
	 * @param environment 環境オブジェクト
	 * @param decl 対象の型宣言
	 * @return 対応する型
	 */
	public static DeclaredType toMirror(AnnotationProcessorEnvironment environment, TypeDeclaration decl) {
		Types types = environment.getTypeUtils();
		List<TypeVariable> typeArgs = new ArrayList<TypeVariable>();
		for (TypeParameterDeclaration tp : decl.getFormalTypeParameters()) {
			typeArgs.add(types.getTypeVariable(tp));
		}
		DeclaredType declaredType = types.getDeclaredType(decl, typeArgs.toArray(new TypeVariable[typeArgs.size()]));
		return declaredType;
	}
	
	private static DeclaredType getType(AnnotationProcessorEnvironment environment, Class<?> runtime) {
		String name = runtime.getName();
		TypeDeclaration type = environment.getTypeDeclaration(name);
		return environment.getTypeUtils().getDeclaredType(type);
	}
	
	private Util() {
	}
}
