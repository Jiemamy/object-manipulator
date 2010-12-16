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
 * プロパティの形式。
 * 
 * @version $Date: 2009-11-20 22:36:08 +0900 (金, 20 11 2009) $
 * @author Suguru ARAKAWA
 */
public enum PropertyStyle {
	
	/**
	 * {@code public}で指定されたすべてのフィールドを対象にする。
	 */
	FIELDS,

	/**
	 * {@code public}で指定された全ての{@code getter/setter}を対象にする。
	 */
	BEANS,
}
