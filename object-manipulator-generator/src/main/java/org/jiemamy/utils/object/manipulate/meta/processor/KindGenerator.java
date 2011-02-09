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

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.sun.mirror.apt.AnnotationProcessorEnvironment;
import com.sun.mirror.apt.Filer;
import com.sun.mirror.declaration.PackageDeclaration;
import com.sun.mirror.declaration.TypeParameterDeclaration;

import org.jiemamy.utils.object.manipulate.meta.Names;
import org.jiemamy.utils.object.manipulate.meta.Options;
import org.jiemamy.utils.object.manipulate.meta.model.KindModel;
import org.jiemamy.utils.object.manipulate.meta.model.PropertyModel;

/**
 * {@link KindModel}をソースコードに変換して出力する。
 * 
 * @version $Date$
 * @author Suguru ARAKAWA
 */
@SuppressWarnings("restriction")
public class KindGenerator {
	
	private static List<String> getHeaer(AnnotationProcessorEnvironment environment) {
		String location = Options.HEADER.getOption(environment);
		if (location == null) {
			environment.getMessager().printNotice(
					MessageFormat.format("No source headers (-A{0} is not specified)", Options.HEADER.getOptionName()));
			return Collections.emptyList();
		}
		
		String encoding = Options.ENCODING.getOption(environment);
		if (encoding == null) {
			encoding = Charset.defaultCharset().name();
		} else if (Charset.isSupported(encoding) == false) {
			environment.getMessager().printWarning(
					MessageFormat.format("A charset {0} is not supported, use default ({1})", encoding, Charset
						.defaultCharset().displayName()));
			encoding = Charset.defaultCharset().name();
		}
		File file = new File(location);
		try {
			List<String> lines = new ArrayList<String>();
			Scanner scanner = new Scanner(file, encoding);
			while (scanner.hasNextLine()) {
				lines.add(scanner.nextLine());
			}
			if (scanner.ioException() != null) {
				throw scanner.ioException();
			}
			return lines;
		} catch (IOException e) {
			throw new AptException(null, MessageFormat.format("The header file {0} is not found ({1})",
					file.getAbsolutePath(), e.getMessage() == null ? e.toString() : e.getMessage()));
		}
	}
	

	private AnnotationProcessorEnvironment environment;
	
	private List<String> header;
	

	/**
	 * インスタンスを生成する。
	 * 
	 * @param environment 環境オブジェクト
	 */
	public KindGenerator(AnnotationProcessorEnvironment environment) {
		this.environment = environment;
		header = getHeaer(environment);
	}
	
	/**
	 * <p>TODO
	 * </p>
	 * 
	 * @param kind 生成する対象のモデル
	 * @throws IOException ファイルの書き出しに失敗した場合
	 */
	public void generate(KindModel kind) throws IOException {
		PrintWriter writer = open(kind);
		try {
			writeHeader(writer);
			writePackage(kind, writer);
			writeImports(kind, writer);
			
			writeClassHead(kind, writer);
			for (PropertyModel property : kind.getProperties()) {
				writeProperty(property, writer);
			}
			writeGenerator(kind, writer);
			writeProperties(kind, writer);
			writeUtilities(kind, writer);
			writeClassFoot(kind, writer);
		} finally {
			writer.close();
		}
	}
	
	private PrintWriter open(KindModel kind) throws IOException {
		String fqn;
		if (kind.getPackage().getQualifiedName().length() >= 1) {
			fqn = kind.getPackage().getQualifiedName() + "." + kind.getTargetSimpleName();
		} else {
			fqn = kind.getTargetSimpleName();
		}
		
		Filer filer = environment.getFiler();
		return filer.createSourceFile(fqn);
	}
	
	private String toSimpleName(String qualifiedName) {
		int index = qualifiedName.lastIndexOf('.');
		if (index < 0) {
			return qualifiedName;
		}
		return qualifiedName.substring(index + 1);
	}
	
	private Object typeParameters(List<TypeParameterDeclaration> typeParameters) {
		if (typeParameters.isEmpty()) {
			return "";
		}
		StringBuilder buf = new StringBuilder();
		buf.append("<");
		buf.append(typeParameters.get(0));
		for (int i = 1, n = typeParameters.size(); i < n; i++) {
			buf.append(", ");
			buf.append(typeParameters.get(i));
		}
		buf.append(">");
		return buf.toString();
	}
	
	private void writeClassFoot(KindModel kind, PrintWriter writer) {
		writer.println("}");
	}
	
	private void writeClassHead(KindModel kind, PrintWriter writer) {
		writer.printf("/**%n");
		writer.printf(" * The kind for {@link %s}.%n", kind.getSourceRawType());
		for (TypeParameterDeclaration p : kind.getTypeParameters()) {
			writer.printf(" * @param <%2$s> is same as the %1$s's {@code %2$s}%n", kind.getSourceRawType(),
					p.getSimpleName());
		}
		writer.printf(" */%n");
		writer.printf("public class %s%s extends %s<%s> {%n", kind.getTargetSimpleName(),
				typeParameters(kind.getTypeParameters()), Names.S_ABSTRACT_KIND, kind.getSourceType());
	}
	
	private void writeGenerator(KindModel kind, PrintWriter writer) {
		if (kind.hasPublicDefaultConstructor() == false) {
			return;
		}
		writer.println();
		writer.printf("    /**%n" + "     * Returns a generator using {@link %4$s#%5$s()}.%n"
				+ "     * @return a generator for {@link %4$s}%n" + "     */%n"
				+ "    public %1$s<%3$s> generator() {%n" + "        return new %2$s<%3$s>() {%n"
				+ "            public %3$s generate() {%n" + "                return new %3$s();%n" + "            }%n"
				+ "        };%n" + "    }%n", "org.jiemamy.utils.functor.Generator",
				"org.jiemamy.utils.functor.core.AbstractGenerator", kind.getSourceType(), kind.getSourceRawType(),
				toSimpleName(kind.getSourceRawType()));
	}
	
	private void writeHeader(PrintWriter writer) {
		if (header.isEmpty()) {
			return;
		}
		writer.println("/*");
		for (String line : header) {
			writer.print(" * ");
			writer.println(line);
		}
		writer.println(" */");
	}
	
	private void writeImports(KindModel kind, PrintWriter writer) {
		writer.println("import java.util.*;");
		writer.println();
		writer.println("import " + Names.FUNCTOR_BASE_PACKAGE + ".*;");
		writer.println("import " + Names.FUNCTOR_CORE_PACKAGE + ".*;");
		writer.println("import " + Names.MANIPULATOR_BASE_PACKAGE + ".*;");
		writer.println("import " + Names.MANIPULATOR_CORE_PACKAGE + ".*;");
		writer.println();
	}
	
	private void writePackage(KindModel kind, PrintWriter writer) {
		PackageDeclaration pkg = kind.getPackage();
		if (pkg.getQualifiedName().length() >= 1) {
			writer.printf("package %s;%n", pkg.getQualifiedName());
			writer.println();
		}
	}
	
	private void writeProperties(KindModel kind, PrintWriter writer) {
		writer.println();
		writer.printf("    @Override%n");
		writer
			.printf("    protected Collection<%s<%s, ?>> getProperties() {%n", Names.S_PROPERTY, kind.getSourceType());
		
		writer.printf("        List<%1$s<%2$s, ?>> results = new ArrayList<%1$s<%2$s, ?>>();%n", Names.S_PROPERTY,
				kind.getSourceType());
		for (PropertyModel property : kind.getProperties()) {
			writer.printf("        results.add(this.%s);%n", property.getName());
		}
		writer.printf("        return results;%n");
		writer.printf("    }%n");
	}
	
	private void writeProperty(PropertyModel property, PrintWriter writer) {
		writer.println();
		writer.printf("    /**%n" + "     * The property of %s%n" + "     */%n", property.getDocReference() + ".");
		writer.printf("    public final Property<%1$s, %2$s> %3$s = new AbstractProperty<%1$s, %2$s>(\"%3$s\") {%n"
				+ "        @Override%n" + "        protected %2$s get(%1$s object) throws Exception {%n"
				+ "            %4$s%n" + "        }%n" + "        @Override%n"
				+ "        protected void set(%1$s object, %2$s value) throws Exception {%n" + "            %5$s%n"
				+ "        }%n" + "    };%n", property.getDeclaredType(), property.getType(), property.getName(),
				property.getReaderBody("object"), property.getWriterBody("object", "value"));
	}
	
	private void writeUtilities(KindModel kind, PrintWriter writer) {
		writer.println();
		writer.printf("    /**%n" + "     * Returns the identity function.%n"
				+ "     * @return the identity function%n" + "     */%n"
				+ "    public Functor<%1$s, %1$s> itself() {%n" + "        return Functors.id();%n" + "    }%n",
				kind.getSourceType());
	}
}
