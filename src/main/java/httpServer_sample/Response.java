package httpServer_sample;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

public class Response extends AbstractHTTPMessage {
	String version;
	Status status;

	public String getVersion() {
		return version;
	}

	public Status getStatus() {
		return status;
	}

	@Override
	public String get1stLine() {
		return version + " " + status.getCode() + " " + status.getReason();
	}

	public enum Status {
		OK(200, "OK"),
		BAD_REQUEST(400, "Bad Request"),
		NOT_FOUND(404, "Not Found");

		private int code;
		private String reason;

		private Status(int code, String reasonPhrase) {
			this.code = code;
			this.reason = reasonPhrase;
		}

		public int getCode() {
			return code;
		}

		public String getReason() {
			return reason;
		}
	}

	public Response(Request request) throws Exception {
		super();
		this.version = request.getVersion();
		this.addHeader("Date", " " + new Date().toString());

		if (request.getMethod().equals("GET")) {

			try {
				String fileName = request.getURI().substring(request.getURI().lastIndexOf("/"));
				if (fileName.equals("/")) {
					fileName = "/index.html";
				}
				this.bodyFileReader(fileName);
				this.status = Status.OK;
			} catch (Exception e) {
				this.bodyFileReader("/404.html");
				this.status = Status.NOT_FOUND;
			}

		} else if (request.getMethod().equals("POST")) {
			this.status = Status.OK;
			this.bodyFileReader("/post.html");
		} else {
			this.status = Status.BAD_REQUEST;
			this.bodyFileReader("/400.html");
		}

	}

	public void bodyFileReader(String dir) throws Exception {
		InputStream stream = Library.class.getResourceAsStream(dir);
		BufferedReader file = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
		String str = file.readLine();
		while (str != null) {
			this.setBody(this.getBody() + str);
			str = file.readLine();
		}

		stream.close();
		file.close();

		this.addHeader("Content-Type", " text/html; charset=utf-8");
	}

}
