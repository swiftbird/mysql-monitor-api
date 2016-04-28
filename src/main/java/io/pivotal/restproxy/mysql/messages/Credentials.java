package io.pivotal.restproxy.mysql.messages;

public class Credentials {

	private String hostname;

	private String password;

	private int port;

	private String name;

	private String username;

	private String uri;

	private String jdbcUrl;

	public String getHostname() {
		return hostname;
	}

	public String getJdbcUrl() {
		return jdbcUrl;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public int getPort() {
		return port;
	}

	public String getUri() {
		return uri;
	}

	public String getUsername() {
		return username;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "Credentials [hostname=" + hostname + ", password=" + password
				+ ", port=" + port + ", name=" + name + ", username="
				+ username + ", uri=" + uri + ", jdbcUrl=" + jdbcUrl + "]";
	}

	
}
