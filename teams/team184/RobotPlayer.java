package team184;

import battlecode.common.Clock;
import battlecode.common.RobotController;

public class RobotPlayer {
	/**
	 * Entry point of a new Robot.
	 * @param rc
	 */
	public static void run(RobotController rc) {
		BaseRobot br = RobotFactory.createRobot(rc);
		while (true) {
			try {
				br.performAction();
			} catch (Exception e) {
				RobotPlayer.printError(br, e);
			}
		}
	}
	
	public static void printError(RobotController rc, Exception e) {
	    System.err.println("Main loop terminated unexpectedly");
        e.printStackTrace();
        rc.addMatchObservation(e.toString());
	}
}
