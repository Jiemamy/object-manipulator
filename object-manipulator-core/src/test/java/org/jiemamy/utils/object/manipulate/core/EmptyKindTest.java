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

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * Test for {@link EmptyKind}.
 * 
 * @version $Date$
 * @author Suguru ARAKAWA
 */
public class EmptyKindTest {
	
	/**
	 * Test method for {@link AbstractKind#properties()}.
	 */
	@Test
	public void testProperties() {
		EmptyKind<Object> kind = new EmptyKind<Object>();
		assertThat(kind.properties().size(), is(0));
	}
}
