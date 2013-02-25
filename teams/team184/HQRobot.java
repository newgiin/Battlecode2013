package team184;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.RobotController;
import battlecode.common.Team;

public class HQRobot extends BaseRobot {
    private Direction dirToHQ;
    
	public HQRobot(RobotController rc) {
		super(rc);
		dirToHQ = getLocation().directionTo(senseEnemyHQLocation());
		if (rc.senseMine(getLocation().add(dirToHQ)) == Team.NEUTRAL) {
		    dirToHQ = dirToHQ.rotateLeft();
		}
	}

	@Override
	public void performAction() throws GameActionException {
	    if (isActive() && getTeamPower() > 30) {
	        // Spawn a soldier
            if (canMove(dirToHQ)) {
                spawn(dirToHQ);              
            }
	    }
	    yield();
	}
}
