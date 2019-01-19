package vn.jping;

/**
 * 
 * @author Thomas Goossens
 * @version 0.1a
 * 
 */
public class SampleCode {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		PingArguments arguments = new PingArguments.Builder().url("google.com").timeout(5000).count(2).bytes(32).build();

		PingResult results = Ping.ping(arguments, Ping.Backend.UNIX);

	
		System.out.println("TTL: " + results.ttl());

		System.out.println("RTT Minimum: " + results.rtt_min());

		System.out.println("Received : " + results.received());
		
		System.out.println(results.getRequests());
	}

}
