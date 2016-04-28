package io.pivotal.restproxy.mysql.messages;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MessageTester {

	public static void main(String[] args) {
		DatabaseInfo dbi = new DatabaseInfo();

		dbi.getDbMetaData().setProduct("MySQL");
		dbi.getClientStatistics().setAccess_denied("true");
		dbi.getProfiling().setCpu_system("13");
		dbi.getUserStatistics().setBusy_time("100");

		System.out.println(dbi.toJSON());

		GenericResponse response = new GenericResponse();
		response.setResultCode("200");
		response.setResultMessage("success");

		ObjectMapper mapper = new ObjectMapper();
		JsonNode dbiNode = mapper.convertValue(dbi, JsonNode.class);
		response.setResultData(dbiNode);

		System.out.println(response.toJSON());

	}

}
