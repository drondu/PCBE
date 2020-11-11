package main;
public class ShortUniqueIDGenerator {

	private static long lastSessionTimestamp = 0;
	private String crtTimestamp;
	private long idCounter;

	public ShortUniqueIDGenerator() {
		idCounter = 0;
		crtTimestamp = Long.toString(getLastSessionTimestamp());
	}

	public String getNewId() {
		return crtTimestamp + idCounter++;
	}

	private static long getLastSessionTimestamp() {
		long crtTimeSeconds = java.time.Instant.now().getEpochSecond();
		if (crtTimeSeconds > lastSessionTimestamp) {
			lastSessionTimestamp = crtTimeSeconds;
		} else {
			lastSessionTimestamp++;
		}
		return lastSessionTimestamp;
	}

}
