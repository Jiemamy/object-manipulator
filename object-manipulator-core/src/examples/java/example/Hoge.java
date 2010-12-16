/*
 * Copyright 2007-2010 Jiemamy Project and the Others.
 *
 * This file is part of Jiemamy.
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
package example;

import org.jiemamy.utils.object.manipulate.meta.PropertyStyle;
import org.jiemamy.utils.object.manipulate.meta.RequireKind;

/**
 * テスト用Bean。
 * 
 * @param <T> valueの型
 * @version $Id$
 * @author daisuke
 */
@RequireKind({
	PropertyStyle.BEANS,
	PropertyStyle.FIELDS
})
public class Hoge<T> {
	
	private String name;
	
	private T value;
	
	/** テスト用フィールド */
	public Foo<T> source;
	

	/**
	 * 名前を取得する。
	 * 
	 * @return 名前
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * 値を取得する。
	 * 
	 * @return 値
	 */
	public T getValue() {
		return this.value;
	}
	
	/**
	 * 名前を設定する。
	 * 
	 * @param name 名前
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 値を設定する。
	 * 
	 * @param value 値
	 */
	public void setValue(T value) {
		this.value = value;
	}
}
