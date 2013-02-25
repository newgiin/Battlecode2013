package team184;

import java.util.Arrays;

import battlecode.common.Clock;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;

/**
 * Creates robots. Contains logic to determine what kind of strategy a SoldierRobot should take
 * upon creation.
 */
public class RobotFactory {

	public static BaseRobot createRobot(RobotController rc) {
		switch (rc.getType()) {
			case SOLDIER:
				return RobotFactory.createSoldierRobot(rc);
			case ARTILLERY:
				return new ArtilleryRobot(rc);
			case GENERATOR:
				return new GeneratorRobot(rc);
			case MEDBAY:
				return new MedBayRobot(rc);
			case SHIELDS:
				return new ShieldRobot(rc);
			case SUPPLIER:
				return new SupplierRobot(rc);
			case HQ:
				return new HQRobot(rc);
			default:
				return null;
		}
	}
	
	private static SoldierRobot createSoldierRobot(RobotController rc) {
        try {
            int value = rc.readBroadcast(Channels.ENCAMPMENTS_COVERED);
            CampCountMsg campCountMsg = CampCountMsg.decode(value);

            if (value == 0) {
                /*
                 * No one's been on this channel before, sense the encampments
                 * and broadcast their info.
                 */
            	MapLocation[] camps = rc.senseAllEncampmentSquares();
                rc.broadcast(Channels.ENCAMPMENTS_COVERED, 
                		new CampCountMsg(Channels.SECRET_CODE, camps.length).intValue());
                Arrays.sort(camps, Utilities.getDistanceComparator(rc.senseHQLocation()));

                // skip the first one since this robot will be sent to it.
                for (int i = 1; i < camps.length; i++) {
                    rc.broadcast(Channels.ENCAMPMENTS_COVERED + 1 + i, 
                            new CampInfoMsg(Channels.SECRET_CODE, camps[i].x, camps[i].y, 
                                    false, false, false).intValue());
                }
                // cap the closest camp
                rc.broadcast(Channels.ENCAMPMENTS_COVERED + 1, 
                        new CampInfoMsg(Channels.SECRET_CODE, camps[0].x, camps[0].y, 
                                true, false, false).intValue());
                
                return new SoldierRobot(rc, ScoutStrategy.getInstance(), 
                        new BeelineStrategy(rc, camps[0]));
            } else if (campCountMsg.secretCode != Channels.SECRET_CODE) {
                /*
                 * TODOHandle what happens when you find enemy data here.
                 */
            } else { 
            	UntargetedCampInfo campToTake = getNextUntargetedCamp(rc, campCountMsg.campCount / 2);
                if (campToTake != null) {
                	rc.broadcast(campToTake.channel, 
                	        CampInfoMsg.setIsTargeted(campToTake.code, true));
        			return new SoldierRobot(rc, ScoutStrategy.getInstance(), new BeelineStrategy(rc, 
        					campToTake.loc));
                }
            }
        } catch (GameActionException e) {
            RobotPlayer.printError(rc, e);
            return new SoldierRobot(rc, AttackMoveStrategy.getInstance(), new BeelineStrategy(rc, 
            		rc.senseEnemyHQLocation()));
        }
		
        return new SoldierRobot(rc, AttackMoveStrategy.getInstance(), new BeelineStrategy(rc, 
        		rc.senseEnemyHQLocation()));
	
	}
	
	/**
	 * @param rc
	 * @param campLimit Consider camps from 0 up to <code>campLimit</code>.
	 */
	private static UntargetedCampInfo getNextUntargetedCamp(RobotController rc, int campLimit) 
			throws GameActionException {
        for (int i = 0; i < campLimit; i++) {
        	int channel = Channels.ENCAMPMENTS_COVERED + 1 + i;
        	int code = rc.readBroadcast(channel);
        	CampInfoMsg msg = CampInfoMsg.decode(code);

        	if (msg.secretCode != Channels.SECRET_CODE) {
        		// TODO take care of enemy activity
        	} else {
        		if (!msg.isTargeted) {
        			return new UntargetedCampInfo(channel, code, new MapLocation(msg.x, msg.y));
        		}
        	}
        }
        return null;
	}
	
	private static class UntargetedCampInfo {
		int channel;
		MapLocation loc;
		int code;
		
		UntargetedCampInfo(int channel, int code, MapLocation loc) {
			this.channel = channel;
			this.code = code;
			this.loc = loc;
		}
	}
}
