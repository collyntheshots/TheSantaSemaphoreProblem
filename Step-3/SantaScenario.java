import java.util.ArrayList;
import java.util.List;

/*
Step 3:
Starting from step 2, create a version where
-there is no reindeer present
-unless there are three elves which are in TROUBLE, the elves stay in TROUBLE. When the third elf gets in TROUBLE, they go to Santa’s door.
-if there is any elf at Santa’s door, the elves who get into TROUBLE, they stay in TROUBLE, and only go to Santa’s door when the previous elves came back.
-as soon as an elf is at Santa’s door and Santa is sleeping, he wakes up Santa
-if woken up, Santa solves the problems of the three elves who are at the door.
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
		scenario.elves = new ArrayList<>();
		for(int i = 0; i != 10; i++) {
			Elf elf = new Elf(i+1, scenario);
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
