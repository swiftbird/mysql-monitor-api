package io.pivotal.restproxy.mysql.controllers;

import io.pivotal.restproxy.mysql.messages.GenericResponse;
import io.pivotal.restproxy.mysql.services.MySqlAPIService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value = "/admin", produces = { "application/json" })
public class MySqlAPIController {

	@Autowired
	private MySqlAPIService apiService;

	private Logger log = LoggerFactory.getLogger(MySqlAPIController.class);

	// Get the instance of mysql here
	@ApiOperation(value = "Health Check", notes = "Verify mysql is up and accepting connections.")
	@RequestMapping(value = "/_dump", method = RequestMethod.GET)
	public String dump() {
		
		log.info("System Info Dump Started");

		return apiService.dumpString();
	}

	// Ping the cluster
	@ApiOperation(value = "Ping", notes = "Connect to mysql and send a PING")
	@RequestMapping(value = "/ping", method = RequestMethod.GET)
	public GenericResponse ping() {

		log.info("Ping started");

		return apiService.ping();
	}

	@ApiOperation(value = "Info", notes = "Get a full dump of the mysql Service Info")
	@RequestMapping(value = "/info", method = RequestMethod.GET)
	public GenericResponse info() {

		log.info("Info started");

		return apiService.info();
	}
	
//	@ApiOperation(value = "Info", notes = "Get a dump of a specific the mysql Service Info")
//	@RequestMapping(value = "/info/{section}", method = RequestMethod.GET)
//	public GenericResponse info(@ApiParam(value = "The Name of the Agent", required = true) @PathVariable String section) {
//
//		log.info("Info for " + section + " started");
//
//		return apiService.info(section);
//	}
}
