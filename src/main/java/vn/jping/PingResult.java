package vn.jping;

import java.util.List;

/**
 * 
 * @author Thomas Goossens
 * @version 0.1a
 * 
 */
public abstract class PingResult {

	private List<String> lines;

	private final String address;
	private final int transmitted;
	private final int ttl;
	private final long time;
	private final int received;
	private final int payload;
	private final float rtt_min;
	private final float rtt_avg;
	private final float rtt_max;
	private final float rtt_mdev;

	public String address() {
		return address;
	}

	public int transmitted() {
		return transmitted;
	}

	public int ttl() {
		return ttl;
	}

	public long time() {
		return time;
	}

	public int received() {
		return received;
	}

	public int payload() {
		return payload;
	}

	public float rtt_min() {
		return rtt_min;
	}

	public float rtt_avg() {
		return rtt_avg;
	}

	public float rtt_max() {
		return rtt_max;
	}

	public float rtt_mdev() {
		return rtt_mdev;
	}

	protected PingResult(List<String> pingOutput) {

		this.lines = pingOutput;
		transmitted = matchTransmitted(pingOutput);
		received = matchReceived(pingOutput);
		time = matchTime(pingOutput);

		rtt_min = matchRttMin(pingOutput);
		rtt_avg = matchRttAvg(pingOutput);
		rtt_max = matchRttMax(pingOutput);
		rtt_mdev = matchRttMdev(pingOutput);

		ttl = matchTTL(pingOutput);

		address = matchIP(pingOutput);

		payload = parsePayload(pingOutput);

	}

	public List<String> getLines() {
		return lines;
	}

	protected abstract int parsePayload(List<String> lines);

	protected abstract int matchTransmitted(List<String> lines);

	protected abstract int matchReceived(List<String> lines);

	protected abstract int matchTime(List<String> lines);

	protected abstract float matchRttMin(List<String> lines);

	protected abstract float matchRttAvg(List<String> lines);

	protected abstract float matchRttMax(List<String> lines);

	protected abstract float matchRttMdev(List<String> lines);

	protected abstract String matchIP(List<String> lines);

	protected abstract int matchTTL(List<String> lines);

	public abstract List<PingRequest> getRequests();

	@Override
	public String toString() {
		return "PingResult [address=" + address + ", transmitted="
				+ transmitted + ", ttl=" + ttl + ", time=" + time
				+ ", received=" + received + ", payload=" + payload
				+ ", rtt_min=" + rtt_min + ", rtt_avg=" + rtt_avg
				+ ", rtt_max=" + rtt_max + ", rtt_mdev=" + rtt_mdev + "]";
	}
}