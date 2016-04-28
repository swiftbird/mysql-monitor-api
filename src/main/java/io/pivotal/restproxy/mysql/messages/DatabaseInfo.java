package io.pivotal.restproxy.mysql.messages;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DatabaseInfo {

	private DBMetaData dbMetaData;

	private UserStatistics userStatistics;

	private Profiling profiling;

	private ClientStatistics clientStatistics;

	public DatabaseInfo() {
		dbMetaData = new DBMetaData();
		userStatistics = new UserStatistics();
		profiling = new Profiling();
		clientStatistics = new ClientStatistics();
	}

	public ClientStatistics getClientStatistics() {
		return clientStatistics;
	}

	public DBMetaData getDbMetaData() {
		return dbMetaData;
	}

	public Profiling getProfiling() {
		return profiling;
	}

	public UserStatistics getUserStatistics() {
		return userStatistics;
	}

	public void setClientStatistics(ClientStatistics clientStatistics) {
		this.clientStatistics = clientStatistics;
	}

	public void setDbMetaData(DBMetaData dbMetaData) {
		this.dbMetaData = dbMetaData;
	}

	public void setProfiling(Profiling profiling) {
		this.profiling = profiling;
	}

	public void setUserStatistics(UserStatistics userStatistics) {
		this.userStatistics = userStatistics;
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

	class ClientStatistics {

		private String variable_value;

		private String client;

		private String concurrent_connections;

		private String total_connections;

		private String busy_time;

		private String connected_time;

		private String cpu_time;

		private String bytes_received;

		private String bytes_sent;

		private String binlog_bytes_written;

		private String rows_read;

		private String rows_sent;

		private String rows_updated;

		private String update_commands;

		private String rows_deleted;

		private String rows_inserted;

		private String select_commands;

		private String other_commands;

		private String rollback_transactions;

		private String access_denied;

		private String commit_transactions;

		private String denied_connections;

		private String lost_connections;

		private String empty_queries;

		public String getAccess_denied() {
			return access_denied;
		}

		public String getBinlog_bytes_written() {
			return binlog_bytes_written;
		}

		public String getBusy_time() {
			return busy_time;
		}

		public String getBytes_received() {
			return bytes_received;
		}

		public String getBytes_sent() {
			return bytes_sent;
		}

		public String getClient() {
			return client;
		}

		public String getCommit_transactions() {
			return commit_transactions;
		}

		public String getConcurrent_connections() {
			return concurrent_connections;
		}

		public String getConnected_time() {
			return connected_time;
		}

		public String getCpu_time() {
			return cpu_time;
		}

		public String getDenied_connections() {
			return denied_connections;
		}

		public String getEmpty_queries() {
			return empty_queries;
		}

		public String getLost_connections() {
			return lost_connections;
		}

		public String getOther_commands() {
			return other_commands;
		}

		public String getRollback_transactions() {
			return rollback_transactions;
		}

		public String getRows_deleted() {
			return rows_deleted;
		}

		public String getRows_inserted() {
			return rows_inserted;
		}

		public String getRows_read() {
			return rows_read;
		}

		public String getRows_sent() {
			return rows_sent;
		}

		public String getRows_updated() {
			return rows_updated;
		}

		public String getSelect_commands() {
			return select_commands;
		}

		public String getTotal_connections() {
			return total_connections;
		}

		public String getUpdate_commands() {
			return update_commands;
		}

		public String getVariable_value() {
			return variable_value;
		}

		public void setAccess_denied(String access_denied) {
			this.access_denied = access_denied;
		}

		public void setBinlog_bytes_written(String binlog_bytes_written) {
			this.binlog_bytes_written = binlog_bytes_written;
		}

		public void setBusy_time(String busy_time) {
			this.busy_time = busy_time;
		}

		public void setBytes_received(String bytes_received) {
			this.bytes_received = bytes_received;
		}

		public void setBytes_sent(String bytes_sent) {
			this.bytes_sent = bytes_sent;
		}

		public void setClient(String client) {
			this.client = client;
		}

		public void setCommit_transactions(String commit_transactions) {
			this.commit_transactions = commit_transactions;
		}

		public void setConcurrent_connections(String concurrent_connections) {
			this.concurrent_connections = concurrent_connections;
		}

		public void setConnected_time(String connected_time) {
			this.connected_time = connected_time;
		}

		public void setCpu_time(String cpu_time) {
			this.cpu_time = cpu_time;
		}

		public void setDenied_connections(String denied_connections) {
			this.denied_connections = denied_connections;
		}

		public void setEmpty_queries(String empty_queries) {
			this.empty_queries = empty_queries;
		}

		public void setLost_connections(String lost_connections) {
			this.lost_connections = lost_connections;
		}

		public void setOther_commands(String other_commands) {
			this.other_commands = other_commands;
		}

		public void setRollback_transactions(String rollback_transactions) {
			this.rollback_transactions = rollback_transactions;
		}

		public void setRows_deleted(String rows_deleted) {
			this.rows_deleted = rows_deleted;
		}

		public void setRows_inserted(String rows_inserted) {
			this.rows_inserted = rows_inserted;
		}

		public void setRows_read(String rows_read) {
			this.rows_read = rows_read;
		}

		public void setRows_sent(String rows_sent) {
			this.rows_sent = rows_sent;
		}

		public void setRows_updated(String rows_updated) {
			this.rows_updated = rows_updated;
		}

		public void setSelect_commands(String select_commands) {
			this.select_commands = select_commands;
		}

		public void setTotal_connections(String total_connections) {
			this.total_connections = total_connections;
		}

		public void setUpdate_commands(String update_commands) {
			this.update_commands = update_commands;
		}

		public void setVariable_value(String variable_value) {
			this.variable_value = variable_value;
		}

	}

	public class DBMetaData {

		private String URL;

		private String Product;

		private String Version;

		public String getProduct() {
			return Product;
		}

		public String getURL() {
			return URL;
		}

		public String getVersion() {
			return Version;
		}

		public void setProduct(String product) {
			Product = product;
		}

		public void setURL(String uRL) {
			URL = uRL;
		}

		public void setVersion(String version) {
			Version = version;
		}
	}

	public class Profiling {

		private String query_id;

		private String seq;

		private String state;

		private String cpu_system;

		private String duration;

		private String cpu_user;

		private String context_voluntary;

		private String context_involuntary;

		private String block_ops_in;

		private String block_ops_out;

		private String messages_sent;

		private String messages_received;

		private String page_faults_major;

		private String swaps;

		private String page_faults_minor;

		private String source_function;

		public String getBlock_ops_in() {
			return block_ops_in;
		}

		public String getBlock_ops_out() {
			return block_ops_out;
		}

		public String getContext_involuntary() {
			return context_involuntary;
		}

		public String getContext_voluntary() {
			return context_voluntary;
		}

		public String getCpu_system() {
			return cpu_system;
		}

		public String getCpu_user() {
			return cpu_user;
		}

		public String getDuration() {
			return duration;
		}

		public String getMessages_received() {
			return messages_received;
		}

		public String getMessages_sent() {
			return messages_sent;
		}

		public String getPage_faults_major() {
			return page_faults_major;
		}

		public String getPage_faults_minor() {
			return page_faults_minor;
		}

		public String getQuery_id() {
			return query_id;
		}

		public String getSeq() {
			return seq;
		}

		public String getSource_function() {
			return source_function;
		}

		public String getState() {
			return state;
		}

		public String getSwaps() {
			return swaps;
		}

		public void setBlock_ops_in(String block_ops_in) {
			this.block_ops_in = block_ops_in;
		}

		public void setBlock_ops_out(String block_ops_out) {
			this.block_ops_out = block_ops_out;
		}

		public void setContext_involuntary(String context_involuntary) {
			this.context_involuntary = context_involuntary;
		}

		public void setContext_voluntary(String context_voluntary) {
			this.context_voluntary = context_voluntary;
		}

		public void setCpu_system(String cpu_system) {
			this.cpu_system = cpu_system;
		}

		public void setCpu_user(String cpu_user) {
			this.cpu_user = cpu_user;
		}

		public void setDuration(String duration) {
			this.duration = duration;
		}

		public void setMessages_received(String messages_received) {
			this.messages_received = messages_received;
		}

		public void setMessages_sent(String messages_sent) {
			this.messages_sent = messages_sent;
		}

		public void setPage_faults_major(String page_faults_major) {
			this.page_faults_major = page_faults_major;
		}

		public void setPage_faults_minor(String page_faults_minor) {
			this.page_faults_minor = page_faults_minor;
		}

		public void setQuery_id(String query_id) {
			this.query_id = query_id;
		}

		public void setSeq(String seq) {
			this.seq = seq;
		}

		public void setSource_function(String source_function) {
			this.source_function = source_function;
		}

		public void setState(String state) {
			this.state = state;
		}

		public void setSwaps(String swaps) {
			this.swaps = swaps;
		}

	}

	public class UserStatistics {

		private String user;

		private String concurrent_connections;

		private String total_connections;

		private String cpu_time;

		private String connected_time;

		private String busy_time;

		private String bytes_sent;

		private String bytes_received;

		private String binlog_bytes_written;

		private String rows_read;

		private String rows_inserted;

		private String rows_sent;

		private String rows_deleted;

		private String select_commands;

		private String other_commands;

		private String rows_updated;

		private String update_commands;

		private String commit_transactions;

		private String empty_queries;

		private String rollback_transactions;

		private String denied_connections;

		private String lost_connections;

		private String access_denied;

		public String getAccess_denied() {
			return access_denied;
		}

		public String getBinlog_bytes_written() {
			return binlog_bytes_written;
		}

		public String getBusy_time() {
			return busy_time;
		}

		public String getBytes_received() {
			return bytes_received;
		}

		public String getBytes_sent() {
			return bytes_sent;
		}

		public String getCommit_transactions() {
			return commit_transactions;
		}

		public String getConcurrent_connections() {
			return concurrent_connections;
		}

		public String getConnected_time() {
			return connected_time;
		}

		public String getCpu_time() {
			return cpu_time;
		}

		public String getDenied_connections() {
			return denied_connections;
		}

		public String getEmpty_queries() {
			return empty_queries;
		}

		public String getLost_connections() {
			return lost_connections;
		}

		public String getOther_commands() {
			return other_commands;
		}

		public String getRollback_transactions() {
			return rollback_transactions;
		}

		public String getRows_deleted() {
			return rows_deleted;
		}

		public String getRows_inserted() {
			return rows_inserted;
		}

		public String getRows_read() {
			return rows_read;
		}

		public String getRows_sent() {
			return rows_sent;
		}

		public String getRows_updated() {
			return rows_updated;
		}

		public String getSelect_commands() {
			return select_commands;
		}

		public String getTotal_connections() {
			return total_connections;
		}

		public String getUpdate_commands() {
			return update_commands;
		}

		public String getUser() {
			return user;
		}

		public void setAccess_denied(String access_denied) {
			this.access_denied = access_denied;
		}

		public void setBinlog_bytes_written(String binlog_bytes_written) {
			this.binlog_bytes_written = binlog_bytes_written;
		}

		public void setBusy_time(String busy_time) {
			this.busy_time = busy_time;
		}

		public void setBytes_received(String bytes_received) {
			this.bytes_received = bytes_received;
		}

		public void setBytes_sent(String bytes_sent) {
			this.bytes_sent = bytes_sent;
		}

		public void setCommit_transactions(String commit_transactions) {
			this.commit_transactions = commit_transactions;
		}

		public void setConcurrent_connections(String concurrent_connections) {
			this.concurrent_connections = concurrent_connections;
		}

		public void setConnected_time(String connected_time) {
			this.connected_time = connected_time;
		}

		public void setCpu_time(String cpu_time) {
			this.cpu_time = cpu_time;
		}

		public void setDenied_connections(String denied_connections) {
			this.denied_connections = denied_connections;
		}

		public void setEmpty_queries(String empty_queries) {
			this.empty_queries = empty_queries;
		}

		public void setLost_connections(String lost_connections) {
			this.lost_connections = lost_connections;
		}

		public void setOther_commands(String other_commands) {
			this.other_commands = other_commands;
		}

		public void setRollback_transactions(String rollback_transactions) {
			this.rollback_transactions = rollback_transactions;
		}

		public void setRows_deleted(String rows_deleted) {
			this.rows_deleted = rows_deleted;
		}

		public void setRows_inserted(String rows_inserted) {
			this.rows_inserted = rows_inserted;
		}

		public void setRows_read(String rows_read) {
			this.rows_read = rows_read;
		}

		public void setRows_sent(String rows_sent) {
			this.rows_sent = rows_sent;
		}

		public void setRows_updated(String rows_updated) {
			this.rows_updated = rows_updated;
		}

		public void setSelect_commands(String select_commands) {
			this.select_commands = select_commands;
		}

		public void setTotal_connections(String total_connections) {
			this.total_connections = total_connections;
		}

		public void setUpdate_commands(String update_commands) {
			this.update_commands = update_commands;
		}

		public void setUser(String user) {
			this.user = user;
		}

	}

}
