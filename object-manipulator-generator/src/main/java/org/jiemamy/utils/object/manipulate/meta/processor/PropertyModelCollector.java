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
package org.jiemamy.utils.object.manipulate.meta.processor;

import java.util.Collection;

import com.sun.mirror.declaration.TypeDeclaration;

import org.jiemamy.utils.object.manipulate.meta.model.PropertyModel;

/**
 * 指定の型に含まれるプロパティを抽出する。
 * 
 * @version $Date$
 * @author Suguru ARAKAWA
 */
@SuppressWarnings("restriction")
public interface PropertyModelCollector {
	
	/**
	 * 指定の型に関連するプロパティを抽出する。
	 * 
	 * @param type 対象の型
	 */
	void collect(TypeDeclaration type);
	
	/**
	 * このオブジェクトが抽出したモデル一覧を返す。
	 * 
	 * @return このオブジェクトが抽出したモデル一覧
	 */
	Collection<? extends PropertyModel> getResults();
}
