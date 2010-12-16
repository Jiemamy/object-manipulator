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

import com.sun.mirror.type.TypeMirror;

/**
 * プロパティの表現をカプセル化する。
 * 
 * @version $Date: 2009-11-21 23:34:28 +0900 (土, 21 11 2009) $
 * @author Suguru ARAKAWA
 */
@SuppressWarnings("restriction")
public interface PropertyModel {
	
	/**
	 * このプロパティを保持する型を返す。
	 * 
	 * @return このプロパティを保持する型
	 */
	TypeMirror getDeclaredType();
	
	/**
	 * このプロパティの参照先を表現するJavadocの表記を返す。
	 * 
	 * @return このプロパティの参照先を表現するJavadocの表記
	 */
	String getDocReference();
	
	/**
	 * このプロパティの名前を返す。
	 * 
	 * @return このプロパティの名前
	 */
	String getName();
	
	/**
	 * プロパティを読み出す処理の本体を返す。
	 * 
	 * @param object プロパティを保持する式の表現(PrimaryExpression)
	 * @return プロパティを読み出す処理の本体
	 */
	String getReaderBody(String object);
	
	/**
	 * このプロパティの型を返す。
	 * 
	 * @return このプロパティの型
	 */
	TypeMirror getType();
	
	/**
	 * プロパティを読み出す処理の本体を返す。
	 * 
	 * @param object プロパティを保持する式の表現(PrimaryExpression)
	 * @param value 設定する値を保持する式の表現(PrimaryExpression)
	 * @return プロパティを読み出す処理の本体
	 */
	String getWriterBody(String object, String value);
}
