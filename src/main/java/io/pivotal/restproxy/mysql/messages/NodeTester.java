package io.pivotal.restproxy.mysql.messages;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class NodeTester {

	public static void main(String[] args) {
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.createObjectNode();
		
		String tag = "tag";
		String value = "value";
		
		((ObjectNode) rootNode).put(tag, value);
		
		try {
			System.out.println(mapper.writerWithDefaultPrettyPrinter()
			.writeValueAsString(rootNode));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
