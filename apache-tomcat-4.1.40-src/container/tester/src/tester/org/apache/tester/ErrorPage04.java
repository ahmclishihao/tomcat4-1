/*
 * Copyright 1999, 2000, 2001 ,2004 The Apache Software Foundation.
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

package org.apache.tester;


import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * Part 4 of the ErrorPage Tests.  Should be mapped by the container when
 * the ErrorPage01 servlet returns the appropriate exception.
 *
 * @author Craig R. McClanahan
 * @version $Revision: 289023 $ $Date: 2004-08-26 23:06:34 +0100 (Thu, 26 Aug 2004) $
 */

public class ErrorPage04 extends HttpServlet {


    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {

        response.reset();
        response.setContentType("text/plain");
        PrintWriter writer = response.getWriter();

        // Accumulate all the reasons this request might fail
        ServletException exception = null;
        StringBuffer sb = new StringBuffer();
        Object value = null;

        value = request.getAttribute("javax.servlet.error.exception");
        if (value == null)
            sb.append(" exception is missing/");
        else if (!(value instanceof org.apache.tester.TesterException)) {
            sb.append(" exception class is ");
            sb.append(value.getClass().getName());
            sb.append("/");
        } else {
            TesterException te = (TesterException) value;
            if (!"ErrorPage03 Threw Exception".equals(te.getMessage())) {
                sb.append(" exception message is ");
                sb.append(te.getMessage());
                sb.append("/");
            }
        }

        value = request.getAttribute("javax.servlet.error.exception_type");
        if (value == null)
            sb.append(" exception_type is missing/");
        else if (!(value instanceof Class)) {
            sb.append(" exception_type class is ");
            sb.append(value.getClass().getName());
            sb.append("/");
        } else {
            Class clazz = (Class) value;
            if (!"org.apache.tester.TesterException".equals(clazz.getName())) {
                sb.append(" exception_type class is ");
                sb.append(clazz.getName());
                sb.append("/");
            }
        }

        value = request.getAttribute("javax.servlet.error.message");
        if (value == null)
            sb.append(" message is missing/");
        else if (!(value instanceof String)) {
            sb.append(" message class is ");
            sb.append(value.getClass().getName());
            sb.append("/");
        } else {
            String message = (String) value;
            if (!"ErrorPage03 Threw Exception".equals(message)) {
                sb.append(" message is ");
                sb.append(message);
                sb.append("/");
            }
        }

        value = request.getAttribute("javax.servlet.error.request_uri");
        if (value == null)
            sb.append(" request_uri is missing/");
        else if (!(value instanceof String)) {
            sb.append(" request_uri class is ");
            sb.append(value.getClass().getName());
            sb.append("/");
        } else {
            String request_uri = (String) value;
            String test1 = request.getContextPath() + "/ErrorPage03";
            String test2 = request.getContextPath() + "/WrappedErrorPage03";
            if (!request_uri.equals(test1) && !request_uri.equals(test2)) {
                sb.append(" request_uri is ");
                sb.append(request_uri);
                sb.append("/");
            }
        }

        value = request.getAttribute("javax.servlet.error.servlet_name");
        if (value == null)
            sb.append(" servlet_name is missing/");
        else if (!(value instanceof String)) {
            sb.append(" servlet_name class is ");
            sb.append(value.getClass().getName());
            sb.append("/");
        } else {
            String servlet_name = (String) value;
            if (!"ErrorPage03".equals(servlet_name)) {
                sb.append(" servlet_name is ");
                sb.append(servlet_name);
                sb.append("/");
            }
        }

        // Report ultimate success or failure
        if (sb.length() < 1)
            writer.println("ErrorPage04 PASSED");
        else
            writer.println("ErrorPage04 FAILED -" + sb.toString());

        while (true) {
            String message = StaticLogger.read();
            if (message == null)
                break;
            writer.println(message);
        }
        StaticLogger.reset();

    }


}
