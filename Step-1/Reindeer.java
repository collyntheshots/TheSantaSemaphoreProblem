import java.util.Random;


public class Reindeer implements Runnable {

	public enum ReindeerState {AT_BEACH, AT_WARMING_SHED, AT_THE_SLEIGH};
	private ReindeerState state;
	private SantaScenario scenario;
	private volatile boolean isRunning = true;
	private Random rand = new Random();

	/**
	 * The number associated with the reindeer
	 */
	private int number;

	public Reindeer(int number, SantaScenario scenario) {
		this.number = number;
		this.scenario = scenario;
		this.state = ReindeerState.AT_BEACH;
	}

	public void stop()
	{
		isRunning = false;
		//System.out.println("Raindeer stop");
	}

	@Override
	public void run() {
		while(isRunning) {
		// wait a day
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// see what we need to do:
		switch(state) {
		case AT_BEACH: { // if it is December, the reindeer might think about returning from the beach
			if (scenario.isDecember) {
				if (rand.nextDouble() < 0.1) {
					state = ReindeerState.AT_WARMING_SHED;
				}
			}
			break;
		}
		case AT_WARMING_SHED:
			// if all the reindeer are home, wake up santa
			break;
		case AT_THE_SLEIGH:
			// keep pulling
			break;
		}
		}
	};

	/**
	 * Report about my state
	 */
	public void report() {
		System.out.println("Reindeer " + number + " : " + state);
	}

}
