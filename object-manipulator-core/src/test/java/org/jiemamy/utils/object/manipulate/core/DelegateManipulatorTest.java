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

import org.jiemamy.utils.functor.Functor;
import org.jiemamy.utils.functor.java.lang.Objects;

/**
 * Test for {@link DelegateManipulator}.
 * 
 * @version $Date$
 * @author Suguru ARAKAWA
 */
public class DelegateManipulatorTest {
	
	/**
	 * Test method for {@link DelegateManipulator#andThen(org.jiemamy.utils.functor.Functor)}.
	 */
	@Test
	public void testAndThen() {
		DelegateManipulator<StringBuilder, String> m =
				new DelegateManipulator<StringBuilder, String>(new Append(), new ToStringBuilder());
		Functor<String, String> functor = m.andThen(Objects.asString()).andThen(new Add(", world!"));
		assertThat(functor.apply("Hello"), is("Hello, world!"));
	}
	
	/**
	 * Test method for {@link DelegateManipulator#apply(java.lang.Object)}.
	 */
	@Test
	public void testApply() {
		DelegateManipulator<StringBuilder, String> m =
				new DelegateManipulator<StringBuilder, String>(new Append(), new ToStringBuilder());
		assertThat(m.apply("hello").toString(), is("hello"));
		assertThat(m.apply("world").toString(), is("world"));
	}
	
	/**
	 * Test method for {@link DelegateManipulator#edit(java.lang.Object, java.lang.Object)}.
	 */
	@Test
	public void testEdit() {
		DelegateManipulator<StringBuilder, String> m =
				new DelegateManipulator<StringBuilder, String>(new Append(), new ToStringBuilder());
		StringBuilder b1 = new StringBuilder();
		StringBuilder edited = m.edit(b1, "hello");
		assertThat(edited.toString(), is("hello"));
	}
}
