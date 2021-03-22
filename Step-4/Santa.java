//import com.sun.org.apache.xml.internal.security.utils.HelperNodeList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class Santa implements Runnable {

	enum SantaState {SLEEPING, READY_FOR_CHRISTMAS, WOKEN_UP_BY_ELVES, WOKEN_UP_BY_REINDEER};
	private SantaState state;
	private SantaScenario scenario;
	private volatile boolean isRunning = true;
	private Semaphore elvesSemaphore;

	public Santa(SantaScenario scenario) {
		this.state = SantaState.SLEEPING;
		this.scenario = scenario;
	}

	public void stop()
	{
		isRunning = false;
		//System.out.println("Santa stop");
	}

	public void wakeWithElves(Semaphore elvesSemaphore)
	{
		this.state = SantaState.WOKEN_UP_BY_ELVES;
		this.elvesSemaphore = elvesSemaphore;
	}

	public void wakeWithReindeer()
	{
		this.state = SantaState.WOKEN_UP_BY_REINDEER;
	}

	@Override
	public void run() {
		while(isRunning) {
			// wait a day...
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			switch(state) {
			case SLEEPING: // if sleeping, continue to sleep
				break;
			case WOKEN_UP_BY_ELVES:
				// FIXME: help the elves who are at the door and go back to sleep
				for(Elf elf: scenario.elves) {
					if (elf.getState() == Elf.ElfState.AT_SANTAS_DOOR && elvesSemaphore.availablePermits() != 3)
					{
						elf.setState(Elf.ElfState.WORKING);
						elvesSemaphore.release();
					}
				}
				this.state = SantaState.SLEEPING;
				break;
			case WOKEN_UP_BY_REINDEER:
				// FIXME: assemble the reindeer to the sleigh then change state to ready
				break;
			case READY_FOR_CHRISTMAS: // nothing more to be done
				break;
			}
		}
	}


	/**
	 * Report about my state
	 */
	public void report() {
		System.out.println("Santa : " + state);
	}


}