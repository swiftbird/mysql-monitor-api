import io.pivotal.restproxy.mysql.messages.Credentials;
import io.pivotal.restproxy.mysql.messages.MySqlConfig;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Tester {

	public static void main(String[] args) {

		String result = "";
		ObjectMapper mapper = new ObjectMapper();
		Credentials c = new Credentials();
		MySqlConfig config = new MySqlConfig();
		config.setCredentials(c);
		
		try {

			String jsonStat = mapper.writerWithDefaultPrettyPrinter()
					.writeValueAsString(config);
			result = jsonStat;
			
			MySqlConfig newconfig = mapper.readValue(jsonStat, MySqlConfig.class);
			
			System.out.println(" New config: " + mapper.writerWithDefaultPrettyPrinter()
					.writeValueAsString(newconfig));

		} catch (Exception e) {

			e.printStackTrace();

		}
		
//		System.out.println(result);

	}

}
