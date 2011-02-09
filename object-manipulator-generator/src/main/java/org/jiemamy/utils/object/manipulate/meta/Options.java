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

import java.util.Map;

import com.sun.mirror.apt.AnnotationProcessorEnvironment;

/**
 * オプション引数。
 * 
 * @version $Date$
 * @author Suguru ARAKAWA
 */
@SuppressWarnings("restriction")
public enum Options {
	
	/**
	 * 生成されるソースコードに付与するヘッダ{@code -Akind.header=/path/to/file}
	 * 
	 * <p>ファイルパスで指定する。</p>
	 */
	HEADER("kind.header", true),

	/**
	 * エンコーディング情報 (javac組み込みオプション)
	 */
	ENCODING("encoding", false);
	
	private String optionName;
	
	private boolean processorSpecific;
	

	private Options(String optionName, boolean processorSpecific) {
		this.optionName = optionName;
		this.processorSpecific = processorSpecific;
	}
	
	/**
	 * このオプションの値を返す。
	 * 
	 * @param environment 実行環境
	 * @return 対応する値、不明の場合は{@code null}
	 */
	public String getOption(AnnotationProcessorEnvironment environment) {
		Map<String, String> options = environment.getOptions();
		if (processorSpecific) {
			return options.get(optionName);
		} else {
			return options.get("-" + optionName);
		}
	}
	
	/**
	 * このオプションの名称を返す。
	 * @return このオプションの名称
	 */
	public String getOptionName() {
		return optionName;
	}
}
