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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import org.jiemamy.utils.object.manipulate.Manipulator;
import org.jiemamy.utils.object.manipulate.fluent.Manipulators;

/**
 * TODO for daisuke
 * 
 * @version $Id$
 * @author daisuke
 */
public class Sample {
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void testname() throws Exception {
		BazKind bazKind = new BazKind(); // from
		QuxKind quxKind = new QuxKind(); // to
		// FORMAT-OFF
		Manipulator<Qux, Baz> manipulator = Manipulators
				.edit(quxKind).using(bazKind)
				.that(quxKind.ab).is(bazKind.a)
				.otherProperties().areCopiedFromArgument()
				.createManipulator(quxKind.generator());
		// FORMAT-ON
		
		Baz foo = new Baz();
		foo.setA("a");
		foo.setB("b");
		foo.setC(1);
		foo.setD(1000);
		Qux bar = manipulator.apply(foo);
		assertThat(bar.getAb(), is("a"));
		assertThat(bar.getC(), is(1));
		assertThat(bar.getD(), is(1000L));
		
		Qux edit = manipulator.edit(bar, foo);
		assertThat(edit, is(not(sameInstance(bar))));
	}
}
