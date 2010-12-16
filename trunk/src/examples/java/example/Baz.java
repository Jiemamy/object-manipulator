/*
 * Copyright 2007-2010 Jiemamy Project and the Others.
 * Created on 2010/12/16
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
 * @version $Id$
 * @author daisuke
 */
@RequireKind(PropertyStyle.BEANS)
public class Baz {
	
	String a;
	
	String b;
	
	int c;
	
	long d;
	

	/**
	 * A用getter。
	 * 
	 * @return A
	 */
	public String getA() {
		return a;
	}
	
	/**
	 * B用getter。
	 * 
	 * @return B
	 */
	public String getB() {
		return b;
	}
	
	/**
	 * C用getter。
	 * 
	 * @return C
	 */
	public int getC() {
		return c;
	}
	
	/**
	 * D用getter。
	 * 
	 * @return D
	 */
	public long getD() {
		return d;
	}
	
	/**
	 * A用setter。
	 * 
	 * @param a A
	 */
	public void setA(String a) {
		this.a = a;
	}
	
	/**
	 * B用setter。
	 * 
	 * @param b B
	 */
	public void setB(String b) {
		this.b = b;
	}
	
	/**
	 * C用setter。
	 * 
	 * @param c C
	 */
	public void setC(int c) {
		this.c = c;
	}
	
	/**
	 * D用setter。
	 * 
	 * @param d D
	 */
	public void setD(long d) {
		this.d = d;
	}
	
}
