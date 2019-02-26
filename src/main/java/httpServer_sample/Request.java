package httpServer_sample;

import java.io.BufferedReader;

public class Request extends AbstractHTTPMessage {
	String method;
	String uri;
	String version;

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

	public Request(BufferedReader in) throws Exception {
		super();
		String line = in.readLine();

		String[] firstLine = line.split(" ", 3);
		this.method = firstLine[0];
		this.uri = firstLine[1];
		this.version = firstLine[2];

		line = in.readLine();
		int contentLength = 0;
		while (line != null && !line.isEmpty()) {
			if (line.startsWith("Content-Length")) {
				contentLength = Integer.parseInt(line.split(":")[1].trim());
			}

			String[] record = line.split(":", 2);
			this.addHeader(record[0], record[1]);
			line = in.readLine();
		}

		if (0 < contentLength) {
			char[] c = new char[contentLength];
			in.read(c);
			this.setBody(new String(c));
		}

		if (this.getHeaders().containsKey("Transfer-Encoding")) {
			if (this.getHeaders().get("Transfer-Encoding").contains("chunked")) {

				StringBuilder buffer = new StringBuilder();
				line = in.readLine();
				int chunkLength = Integer.parseInt(line, 16);

				while (chunkLength != 0) {
					char[] c = new char[chunkLength];
					in.read(c);
					buffer.append(c);
					in.readLine();
					line = in.readLine();
					chunkLength = Integer.parseInt(line, 16);
				}

				this.setBody(buffer.toString());
			}
		}
	}

}