package team184;

import battlecode.common.Clock;
import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.Robot;
import battlecode.common.Team;

public class AttackMoveStrategy implements SoldierStrategy {
    private final static AttackMoveStrategy INSTANCE = new AttackMoveStrategy();
    
    // enforce singleton
    private AttackMoveStrategy() {
        
    }

    public static AttackMoveStrategy getInstance() {
        return INSTANCE;
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
                AttackMoveStrategy.defuseAndMove(bot, nextStep);
            }
        }
        
        bot.yield();
    }
    
    /**
     * Defuses one mine in the adjacent square in the given direction if the 
     * square is mined. Otherwise, move there immediately. If <code>dir</code>
     * is OMNI or NONE, the robot will sense its current location for a mine and defuse it, 
     * otherwise it will stand there and do nothing.
     * @throws GameActionException 
     */
    public static void defuseAndMove(SoldierRobot bot, Direction dir) throws GameActionException {
        Team mineTeam = bot.senseMine(bot.getLocation().add(dir));
        
        if (mineTeam == Team.NEUTRAL || mineTeam == bot.getTeam().opponent()) {
            bot.defuseMine(bot.getLocation().add(dir));
        } else {
        	if (dir != Direction.NONE && dir != Direction.OMNI)
        		bot.move(dir);
        }
    }
}
