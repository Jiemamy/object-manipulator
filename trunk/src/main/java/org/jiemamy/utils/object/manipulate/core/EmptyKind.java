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

import java.util.Collection;
import java.util.Collections;

import org.jiemamy.utils.object.manipulate.Property;

/**
 * プロパティを持たないカインド。
 * 
 * @version $Date: 2009-11-20 18:19:07 +0900 (金, 20 11 2009) $
 * @author Suguru ARAKAWA
 * @param <T> 対応するJava上の型
 */
public class EmptyKind<T> extends AbstractKind<T> {
	
	@Override
	protected Collection<? extends Property<T, ?>> getProperties() {
		return Collections.emptySet();
	}
}
