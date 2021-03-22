import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/*
Step 4:
Now, notice that Step 3 still did not use any synchronization primitives – even when in TROUBLE or at Santa’s door, the elf threads are spinning.
Using semaphores, create a new version starting from the code from Step 3 in such a way that the threads of the Elves are waiting in the acquire() function of a semaphore when they are in the TROUBLE mode.
*/

public class SantaScenario {

	public Santa santa;
	public List<Elf> elves;
	public List<Reindeer> reindeers;
	public boolean isDecember;

	public static void main(String args[]) {
		SantaScenario scenario = new SantaScenario();
		scenario.isDecember = false;
		// create the participants
		// Santa
		scenario.santa = new Santa(scenario);
		Thread th = new Thread(scenario.santa);
		th.start();
		// The elves: in this case: 10
		Semaphore elvesSemaphore = new Semaphore(3);
		scenario.elves = new ArrayList<>();
		for(int i = 0; i != 10; i++) {
			Elf elf = new Elf(i+1, scenario, elvesSemaphore);
			scenario.elves.add(elf);
			th = new Thread(elf);
			th.start();
		}
		// The reindeer: in this case: 9
		/*scenario.reindeers = new ArrayList<>();
		for(int i=0; i != 9; i++) {
			Reindeer reindeer = new Reindeer(i+1, scenario);
			scenario.reindeers.add(reindeer);
			th = new Thread(reindeer);
			th.start();
		}
		*/
		// now, start the passing of time
		for(int day = 1; day < 500; day++) {
			// wait a day
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// turn on December
			if (day > (365 - 31)) {
				scenario.isDecember = true;
			}

			// print out the state:
			System.out.println("***********  Day " + day + " *************************");
			// Stops threads after 370 days have passed
			if (day == 370)
			{
				scenario.santa.stop();
				for (Elf elf: scenario.elves)
					elf.stop();
				//for (Reindeer reindeer: scenario.reindeers)
				//	reindeer.stop();
			}
			scenario.santa.report();
			for(Elf elf: scenario.elves) {
				elf.report();
			}
			//for(Reindeer reindeer: scenario.reindeers) {
			//	reindeer.report();
			//}
			//System.out.println(Thread.getAllStackTraces().keySet().toString());
		}
	}



}
