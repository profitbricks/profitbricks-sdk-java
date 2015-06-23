/*
 * Copyright 2015.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.profitbricks.rest.domain.raw;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jasmin.gacic
 */

public class ServersRaw  extends ProfitbricksBaseRaw{

   public List<ServerRaw> items = new ArrayList<ServerRaw>();

   /**
    * @return the items
    */
   public List<ServerRaw> getItems() {
      return items;
   }

   /**
    * @param items the items to set
    */
   public void setItems(List<ServerRaw> items) {
      this.items = items;
   }

}
