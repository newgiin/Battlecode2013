package team184;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.Robot;
import battlecode.common.RobotType;

/**
 * A Scout is responsible for capturing encampments and harassing enemy
 * encampments.
 */
public class ScoutStrategy implements SoldierStrategy {
	private static ScoutStrategy INSTANCE = new ScoutStrategy();
	
	private ScoutStrategy() {
	}
	
    @Override
    public void performAction(SoldierRobot bot) throws GameActionException {
        if (bot.isActive()) {
            // check for infidels
            Robot[] infidels = bot.senseNearbyGameObjects(Robot.class, 1, bot.getTeam().opponent());
            if (infidels.length > 0) {
                // stand and fight
            } else {
                Direction nextStep = bot.getRouteStrategy().getStep();
                if (nextStep != Direction.NONE) {
                	if (nextStep == Direction.OMNI) {
                		// NOTE: This robot dies after the capturing the encampment.
                		try {
                		    /*
                		     *  TODO resolve power depletion by building more Generators.
                		     *  When to start building them is the problem.
                		     */
                            if (true) // obviously temporary fix
                                bot.captureEncampment(RobotType.GENERATOR);
                            else
                                bot.captureEncampment(RobotType.SUPPLIER);
                        } catch (GameActionException e) {
                            System.out.println("XXX: CAN'T CAP");
                            System.out.println(e);
                        }
                		// Robot is now dead, don't put anything under this.
                	} else {
                		AttackMoveStrategy.defuseAndMove(bot, nextStep);
                	}
                }
            }
            
        }
        
        bot.yield();
    }

	public static ScoutStrategy getInstance() {
		return INSTANCE;
	}
}
