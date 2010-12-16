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
package org.jiemamy.utils.object.manipulate;

import org.jiemamy.utils.functor.Editor;
import org.jiemamy.utils.functor.Functor;

/**
 * Manipulator.
 * 
 * @version $Date: 2009-11-20 18:19:07 +0900 (金, 20 11 2009) $
 * @author Suguru ARAKAWA
 * @param <D> 操作対象のオブジェクトの型
 * @param <S> 操作引数の型
 */
public interface Manipulator<D, S> extends Functor<D, S>, Editor<D, S> {
	// TODO members 2009/11/20 15:07:05
}
