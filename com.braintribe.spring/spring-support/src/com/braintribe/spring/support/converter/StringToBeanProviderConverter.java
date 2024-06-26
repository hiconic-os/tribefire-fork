// ============================================================================
// Copyright BRAINTRIBE TECHNOLOGY GMBH, Austria, 2002-2022
// 
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
// 
//     http://www.apache.org/licenses/LICENSE-2.0
// 
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
// ============================================================================
package com.braintribe.spring.support.converter;

import java.util.function.Supplier;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.core.convert.converter.Converter;

import com.braintribe.spring.support.BeanFactoryProvider;

public class StringToBeanProviderConverter implements BeanFactoryAware, Converter<String, Supplier<?>> {
  private BeanFactory beanFactory;

  public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
    this.beanFactory = beanFactory;
  }

  public Supplier<?> convert(String beanId) {
    if (beanFactory.containsBean(beanId)) {
      BeanFactoryProvider<Object> provider = new BeanFactoryProvider<Object>();
      provider.setBeanFactory(beanFactory);
      provider.setBeanName(beanId);
      return provider;
    } else
      throw new IllegalArgumentException("no bean with id " + beanId + " found");
  }

  public static String getBuildVersion() {
    return "$Build_Version$ $Id: StringToBeanProviderConverter.java 102880 2018-01-18 11:36:53Z roman.kurmanowytsch $";
  }
}
