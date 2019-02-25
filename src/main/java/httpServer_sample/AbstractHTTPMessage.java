package httpServer_sample;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractHTTPMessage {
	Map<String,String> headers;
	String body;

	public AbstractHTTPMessage() {
		this.headers = new HashMap<>();
		this.body = null;
	}

	public void addHeader(String key, String value) {
		this.headers.put(key, value);
	}

	public Map<String, String> getHeaders() {
	    return headers;
	  }

	public void setBody(String str) {
		this.body = str;
	}

	public String getBody() {
		return body;
	}

	public abstract String get1stLine();

	public StringBuilder getMessage() {
		StringBuilder message = null;
		message.append(get1stLine() + "\n");
		for (Map.Entry<String,String> entry : headers.entrySet()) {
			message.append(entry.getKey() + ":" + entry.getValue() + "\n\n");
		}
		message.append(body);

		return message;
	}
}
