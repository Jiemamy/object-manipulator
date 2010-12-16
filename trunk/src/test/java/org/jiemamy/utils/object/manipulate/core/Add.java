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
package org.jiemamy.utils.object.manipulate.core;

import org.jiemamy.utils.functor.core.AbstractFunctor;

/**
 * 末尾に指定の文字列を追加する、テスト用のファンクタ。
 * 
 * @version $Date$
 * @author Suguru ARAKAWA
 */
public class Add extends AbstractFunctor<String, String> {
	
	private String suffix;
	

	/**
	 * インスタンスを生成する。
	 * @param suffix 追加する文字列
	 */
	public Add(String suffix) {
		this.suffix = suffix;
	}
	
	public String apply(String argument) {
		return argument + suffix;
	}
}
