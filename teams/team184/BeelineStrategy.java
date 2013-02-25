package team184;

import battlecode.common.Direction;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;

public class BeelineStrategy implements RouteStrategy {
	private final RobotController rc;
	private final MapLocation dest;
	
	public BeelineStrategy(RobotController bot, MapLocation dest) {
		this.rc = bot;
		this.dest = dest;
	}
	
	@Override
	public Direction getStep() {
		return BeelineStrategy.getStep(rc, dest);
	}

	/**
	 * A functional {@link #getStep() getStep()}
	 * @param rc
	 * @param dest
	 * @return
	 */
	public static Direction getStep(RobotController rc, MapLocation dest) {
		Direction dirToLoc = rc.getLocation().directionTo(dest);
		
		// we've arrived at destination
		if (dirToLoc == Direction.OMNI) 
			return Direction.OMNI;
		
        if (rc.canMove(dirToLoc)) {
            return dirToLoc;
        } else if (rc.canMove(dirToLoc.rotateLeft())) {
            return dirToLoc.rotateLeft();
        } else if(rc.canMove(dirToLoc.rotateRight())) {
            return dirToLoc.rotateRight();
        } else if(rc.canMove(dirToLoc.rotateLeft().rotateLeft())) {
            return dirToLoc.rotateLeft().rotateLeft();
        } else if(rc.canMove(dirToLoc.rotateRight().rotateRight())) {
            return dirToLoc.rotateRight().rotateRight();
        } else if(rc.canMove(dirToLoc.rotateLeft().rotateLeft().rotateLeft())) {
            return dirToLoc.rotateLeft().rotateLeft().rotateLeft();
        } else if(rc.canMove(dirToLoc.rotateRight().rotateRight().rotateRight())) {
            return dirToLoc.rotateRight().rotateRight().rotateRight();
        } else if (rc.canMove(dirToLoc.opposite())) {
            return dirToLoc.opposite();
        }
        return Direction.NONE;
	}
}
