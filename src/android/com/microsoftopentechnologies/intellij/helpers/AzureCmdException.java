/**
 * Copyright 2014 Microsoft Open Technologies Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.microsoftopentechnologies.intellij.helpers;


import java.io.PrintWriter;
import java.io.StringWriter;

public class AzureCmdException extends Exception {

    public AzureCmdException(String message, String errorLog) {
        super(message);

        mErrorLog = errorLog;
    }

    public AzureCmdException(String message, Exception ex) {
        super(message);

        if(ex instanceof AzureCmdException) {
            mErrorLog = ((AzureCmdException) ex).getErrorLog();
        } else {
            StringWriter sw = new StringWriter();
            PrintWriter writer = new PrintWriter(sw);

            ex.printStackTrace(writer);
            writer.flush();

            mErrorLog = sw.toString();
        }
    }


    private String mErrorLog;

    public String getErrorLog() {
        return mErrorLog;
    }
}
