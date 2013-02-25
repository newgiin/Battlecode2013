package team184;

import battlecode.common.Clock;
import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.GameObject;
import battlecode.common.MapLocation;
import battlecode.common.Robot;
import battlecode.common.RobotController;
import battlecode.common.RobotInfo;
import battlecode.common.RobotLevel;
import battlecode.common.RobotType;
import battlecode.common.Team;
import battlecode.common.TerrainTile;
import battlecode.common.Upgrade;

public abstract class BaseRobot implements RobotController, Robot {
    public final RobotController rc;

    /**
     * Runs this robot through its actions a single time. May or may not
     * take more than one turn.
     * @throws GameActionException
     */
    public abstract void performAction() throws GameActionException;

    public BaseRobot(RobotController rc) {
        this.rc = rc;
        // If this is an encampment, broadcast it's been taken.
        if (rc.getType() != RobotType.HQ && rc.getType() != RobotType.SOLDIER) {
            try {
                CampCountMsg campCountMsg = CampCountMsg.decode(
                        rc.readBroadcast(Channels.ENCAMPMENTS_COVERED));
                if (campCountMsg.secretCode == Channels.SECRET_CODE) {
                    for (int i = 0; i < campCountMsg.campCount; i++) {
                        int channel = Channels.ENCAMPMENTS_COVERED + 1 + i;
                        int code = rc.readBroadcast(channel);
                        CampInfoMsg msg = CampInfoMsg.decode(code);

                        if (msg.secretCode == Channels.SECRET_CODE) {
                            code = CampInfoMsg.setIsTargeted(code, false);
                            code = CampInfoMsg.setIsTaken(code, true);
                            rc.broadcast(channel, code);
                            break;
                        } else {
                            // TODO channel compromised
                        }
                    }
                } else {
                    // TODO channel compromised
                }
            } catch (GameActionException e) {
                e.printStackTrace();
                return;
            }
        }
    }

    /*
     * --------- TODO Karl's methods
     */
    private boolean checkClock() {
        return Clock.getBytecodesLeft() < Constants.spare_use_cycle_limit;
    }

    /**
     * Returns true if <code>loc</code> is free of enemy mines.
     * Incurs bytecode cost of RobotController.senseMine().
     * @param loc
     * @return
     */
    public boolean isLocSafe(MapLocation loc) {
        Team t = senseMine(loc);
        return t == null || t == getTeam();
    }

    /*
     * --------- END Karl's methods
     */

    @Override
    public int getID() {
        return rc.getRobot().getID();
    }

    @Override
    public RobotLevel getRobotLevel() {
        return rc.getRobot().getRobotLevel();
    }

    @Override
    public void addMatchObservation(String observation) {
        rc.addMatchObservation(observation);
    }

    @Override
    public void attackSquare(MapLocation loc) throws GameActionException {
        rc.attackSquare(loc);
    }

    @Override
    public void breakpoint() {
        rc.breakpoint();
    }

    @Override
    public void broadcast(int channel, int data) throws GameActionException {
        rc.broadcast(channel, data);
    }

    @Override
    public boolean canAttackSquare(MapLocation loc) {
        return rc.canAttackSquare(loc);
    }

    @Override
    public boolean canMove(Direction dir) {
        return rc.canMove(dir);
    }

    @Override
    public boolean canSenseObject(GameObject o) {
        return rc.canSenseObject(o);
    }

    @Override
    public boolean canSenseSquare(MapLocation loc) {
        return rc.canSenseSquare(loc);
    }

    @Override
    public void captureEncampment(RobotType type) throws GameActionException {
        rc.captureEncampment(type);
    }

    @Override
    public int checkResearchProgress(Upgrade upgrade)
            throws GameActionException {
        return rc.checkResearchProgress(upgrade);
    }

    @Override
    public void defuseMine(MapLocation loc) throws GameActionException {
        rc.defuseMine(loc);
    }

    @Override
    public long getControlBits() {
        return rc.getControlBits();
    }

    @Override
    public double getEnergon() {
        return rc.getEnergon();
    }

    @Override
    public MapLocation getLocation() {
        return rc.getLocation();
    }

    @Override
    public int getMapHeight() {
        return rc.getMapHeight();
    }

    @Override
    public int getMapWidth() {
        return rc.getMapWidth();
    }

    @Override
    public Robot getRobot() {
        return rc.getRobot();
    }

    @Override
    public double getShields() {
        return rc.getShields();
    }

    @Override
    public Team getTeam() {
        return rc.getTeam();
    }

    @Override
    public long[] getTeamMemory() {
        return rc.getTeamMemory();
    }

    @Override
    public double getTeamPower() {
        return rc.getTeamPower();
    }

    @Override
    public RobotType getType() {
        return rc.getType();
    }

    @Override
    public boolean hasUpgrade(Upgrade upgrade) {
        return rc.hasUpgrade(upgrade);
    }

    @Override
    public boolean isActive() {
        return rc.isActive();
    }

    @Override
    public void layMine() throws GameActionException {
        rc.layMine();
    }

    @Override
    public void move(Direction dir) throws GameActionException {
        rc.move(dir);
    }

    @Override
    public int readBroadcast(int channel) throws GameActionException {
        return rc.readBroadcast(channel);
    }

    @Override
    public void researchUpgrade(Upgrade upgrade) throws GameActionException {
        rc.researchUpgrade(upgrade);
    }

    @Override
    public void resign() {
        rc.resign();
    }

    @Override
    public int roundsUntilActive() {
        return rc.roundsUntilActive();
    }

    @Override
    public MapLocation[] senseAllEncampmentSquares() {
        return rc.senseAllEncampmentSquares();
    }

    @Override
    public MapLocation[] senseAlliedEncampmentSquares() {
        return rc.senseAlliedEncampmentSquares();
    }

    @Override
    public double senseCaptureCost() {
        return rc.senseCaptureCost();
    }

    @Override
    public boolean senseEncampmentSquare(MapLocation loc) {
        return rc.senseEncampmentSquare(loc);
    }

    @Override
    public MapLocation[] senseEncampmentSquares(MapLocation center,
            int radiusSquared, Team team) throws GameActionException {
        return rc.senseEncampmentSquares(center, radiusSquared, team);
    }

    @Override
    public MapLocation senseEnemyHQLocation() {
        return rc.senseEnemyHQLocation();
    }

    @Override
    public boolean senseEnemyNukeHalfDone() throws GameActionException {
        return rc.senseEnemyNukeHalfDone();
    }

    @Override
    public MapLocation senseHQLocation() {
        return rc.senseHQLocation();
    }

    @Override
    public MapLocation senseLocationOf(GameObject o) throws GameActionException {
        return rc.senseLocationOf(o);
    }

    @Override
    public Team senseMine(MapLocation location) {
        return rc.senseMine(location);
    }

    @Override
    public MapLocation[] senseMineLocations(MapLocation center,
            int radiusSquared, Team team) {
        return rc.senseMineLocations(center, radiusSquared, team);
    }

    @Override
    public int senseMineRoundsLeft() throws GameActionException {
        return rc.senseMineRoundsLeft();
    }
    
    @Override
    public <T extends GameObject> T[] senseNearbyGameObjects(Class<T> type,
            int radiusSquared, Team team) {
        return rc.senseNearbyGameObjects(type, radiusSquared, team);
    }

    @Override
    public <T extends GameObject> T[] senseNearbyGameObjects(Class<T> type,
            int radiusSquared) {
        return rc.senseNearbyGameObjects(type, radiusSquared);
    }

    @Override
    public <T extends GameObject> T[] senseNearbyGameObjects(Class<T> type,
            MapLocation center, int radiusSquared, Team team) {
        return rc.senseNearbyGameObjects(type, center, radiusSquared, team);
    }

    @Override
    public <T extends GameObject> T[] senseNearbyGameObjects(Class<T> type) {
        return rc.senseNearbyGameObjects(type);
    }

    @Override
    public MapLocation[] senseNonAlliedMineLocations(MapLocation center,
            int radiusSquared) {
        return rc.senseNonAlliedMineLocations(center, radiusSquared);
    }

    @Override
    public GameObject senseObjectAtLocation(MapLocation loc)
            throws GameActionException {
        return rc.senseObjectAtLocation(loc);
    }

    @Override
    public RobotInfo senseRobotInfo(Robot r) throws GameActionException {
        return rc.senseRobotInfo(r);
    }

    @Override
    public TerrainTile senseTerrainTile(MapLocation loc) {
        return rc.senseTerrainTile(loc);
    }

    @Override
    public void setIndicatorString(int stringIndex, String newString) {
        rc.setIndicatorString(stringIndex, newString);
    }

    @Override
    public void setTeamMemory(int index, long value, long mask) {
        rc.setTeamMemory(index, value, mask);
    }

    @Override
    public void setTeamMemory(int index, long value) {
        rc.setTeamMemory(index, value);
    }

    @Override
    public void spawn(Direction dir) throws GameActionException {
        rc.spawn(dir);
    }

    @Override
    public void suicide() {
        rc.suicide();
    }

    @Override
    public void wearHat() {
        rc.wearHat();
    }

    @Override
    public void yield() {
        rc.yield();
    }

}