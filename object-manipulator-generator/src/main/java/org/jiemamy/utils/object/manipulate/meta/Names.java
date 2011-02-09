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
package org.jiemamy.utils.object.manipulate.meta;

/**
 * シンボルの名称に関する定数一覧。
 * 
 * @version $Date$
 * @author Suguru ARAKAWA
 */
public interface Names {
	
	/**
	 * 基本パッケージのルート
	 */
	String MANIPULATOR_BASE_PACKAGE = "org.jiemamy.utils.object.manipulate";
	
	/**
	 * 基本実装パッケージのルート
	 */
	String MANIPULATOR_CORE_PACKAGE = MANIPULATOR_BASE_PACKAGE + ".core";
	
	/**
	 * メタ情報パッケージのルート
	 */
	String MANIPULATOR_META_PACKAGE = MANIPULATOR_BASE_PACKAGE + ".meta";
	
	/**
	 * ファンクタに関する基本パッケージのルート
	 */
	String FUNCTOR_BASE_PACKAGE = "org.jiemamy.utils.functor";
	
	/**
	 * ファンクタに関する基本実装パッケージのルート
	 */
	String FUNCTOR_CORE_PACKAGE = FUNCTOR_BASE_PACKAGE + ".core";
	
	/**
	 * このジェネレータがトリガとする注釈の限定名
	 */
	String REQUIRE_KIND = MANIPULATOR_META_PACKAGE + ".RequireKind";
	
	/**
	 * {@link #REQUIRE_KIND}の引数に利用する列挙
	 */
	String PROPERTY_STYLE = MANIPULATOR_META_PACKAGE + ".PropertyStyle";
	
	/**
	 * カインドを表現するオブジェクトの型(単純名)
	 * 
	 * <p>{@link #MANIPULATOR_BASE_PACKAGE}下に配置される。</p>
	 */
	String S_KIND = "AbstractKind";
	
	/**
	 * プロパティを表現するオブジェクトの型(単純名)
	 * 
	 * <p>{@link #MANIPULATOR_BASE_PACKAGE}下に配置される。</p>
	 */
	String S_PROPERTY = "Property";
	
	/**
	 * {@link #S_KIND}の骨格実装の型(単純名)
	 * 
	 * <p>{@link #MANIPULATOR_CORE_PACKAGE}下に配置される。</p>
	 */
	String S_ABSTRACT_KIND = "AbstractKind";
	
	/**
	 * {@link #S_PROPERTY}の骨格実装の型(単純名)
	 * 
	 * <p>{@link #MANIPULATOR_CORE_PACKAGE}下に配置される。</p>
	 */
	String S_ABSTRACT_PROPERTY = "AbstractProperty";
}
