package team184;

import battlecode.common.GameActionException;

/**
 * A <code>SoldierStrategy</code> defines the behavior of a SoldierRobot.
 */
public interface SoldierStrategy {
    /**
     * Runs this SoldierRobot through its actions a single time. May or may not
     * take more than one turn. Performing an action could result in
     * modification of <code>bot</code>'s strategy field, e.g. transitioning
     * from a scouting to offensive strategy.
     * @throws GameActionException
     */
    public void performAction(SoldierRobot bot) throws GameActionException;
}
