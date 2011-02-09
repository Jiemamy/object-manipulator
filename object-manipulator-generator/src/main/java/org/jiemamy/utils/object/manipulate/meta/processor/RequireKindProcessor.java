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

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.mirror.apt.AnnotationProcessor;
import com.sun.mirror.apt.AnnotationProcessorEnvironment;
import com.sun.mirror.declaration.AnnotationMirror;
import com.sun.mirror.declaration.AnnotationTypeDeclaration;
import com.sun.mirror.declaration.AnnotationTypeElementDeclaration;
import com.sun.mirror.declaration.AnnotationValue;
import com.sun.mirror.declaration.Declaration;
import com.sun.mirror.declaration.EnumConstantDeclaration;
import com.sun.mirror.declaration.TypeDeclaration;
import com.sun.mirror.util.DeclarationFilter;

import org.jiemamy.utils.object.manipulate.meta.model.KindModel;
import org.jiemamy.utils.object.manipulate.meta.model.PropertyModel;
import org.jiemamy.utils.object.manipulate.meta.model.PropertyStyle;

/**
 * {@code RequireKind}注釈を処理する。
 * 
 * @version $Date$
 * @author Suguru ARAKAWA
 */
@SuppressWarnings("restriction")
public class RequireKindProcessor implements AnnotationProcessor {
	
	private static AnnotationTypeElementDeclaration getElement(AnnotationTypeDeclaration decl, String name) {
		for (AnnotationTypeElementDeclaration elem : decl.getMethods()) {
			if (elem.getSimpleName().equals(name)) {
				return elem;
			}
		}
		throw new IllegalStateException(MessageFormat.format("Cannot found element {0} in {1}", name,
				decl.getQualifiedName()));
	}
	

	private AnnotationTypeDeclaration annotationDecl;
	
	private AnnotationTypeElementDeclaration valueDecl;
	
	private AnnotationProcessorEnvironment environment;
	

	/**
	 * インスタンスを生成する。
	 * 
	 * @param annotation 対象の注釈宣言一覧
	 * @param environment 環境オブジェクト
	 */
	public RequireKindProcessor(AnnotationTypeDeclaration annotation, AnnotationProcessorEnvironment environment) {
		annotationDecl = annotation;
		this.environment = environment;
		valueDecl = getElement(annotation, "value");
	}
	
	public void process() {
		try {
			run();
		} catch (AptException e) {
			environment.getMessager().printError(e.getPosition(),
					e.getMessage() == null ? e.toString() : e.getMessage());
		}
	}
	
	private boolean checkKind(AnnotationMirror annotation, TypeDeclaration decl) {
		if (decl.getDeclaringType() != null) {
			environment.getMessager().printError(annotation.getPosition(),
					MessageFormat.format("@{0} if for only a top level type", annotation.getAnnotationType()));
			return false;
		}
		return true;
	}
	
	private Collection<TypeDeclaration> findAnnotatedTypes() {
		Collection<Declaration> declarations = environment.getDeclarationsAnnotatedWith(annotationDecl);
		Collection<TypeDeclaration> types =
				DeclarationFilter.getFilter(TypeDeclaration.class).filter(declarations, TypeDeclaration.class);
		return types;
	}
	
	private AnnotationMirror getAnnotation(TypeDeclaration decl) {
		for (AnnotationMirror mirror : decl.getAnnotationMirrors()) {
			if (annotationDecl.equals(mirror.getAnnotationType().getDeclaration()) == false) {
				continue;
			}
			return mirror;
		}
		environment.getMessager().printError(decl.getPosition(),
				MessageFormat.format("Cannot detect annotation {0}", annotationDecl.getQualifiedName()));
		return null;
	}
	
	private List<PropertyStyle> getPropertyStyles(AnnotationMirror mirror) {
		AnnotationValue value = getValue(mirror, valueDecl);
		Object object = value.getValue();
		if (object instanceof Collection<?>) {
			List<PropertyStyle> styles = new ArrayList<PropertyStyle>();
			for (Object o : (Collection<?>) object) {
				PropertyStyle style = resolvePropertyStyle((AnnotationValue) o);
				styles.add(style);
			}
			return styles;
		} else {
			PropertyStyle style = resolvePropertyStyle(value);
			return Collections.singletonList(style);
		}
	}
	
	private AnnotationValue getValue(AnnotationMirror mirror, AnnotationTypeElementDeclaration element) {
		Map<AnnotationTypeElementDeclaration, AnnotationValue> elements = mirror.getElementValues();
		AnnotationValue specified = elements.get(element);
		if (specified != null) {
			return specified;
		}
		AnnotationValue implicit = element.getDefaultValue();
		if (implicit == null) {
			throw new IllegalStateException(MessageFormat.format("{0} has no default values", element.getSimpleName()));
		}
		return implicit;
	}
	
	private Collection<? extends PropertyModel> resolveBeans(TypeDeclaration decl) {
		return resolveProperties(decl, new BeanPropertyModelCollector(environment));
	}
	
	private Collection<? extends PropertyModel> resolveFields(TypeDeclaration decl) {
		return resolveProperties(decl, new FieldPropertyModelCollector(environment));
	}
	
	private Collection<? extends PropertyModel> resolveProperties(TypeDeclaration decl, PropertyModelCollector collector) {
		collector.collect(decl);
		return collector.getResults();
	}
	
	private PropertyStyle resolvePropertyStyle(AnnotationValue scalar) {
		Object value = scalar.getValue();
		if (value instanceof EnumConstantDeclaration) {
			String name = ((EnumConstantDeclaration) value).getSimpleName();
			return PropertyStyle.valueOf(name);
		}
		throw new AptException(scalar.getPosition(), MessageFormat.format("not a enum constant of {0}",
				PropertyStyle.class.getSimpleName()));
	}
	
	private void run() {
		Collection<TypeDeclaration> types = findAnnotatedTypes();
		if (types.isEmpty()) {
			return;
		}
		
		KindGenerator generator = new KindGenerator(environment);
		for (TypeDeclaration type : types) {
			KindModel kind = toKindModel(type);
			if (kind == null) {
				continue;
			}
			try {
				generator.generate(kind);
			} catch (IOException e) {
				environment.getMessager().printError(
						type.getPosition(),
						MessageFormat.format("Cannot create kind {0} into {1}", kind.getTargetSimpleName(),
								kind.getPackage()));
			}
		}
	}
	
	private KindModel toKindModel(TypeDeclaration decl) {
		AnnotationMirror annotation = getAnnotation(decl);
		if (annotation == null) {
			return null;
		}
		
		if (checkKind(annotation, decl) == false) {
			return null;
		}
		
		List<PropertyStyle> styles = getPropertyStyles(annotation);
		Map<String, PropertyModel> properties = new HashMap<String, PropertyModel>();
		for (PropertyStyle style : styles) {
			Collection<? extends PropertyModel> current;
			switch (style) {
				case FIELDS:
					current = resolveFields(decl);
					break;
				case BEANS:
					current = resolveBeans(decl);
					break;
				default:
					throw new AssertionError(style);
			}
			for (PropertyModel property : current) {
				String name = property.getName();
				if (properties.containsKey(name) == false) {
					properties.put(name, property);
				}
			}
		}
		return KindModel.of(environment, decl, properties.values());
	}
}
