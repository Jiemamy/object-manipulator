/*
 * Copyright 2007-2010 Jiemamy Project and the Others.
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

import org.jiemamy.utils.object.manipulate.Manipulator;
import org.jiemamy.utils.object.manipulate.fluent.Manipulators;

/**
 * Example.
 * 
 * @version $Id$
 * @author daisuke
 */
public class Main {
	
	/**
	 * Example main.
	 * 
	 * @param args command line arguments.  ignored.
	 */
	public static void main(String[] args) {
		HogeKind<String> hoge = new HogeKind<String>();
		FooKind<String> foo = new FooKind<String>();
		BarKind<String> bar = new BarKind<String>();
		
		// FORMAT-OFF
        Manipulator<Hoge<String>, Foo<String>> editor = Manipulators.edit(hoge)
            .using(foo)
            .that(hoge.value).is(foo.bar.andThen(bar.value))
            .that(hoge.source).is(foo.itself())
            .otherProperties().areCopiedFromArgument()
            .createManipulator(hoge.generator());
        // FORMAT-ON
		
		Foo<String> f = new Foo<String>();
		f.name = "foo";
		f.bar = new Bar<String>();
		f.bar.value = "bar";
		
		Hoge<String> h1 = editor.edit(new Hoge<String>(), f);
		System.out.println(h1.getName());
		System.out.println(h1.getValue());
		
		Hoge<String> h2 = editor.apply(f);
		System.out.println(h2.getName());
		System.out.println(h2.getValue());
	}
}
