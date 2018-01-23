/*
 * Copyright 2002-2014 the original author or authors.
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

package com.example;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import it.sauronsoftware.cron4j.Scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;

@Controller
@SpringBootApplication
public class Main {

  public static void main(String[] args) throws Exception {
    SpringApplication.run(Main.class, args);
	Main app = new Main();
	try {
		app.schedulerSimple();
		Thread.sleep(100000000);
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
  }

  @RequestMapping("/")
  String index() {
    return "index";
  }
  
	public void schedulerSimple() {
		Scheduler scheduler = new Scheduler();
		scheduler.schedule("30 * * * *", new TweetTask());
		scheduler.schedule("40 * * * *", new AutoRetweetTask());
		scheduler.schedule("50 * * * *", new AutoRetweetTask());
		scheduler.schedule("00 12 * * *", new AutoFollowTask());
		scheduler.schedule("00 19 * * *", new AutoFollowTask());
		scheduler.schedule("00 01 * * *", new AutoAnFollowTask());
		scheduler.schedule("00 02 * * *", new AutoAnFollowTask());
		scheduler.schedule("00 03 * * *", new AutoAnFollowTask());
		//scheduler.schedule("1 12 * * *", new AIReplyTask());
		scheduler.start();
	}

}
