package team184;

import battlecode.common.Direction;
import battlecode.common.MapLocation;

public interface RouteStrategy {
	/**
	 * Determines the next direction to move based on the implementing
	 * routing strategy. Should guarantee that moving to that space will
	 * not throw a GameActionException assuming the robot moves within
	 * the turn that it calls <code>getStep()</code>.
	 * @param br
	 * @param dest The intended destination
	 * @return The next Direction to move. OMNI if robot
	 * has arrived at destination or NONE if robot cannot move, but is not yet at the 
	 * destination.
	 */
	public Direction getStep();
}
