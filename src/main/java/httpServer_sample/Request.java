package httpServer_sample;

public class Request extends AbstractHTTPMessage {
	String method;
	String uri;
	String version;

	public Request(String m, String u, String v) {
		super();
		this.method = m;
		this.uri = u;
		this.version = v;
	}

	public String getMethod() {
		return method;
	}

	public String getURI() {
		return uri;
	}

	public String getVersion() {
		return version;
	}

	@Override
	public String get1stLine() {
		return method + " " + uri + " " + version;
	}

}