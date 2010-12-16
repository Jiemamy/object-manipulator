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

import org.jiemamy.utils.functor.core.AbstractGenerator;

/**
 * 空の{@link StringBuilder}を返す。
 * 
 * @version $Date$
 * @author Suguru ARAKAWA
 */
public class NewStringBuilder extends AbstractGenerator<StringBuilder> {
	
	private String init;
	

	/**
	 * インスタンスを生成する。
	 * @param init 初期値
	 */
	public NewStringBuilder(String init) {
		this.init = init;
	}
	
	public StringBuilder generate() {
		return new StringBuilder(init);
	}
}
