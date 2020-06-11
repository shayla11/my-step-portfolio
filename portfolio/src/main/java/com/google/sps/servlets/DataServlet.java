// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.sps.data.Comment;


/** Servlet that returns some comments from the user. */
@WebServlet("/data")
public class DataServlet extends HttpServlet {

  private ArrayList <String> comments = new ArrayList <String>();
  private ArrayList<Comment> tasks = new ArrayList<Comment>();

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Gson gson = new Gson();
    Query query = new Query("Task").addSort("timestamp", SortDirection.DESCENDING);
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);

	for (Entity entity : results.asIterable()) {
      String text = (String) entity.getProperty("text");
      String name = (String) entity.getProperty("name");
      long timestamp = (long) entity.getProperty("timestamp");
      Comment task = new Comment(name, text, timestamp);
      tasks.add(task);
    }
    response.setContentType("application/json;");
    response.getWriter().println(gson.toJson(tasks));
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String text = getParameter(request, "text-input", "");
    String name = getParameter(request, "name-input", "");
    long timestamp = System.currentTimeMillis();
    Entity taskEntity = new Entity("Task");
    taskEntity.setProperty("text", text);
    taskEntity.setProperty("name", name);
    taskEntity.setProperty("timestamp", timestamp);
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(taskEntity);
    response.sendRedirect("/index.html");
    }

  private String getParameter(HttpServletRequest request, String name, String defaultValue) {
    String value = request.getParameter(name);
    return (value == null) ? defaultValue : value; 
  }
}
