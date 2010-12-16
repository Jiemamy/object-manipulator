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
package org.jiemamy.utils.object.manipulate.reflect;

import java.text.MessageFormat;

import org.jiemamy.utils.functor.ApplyException;
import org.jiemamy.utils.functor.Generator;
import org.jiemamy.utils.functor.core.AbstractGenerator;

/**
 * {@link Class#newInstance()}によってインスタンスを生成する。
 * 
 * @version $Date: 2009-11-20 18:19:07 +0900 (金, 20 11 2009) $
 * @author Suguru ARAKAWA
 * @param <T> 生成するインスタンスの型
 */
public class InstanceGenerator<T> extends AbstractGenerator<T> {
	
	/**
	 * 指定のクラスのインスタンスを生成するような{@link Generator}を返す。
	 * 
	 * @param <T> 生成するインスタンスの型
	 * @param aClass インスタンスを生成する対象のクラス
	 * @return 生成した{@link Generator}
	 * @throws IllegalArgumentException 引数に{@code null}が含まれる場合
	 */
	public static <T>Generator<T> of(Class<T> aClass) {
		return new InstanceGenerator<T>(aClass);
	}
	

	private Class<? extends T> entity;
	

	/**
	 * インスタンスを生成する。
	 * 
	 * @param entity インスタンスを生成する対象のクラス
	 * @throws IllegalArgumentException 引数に{@code null}が含まれる場合
	 */
	public InstanceGenerator(Class<? extends T> entity) {
		if (entity == null) {
			throw new IllegalArgumentException("entity is null"); //$NON-NLS-1$
		}
		this.entity = entity;
	}
	
	public T generate() {
		try {
			return entity.newInstance();
		} catch (Exception e) {
			throw new ApplyException(MessageFormat.format("Cannot create instance for {0}", entity.getName()), e);
		}
	}
}
