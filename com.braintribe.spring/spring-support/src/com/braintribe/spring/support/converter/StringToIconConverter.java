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
/**
 * 
 */
package com.braintribe.spring.support.converter;

import java.io.IOException;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.io.Resource;

/**
 *
 */
public class StringToIconConverter implements Converter<String, Icon>, ApplicationContextAware {

  private ApplicationContext applicationContext;

  public StringToIconConverter() {
  }

  public void setApplicationContext(ApplicationContext applicationContext) {
    this.applicationContext = applicationContext;
  }

  public Icon convert(String source) {
    if ((source == null) || (source.trim().length() == 0))
      return null;

    try {
      Resource resource = applicationContext.getResource(source);

      URL url = resource.getURL();
      ImageIcon icon = new ImageIcon(url);

      return icon;
    } catch (IOException e) {
      throw new IllegalArgumentException(String.format("error while building icon url from: %s", source), e);
    }
  }

}
