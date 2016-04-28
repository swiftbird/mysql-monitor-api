package io.pivotal.restproxy.mysql.messages;

import java.util.Arrays;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MySqlConfig {

	private String name;

	private String label;

	private String[] tags;

	private String plan;

	private Credentials credentials;

	public Credentials getCredentials() {
		return credentials;
	}

	public String getLabel() {
		return label;
	}

	public String getName() {
		return name;
	}

	public String getPlan() {
		return plan;
	}

	public String[] getTags() {
		return tags;
	}

	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
	}

	@Override
	public String toString() {
		return "mysqlConfig [name=" + name + ", label=" + label + ", tags="
				+ Arrays.toString(tags) + ", plan=" + plan + ", credentials="
				+ credentials + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}

	public String toJSON() {
		String result = "";
		ObjectMapper mapper = new ObjectMapper();

		try {

			String jsonStat = mapper.writerWithDefaultPrettyPrinter()
					.writeValueAsString(this);
			result = jsonStat;

		} catch (Exception e) {

			e.printStackTrace();

		}

		return result;

	}

}
