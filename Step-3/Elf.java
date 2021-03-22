import java.util.Random;
import java.util.LinkedList;
import java.util.Queue;

public class Elf implements Runnable {

	enum ElfState {
		WORKING, TROUBLE, AT_SANTAS_DOOR
	};

	private ElfState state;
	/**
	 * The number associated with the Elf
	 */
	private int number;
	private Random rand = new Random();
	private SantaScenario scenario;
	private volatile boolean isRunning = true;
	//private Queue<Elf> inTrouble;
	private int inTrouble;

	public Elf(int number, SantaScenario scenario) {
		this.number = number;
		this.scenario = scenario;
		this.state = ElfState.WORKING;
		//this.inTrouble = new LinkedList<>();
	}


	public ElfState getState() {
		return state;
	}

	/**
	 * Santa might call this function to fix the trouble
	 * @param state
	 */
	public void setState(ElfState state) {
		this.state = state;
	}

	public void stop()
	{
		isRunning = false;
		//System.out.println("Elf stop");
	}

	public void start()
	{
		isRunning = true;
	}

	@Override
	public void run() {
		while (isRunning) {
		inTrouble = 0;
      // wait a day
  		try {
  			Thread.sleep(100);
  		} catch (InterruptedException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}
			switch (this.state) {
			case WORKING: {
				// at each day, there is a 1% chance that an elf runs into
				// trouble.
				if (rand.nextDouble() < 0.01) {
					this.state = ElfState.TROUBLE;
				}
				break;
			}
			case TROUBLE:
				// FIXME: if possible, move to Santa's door
				for(Elf elf: scenario.elves)
				{
					if (elf.getState() == ElfState.TROUBLE)
					{
						inTrouble++;
						elf.stop();
					}
				}
				//System.out.println("***** inTrouble: " + inTrouble + " *****");
				if (inTrouble == 3)
				{
					for(Elf elf: scenario.elves)
					{
						if (elf.getState() == ElfState.TROUBLE)
						{
							elf.setState(ElfState.AT_SANTAS_DOOR);
							elf.start();
						}
					}
				}
				//inTrouble.add(this);
				break;
			case AT_SANTAS_DOOR:
				// FIXME: if feasible, wake up Santa
				scenario.santa.wakeWithElves();
				//inTrouble.clear();
				break;
			}
		}
	}

	/**
	 * Report about my state
	 */
	public void report() {
		System.out.println("Elf " + number + " : " + state);
	}

}
