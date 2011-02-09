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

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.sun.mirror.apt.AnnotationProcessor;
import com.sun.mirror.apt.AnnotationProcessorEnvironment;
import com.sun.mirror.apt.AnnotationProcessorFactory;
import com.sun.mirror.apt.AnnotationProcessors;
import com.sun.mirror.declaration.AnnotationTypeDeclaration;

import org.jiemamy.utils.object.manipulate.meta.Names;
import org.jiemamy.utils.object.manipulate.meta.Options;

/**
 * {@link RequireKindProcessor}を生成する。
 * 
 * @version $Date$
 * @author Suguru ARAKAWA
 */
@SuppressWarnings("restriction")
public class RequireKindProcessorFactory implements AnnotationProcessorFactory {
	
	private static final Collection<String> SUPPORTED_OPTIONS = Collections.unmodifiableSet(new HashSet<String>(Arrays
		.asList(new String[] {
			Options.HEADER.getOptionName(),
		})));
	

	public AnnotationProcessor getProcessorFor(Set<AnnotationTypeDeclaration> annotations,
			AnnotationProcessorEnvironment environment) {
		AnnotationTypeDeclaration requireKind = find(Names.REQUIRE_KIND, annotations);
		if (requireKind == null) {
			return AnnotationProcessors.NO_OP;
		}
		return new RequireKindProcessor(requireKind, environment);
	}
	
	public Collection<String> supportedAnnotationTypes() {
		return Arrays.asList(new String[] {
			Names.REQUIRE_KIND
		});
	}
	
	public Collection<String> supportedOptions() {
		return SUPPORTED_OPTIONS;
	}
	
	private AnnotationTypeDeclaration find(String qualifiedName, Set<AnnotationTypeDeclaration> annotations) {
		for (AnnotationTypeDeclaration decl : annotations) {
			if (decl.getQualifiedName().equals(Names.REQUIRE_KIND)) {
				return decl;
			}
		}
		return null;
	}
}
