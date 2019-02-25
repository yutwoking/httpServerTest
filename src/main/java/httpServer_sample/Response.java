package httpServer_sample;

public class Response extends AbstractHTTPMessage {
	String version;
	Status status;

	public Response(String v, Status s) {
		super();
		this.version = v;
		this.status = s;
	}

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
}
