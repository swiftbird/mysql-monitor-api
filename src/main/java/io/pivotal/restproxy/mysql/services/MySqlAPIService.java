package io.pivotal.restproxy.mysql.services;

import io.pivotal.restproxy.mysql.messages.Credentials;
import io.pivotal.restproxy.mysql.messages.DatabaseInfo;
import io.pivotal.restproxy.mysql.messages.DatabaseInfo.Profiling;
import io.pivotal.restproxy.mysql.messages.GenericResponse;
import io.pivotal.restproxy.mysql.messages.MySqlConfig;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mysql.jdbc.DatabaseMetaData;

@Service
public class MySqlAPIService {

	// A reference to the Spring Application context so we can dynamically bind
	// to beans instead
	// of using dependency injection

	private ApplicationContext ctx;

	private Logger log = LoggerFactory.getLogger(MySqlAPIService.class);

	public ApplicationContext getCtx() {
		return ctx;
	}

	public void setCtx(ApplicationContext ctx) {
		this.ctx = ctx;
	}

	public GenericResponse dump() {

		log.info("Gathering Dump Data");

		String result = "";
		GenericResponse response = new GenericResponse();
		response.setResultCode("200");
		response.setResultMessage("Connection Successful");

		MySqlConfig mysqlConfig = null;

		result += "************************** Environment Vars ***************\n";
		Map<String, String> envs = System.getenv();
		for (String key : envs.keySet()) {
			result += key + " = " + envs.get(key) + "\n";
		}

		result += "************************** Properties ***************\n";
		Properties props = System.getProperties();
		for (Object key : props.keySet()) {
			result += key + " = " + props.get(key) + "\n";
		}

		result += "************************** Map VCAP ***************\n";
		Map<String, Object> vcap = mapVCAP(System.getenv().get("VCAP_SERVICES"));
		if (vcap != null) {
			for (String element : vcap.keySet()) {
				result += element + " = " + vcap.get(element) + "\n";
				// if there is a mysql service bound, get the info
				if (element.equals("p-mysql")) {
					System.out
							.println("************* I have a mysql Config! ****");

					ArrayList<Object> a = (ArrayList<Object>) vcap.get(element);
					mysqlConfig = parseConfigFromHash((LinkedHashMap) a.get(0));
					System.out.println(parseConfigFromHash((LinkedHashMap) a
							.get(0)));
				}
			}
		}

		result += "************************** mysqlConfig Parsed ***************\n";
		result += mysqlConfig;

		// System.out.println("Going to return: " + result);
		Credentials creds = mysqlConfig.getCredentials();

		String mysqlURIString = creds.getUri();

		result += "mysql URI is: " + mysqlURIString + "\n";

		ObjectMapper mapper = new ObjectMapper();
		JsonNode subNode = mapper.createObjectNode();

		((ObjectNode) subNode).put("rawdata", JsonEncode(result));

		try {
			log.debug(mapper.writerWithDefaultPrettyPrinter()
					.writeValueAsString(subNode));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		response.setResultData(subNode);

		return response;
	}

	public String dumpString() {

		log.info("Gathering Dump Data");

		String result = "";
		GenericResponse response = new GenericResponse();
		response.setResultCode("200");
		response.setResultMessage("Connection Successful");

		MySqlConfig mysqlConfig = null;

		result += "************************** Environment Vars ***************\n";
		Map<String, String> envs = System.getenv();
		for (String key : envs.keySet()) {
			result += key + " = " + envs.get(key) + "\n";
		}

		result += "************************** Properties ***************\n";
		Properties props = System.getProperties();
		for (Object key : props.keySet()) {
			result += key + " = " + props.get(key) + "\n";
		}

		result += "************************** Map VCAP ***************\n";
		Map<String, Object> vcap = mapVCAP(System.getenv().get("VCAP_SERVICES"));
		if (vcap != null) {
			for (String element : vcap.keySet()) {
				result += element + " = " + vcap.get(element) + "\n";
				// if there is a mysql service bound, get the info
				if (element.equals("p-mysql")) {
					System.out
							.println("************* I have a mysql Config! ****");

					ArrayList<Object> a = (ArrayList<Object>) vcap.get(element);
					mysqlConfig = parseConfigFromHash((LinkedHashMap) a.get(0));
					System.out.println(parseConfigFromHash((LinkedHashMap) a
							.get(0)));
				}
			}
		}

		result += "************************** mysqlConfig Parsed ***************\n";
		result += mysqlConfig;

		// System.out.println("Going to return: " + result);
		Credentials creds = mysqlConfig.getCredentials();

		String mysqlURIString = creds.getJdbcUrl();

		result += "mysql JDBC URI is: " + mysqlURIString + "\n";

		ObjectMapper mapper = new ObjectMapper();
		JsonNode subNode = mapper.createObjectNode();

		((ObjectNode) subNode).put("rawdata", JsonEncode(result));

		try {
			log.debug(mapper.writerWithDefaultPrettyPrinter()
					.writeValueAsString(subNode));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		response.setResultData(subNode);

		return result;
	}

	private Map<String, Object> mapVCAP(String vcapString) {

		Map<String, Object> vcap = null;

		if (vcapString != null) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				vcap = mapper.readValue(vcapString, Map.class);
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			log.error("There was no VCAP environment variable.");
		}

		return vcap;

	}

	private String JsonEncode(String json) {
		json = json.replaceAll("\"", "\\\"");
		return json;
	}

	private MySqlConfig parseConfigFromHash(HashMap jsonString) {

		System.out.println(" Going to try get the config for " + jsonString);
		MySqlConfig rc = new MySqlConfig();

		rc.setCredentials(new Credentials());
		rc.setLabel((String) jsonString.get("label"));
		rc.setName((String) jsonString.get("name"));
		rc.setPlan((String) jsonString.get("plan"));
		HashMap<String, Object> creds = (HashMap<String, Object>) jsonString
				.get("credentials");
		System.out.println("Credentials = "
				+ jsonString.get("credentials").getClass());
		rc.getCredentials().setHostname((String) creds.get("hostname"));
		rc.getCredentials().setPassword((String) creds.get("password"));
		rc.getCredentials().setPort((Integer) creds.get("port"));
		rc.getCredentials().setJdbcUrl((String) creds.get("jdbcUrl"));
		rc.getCredentials().setName((String) creds.get("name"));
		rc.getCredentials().setUri((String) creds.get("uri"));
		rc.getCredentials().setUsername((String) creds.get("username"));

		return rc;

	}

	// private mysqlClient getClient() {
	//
	// MySqlConfig mysqlConfig = null;
	// MySqlConfig mysqlClient = null;
	// Map<String, Object> vcap = mapVCAP(System.getenv().get("VCAP_SERVICES"));
	// if (vcap != null) {
	// for (String element : vcap.keySet()) {
	//
	// // if there is a mysql service bound, get the info
	// if (element.equals("p-mysql")) {
	// log.debug("I have a mysql Config! ****");
	//
	// ArrayList<Object> a = (ArrayList<Object>) vcap.get(element);
	// mysqlConfig = getMySqlConfig((LinkedHashMap) a.get(0));
	//
	// }
	// }
	//
	// Credentials creds = mysqlConfig.getCredentials();
	//
	// String mysqlURIString = "mysql://" + creds.getPassword() + "@"
	// + creds.getHost() + ":" + creds.getPort();
	// mysqlURI mysqlUri = mysqlURI.create(mysqlURIString);
	// mysqlClient = mysqlClient.create(mysqlUri);
	// }
	// return mysqlClient;
	//
	// }

	private MySqlConfig getConfig() {
		MySqlConfig config = null;

		Map<String, Object> vcap = mapVCAP(System.getenv().get("VCAP_SERVICES"));
		if (vcap != null) {
			for (String element : vcap.keySet()) {

				// if there is a mysql service bound, get the info
				if (element.equals("p-mysql")) {
					System.out
							.println("************* I have a mysql Config! ****");

					ArrayList<Object> a = (ArrayList<Object>) vcap.get(element);
					config = parseConfigFromHash((LinkedHashMap<?, ?>) a.get(0));
					System.out.println(config.toJSON());
				}
			}
		}

		return config;
	}

	private Connection getConnection() {
		Connection con = null;
		try {
			con = DriverManager.getConnection(getConfig().getCredentials()
					.getJdbcUrl());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return con;
	}

	public GenericResponse ping() {

		GenericResponse response = new GenericResponse();
		response.setResultCode("200");
		response.setResultMessage("Connection Successful");
		Connection con = getConnection();

		if (con != null) {

			Statement stmt = null;
			ResultSet rs = null;
			// Do the test
			try {

				stmt = con.createStatement();

				// stmt.executeUpdate("CREATE TABLE `test` ( `Key` int(11) NOT NULL) ENGINE=MyISAM DEFAULT CHARSET=latin1;");

				rs = stmt
						.executeQuery("SELECT TABLE_SCHEMA, TABLE_NAME FROM information_schema.TABLES");
				while (rs.next()) {
					System.out.println(rs.getString("TABLE_SCHEMA") + "."
							+ rs.getString("TABLE_NAME"));
				}

				rs = stmt
						.executeQuery("SHOW columns FROM information_schema.USER_STATISTICS");
				while (rs.next()) {
					System.out.println(rs.getString(1));

				}

			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} finally {
				if (rs != null) {
					try {
						rs.close();
					} catch (SQLException sqlEx) {
					} // ignore

					rs = null;
				}

				if (stmt != null) {
					try {
						stmt.close();
					} catch (SQLException sqlEx) {
					} // ignore

					stmt = null;
				}
			}
			ObjectMapper mapper = new ObjectMapper();

			String pingResult = "PONG";
			JsonNode subNode = mapper.createObjectNode();
			((ObjectNode) subNode).put("Ping", pingResult);

			// ((ObjectNode) rootNode).put("Memory", subNode);

			try {
				log.debug(mapper.writerWithDefaultPrettyPrinter()
						.writeValueAsString(subNode));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}

			response.setResultData(subNode);

			log.info(response.toJSON());
		} else {
			// There is no mysql service connected
			response.setResultCode("500");
			response.setResultMessage("Error: Could not bind to a mysql Service. Make sure a mysql service is bound to this application.");
			log.error("Error: Could not bind to a mysql Service. Make sure a mysql service is bound to this application.");
		}

		return response;

	}

	public GenericResponse info() {

		GenericResponse response = new GenericResponse();
		response.setResultCode("200");
		response.setResultMessage("Connection Successful");
		DatabaseInfo dbi = new DatabaseInfo();
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.createObjectNode();

		Connection con = getConnection();

		if (con != null) {

			Statement stmt = null;
			ResultSet rs = null;
			// Do the test
			try {

				DatabaseMetaData dm = (DatabaseMetaData) con.getMetaData();

				System.out.println("*********** Database MetaData **********");
				System.out.println("URL: " + dm.getURL());
				System.out.println("Version: " + dm.getDatabaseMajorVersion()
						+ "." + dm.getDatabaseMinorVersion());
				System.out.println("Product: " + dm.getDatabaseProductName());
				System.out.println("Product Version: "
						+ dm.getDatabaseProductVersion());


				JsonNode metaSubnode = mapper.createObjectNode();
				
				((ObjectNode) metaSubnode).put("URL",dm.getURL());
				((ObjectNode) metaSubnode).put("Product",dm.getDatabaseProductName());
				((ObjectNode) metaSubnode).put("Version",dm.getDatabaseProductVersion());

				((ObjectNode) rootNode).put("DatabaseMetaData", metaSubnode);
				
				stmt = con.createStatement();

				// stmt.executeUpdate("CREATE TABLE `test` ( `Key` int(11) NOT NULL) ENGINE=MyISAM DEFAULT CHARSET=latin1;");

//				System.out.println("****** information_schema ***********");
//				
//				rs = stmt
//						.executeQuery("SELECT TABLE_SCHEMA, TABLE_NAME FROM information_schema.TABLES");
//				while (rs.next()) {
//					System.out.println(rs.getString("TABLE_SCHEMA") + "."
//							+ rs.getString("TABLE_NAME"));
//				}
				
				System.out.println("****** performance_schema ***********");
				rs = stmt
						.executeQuery("SHOW STATUS LIKE 'perf%'");
				JsonNode perfSubnode = mapper.createObjectNode();
				while (rs.next()) {
					System.out.println(rs.getString(1) + " = " + rs.getString(2));
										
					((ObjectNode) perfSubnode).put(rs.getString(1), rs.getString(2));
							
				}
				
				((ObjectNode) rootNode).put("Performance", perfSubnode);

//				System.out.println("****** USER_STATISTICS ***********");
//				rs = stmt.executeQuery("select count(*) from information_schema.USER_STATISTICS");
//				while (rs.next()) {
//					System.out.println("Count: " + rs.getString(1));
//				}
//				rs = stmt
//						.executeQuery("SHOW columns FROM information_schema.USER_STATISTICS");
//				while (rs.next()) {
//					System.out.println(rs.getString(1));
//
//				}

//				System.out.println("****** PROFILING ***********");
//				rs = stmt.executeQuery("select count(*) from information_schema.PROFILING");
//				while (rs.next()) {
//					System.out.println("Count: " + rs.getString(1));
//				}
//				
//				String profileQuery = "SELECT * FROM information_schema.PROFILING";
//				rs = stmt
//						.executeQuery(profileQuery);
//				System.out.println("Profiling: " + rs.first());
//				while (rs.next()) {
//					Profiling p = dbi.getProfiling();
//					p.setBlock_ops_in(rs.getString("BLOCK_OPS_IN"));
//					p.setBlock_ops_out(rs.getString("BLOCK_OPS_OUT"));
//					p.setContext_involuntary(rs.getString("CONTEXT_INVOLUNTARY"));
//					p.setContext_voluntary(rs.getString("CONTEXT_VOLUNTARY"));
//					p.setCpu_system(rs.getString("CPU_SYSTEM"));
//					p.setCpu_user(rs.getString("CPU_USER"));
//					p.setDuration(rs.getString("DURATION"));
//					p.setMessages_received(rs.getString("MESSAGES_RECEIVED"));
//					p.setMessages_sent(rs.getString("MESSAGES_SENT"));
//					p.setPage_faults_major(rs.getString("PAGE_FAULTS_MAJOR"));
//					p.setPage_faults_minor(rs.getString("PAGE_FAULTS_MINOR"));
//					p.setQuery_id(rs.getString("QUERY_ID"));
//					p.setSeq(rs.getString("SEQ"));
//					
//					
//					System.out.println(rs.getString(1));
//
//				}

				System.out.println("****** GLOBAL_STATUS ***********");
				rs = stmt.executeQuery("select * from information_schema.GLOBAL_STATUS");
				
				JsonNode globalSubnode = mapper.createObjectNode();
				while (rs.next()) {
					
					System.out.println(rs.getString("VARIABLE_NAME") + " = " + rs.getString("VARIABLE_VALUE"));
					((ObjectNode) globalSubnode).put(rs.getString("VARIABLE_NAME"),rs.getString("VARIABLE_VALUE"));
				}
				
				((ObjectNode) rootNode).put("GlobalStatus", globalSubnode);
//				rs = stmt
//						.executeQuery("SHOW columns FROM information_schema.GLOBAL_STATUS");
//				while (rs.next()) {
//
//					System.out.println(rs.getString(1));
//
//				}
//
//				System.out.println("****** CLIENT_STATISTICS ***********");
//				rs = stmt.executeQuery("select count(*) from information_schema.CLIENT_STATISTICS");
//				while (rs.next()) {
//					System.out.println("Count: " + rs.getString(1));
//				}
//				rs = stmt
//						.executeQuery("SHOW columns FROM information_schema.CLIENT_STATISTICS");
//				while (rs.next()) {
//
//					System.out.println(rs.getString(1));
//
//				}

			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} finally {
				if (rs != null) {
					try {
						rs.close();
					} catch (SQLException sqlEx) {
					} // ignore

					rs = null;
				}

				if (stmt != null) {
					try {
						stmt.close();
					} catch (SQLException sqlEx) {
					} // ignore

					stmt = null;
				}
			}

		} else {
			// There is no mysql service connected
			response.setResultCode("500");
			response.setResultMessage("Error: Could not bind to a mysql Service. Make sure a mysql service is bound to this application.");
			log.error("Error: Could not bind to a mysql Service. Make sure a mysql service is bound to this application.");
		}

		// Insert the response JSON
//		ObjectMapper mapper = new ObjectMapper();
//		JsonNode dbiNode = mapper.convertValue(dbi, JsonNode.class);
		response.setResultData(rootNode);
		log.info(response.toJSON());

		return response;

	}

}
