/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hadoop.hive.ql.processors;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hive.ql.exec.Utilities;
import org.apache.hadoop.hive.ql.parse.VariableSubstitution;
import org.apache.hadoop.hive.ql.session.SessionState;
import org.apache.hadoop.hive.ql.session.SessionState.LogHelper;

/**
 * DeleteResourceProcessor.
 *
 */
public class DeleteResourceProcessor implements CommandProcessor {

  public static final Log LOG = LogFactory.getLog(DeleteResourceProcessor.class.getName());
  public static final LogHelper console = new LogHelper(LOG);

  public void init() {
  }

  public CommandProcessorResponse run(String command) {
    SessionState ss = SessionState.get();
    command = new VariableSubstitution().substitute(ss.getConf(),command);
    String[] tokens = command.split("\\s+");

    SessionState.ResourceType t;
    if (tokens.length < 1
        || (t = SessionState.find_resource_type(tokens[0])) == null) {
      console.printError("Usage: delete ["
          + StringUtils.join(SessionState.ResourceType.values(), "|")
          + "] <value> [<value>]*");
      return new CommandProcessorResponse(1);
    }

    if (tokens.length >= 2) {
      for (int i = 1; i < tokens.length; i++) {
        ss.delete_resource(t, tokens[i]);
      }
    } else {
      ss.delete_resource(t);
    }

    Thread.currentThread().setContextClassLoader(
        Utilities.addResourceFilesToClassPath(ss.getConf()));

    return new CommandProcessorResponse(0);
  }
}
