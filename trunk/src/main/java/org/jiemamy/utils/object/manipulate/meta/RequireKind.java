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

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * {@code Kind}を自動生成すべき対象につけられる注釈。
 * 
 * @version $Date: 2009-11-20 22:36:08 +0900 (金, 20 11 2009) $
 * @author Suguru ARAKAWA
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target({
	ElementType.TYPE
})
public @interface RequireKind {
	
	/**
	 * 対象とするプロパティの種類を指定する (既定値は  {@link PropertyStyle#BEANS})。
	 * 
	 * <p>複数指定された場合、それら全てのプロパティを対象とする。
	 * ただし、それぞれの形式で重複するプロパティが存在する場合、先頭に書いたスタイルで宣言されたプロパティを優先する。</p>
	 */
	PropertyStyle[] value() default {
		PropertyStyle.BEANS
	};
}
