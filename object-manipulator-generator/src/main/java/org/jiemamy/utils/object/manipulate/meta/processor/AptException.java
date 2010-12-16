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

import com.sun.mirror.util.SourcePosition;

/**
 * 続行不可能なエラー。
 * 
 * @version $Date: 2009-11-21 23:34:28 +0900 (土, 21 11 2009) $
 * @author Suguru ARAKAWA
 */
@SuppressWarnings("restriction")
public class AptException extends RuntimeException {
	
	private static final long serialVersionUID = -3474353154938127180L;
	
	private SourcePosition position;
	

	/**
	 * インスタンスを生成する。
	 * 
	 * @param position エラーが発生した位置
	 * @param message エラーメッセージ
	 */
	public AptException(SourcePosition position, String message) {
		this(position, message, null);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param position エラーが発生した位置
	 * @param message エラーメッセージ
	 * @param cause エラーの原因
	 */
	public AptException(SourcePosition position, String message, Throwable cause) {
		super(message, cause);
		this.position = position;
	}
	
	/**
	 * エラーが発生した位置を返す。
	 * 
	 * @return エラーが発生した位置
	 */
	public SourcePosition getPosition() {
		return position;
	}
}
