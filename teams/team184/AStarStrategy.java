package team184;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import battlecode.common.Direction;
import battlecode.common.GameConstants;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;
import battlecode.common.Team;

public class AStarStrategy implements RouteStrategy {
	private final RobotController rc;
	private final List<MapLocation> path;
	private int nextStep;
	
	public AStarStrategy(RobotController rc, MapLocation dest) {
		this.rc = rc;
		System.out.println("computing aStar");
		this.path = AStarStrategy.aStar(rc, rc.getLocation(), dest);
		for (MapLocation loc : path) {
		    // TODO remove debug code
		    System.out.println("col: " + loc.x + ", row: " + loc.y);
		}
		this.nextStep = 0;
	}
	
	@Override
	public Direction getStep() {
		if (rc.getLocation().equals(path.get(nextStep))) {
			nextStep += 1;
			if (nextStep == path.size()) {
				return Direction.OMNI;
			}
		}
		Direction dirToNextStep = BeelineStrategy.getStep(rc, path.get(nextStep));
		
		return dirToNextStep;
	}

	public static LinkedList<MapLocation> aStar(RobotController rc, MapLocation start, MapLocation end) {
		Direction [] dirs = {Direction.NORTH, Direction.NORTH_EAST, Direction.EAST, Direction.SOUTH_EAST, Direction.SOUTH, Direction.SOUTH_WEST, Direction.WEST, Direction.NORTH_WEST};
		HashSet<MapLocation> closedset = new HashSet<MapLocation>();
		PriorityQueue<CandidateLocation> openset = new PriorityQueue<CandidateLocation>();
		HashMap<MapLocation, MapLocation> cameFrom = new HashMap<MapLocation, MapLocation>();
		HashMap<MapLocation, Integer> gScore = new HashMap<MapLocation, Integer>();
		
		gScore.put(start, 0);
		openset.add(new CandidateLocation(start,estimate(start,end)));
		
		while(!openset.isEmpty()) {
			MapLocation current = openset.poll().loc;
			//System.out.println("current: " + current + " end: " + end);
			if(current.equals(end)) {
				return reconstructPath(cameFrom, end);
			}
			closedset.add(current);
			for(int i = 0; i < dirs.length; i++) {
				MapLocation nextloc = current.add(dirs[i]);
				//System.out.println("nextloc = " + nextloc);
				if(closedset.contains(nextloc) || isSquareValid(nextloc, rc.getMapWidth(), rc.getMapHeight())) {
					//System.out.println("invalid square : " +isSquareValid(nextloc, rc.getMapWidth(), rc.getMapHeight()));
					continue;
				}
				int gEstimate = gScore.get(current) + distBetween(current, nextloc,rc);
				if(!openset.contains(nextloc) || gEstimate <= gScore.get(nextloc)) {
					cameFrom.put(nextloc, current);
					gScore.put(nextloc, gEstimate);
					openset.add(new CandidateLocation(nextloc, gScore.get(nextloc) + estimate(nextloc,end)));
				}
			}
	
		}
		System.out.println("No Path Found");
		return new LinkedList<MapLocation>();
	}

	private static boolean isSquareValid(MapLocation nextLoc, int width, int height) {
		return (nextLoc.x < 0 || nextLoc.y < 0 || nextLoc.x >= width || nextLoc.y >=height);
	}

	private static Integer distBetween(MapLocation current, MapLocation nextloc, RobotController rc) {
		Team mineteam = rc.senseMine(nextloc);
		if(mineteam == rc.getTeam() || mineteam == null) {
			return 1;
		} else {
			return GameConstants.MINE_DEFUSE_DELAY;
		}
	}

	private static Integer estimate(MapLocation start, MapLocation end) {
		int dy = Math.abs(end.y-start.y);
		int dx = Math.abs(end.x - start.x);
		return Math.max(dy, dx);
	}
	
	//returns the path with the newest ones in the back.
	private static LinkedList<MapLocation> reconstructPath(
			HashMap<MapLocation, MapLocation> cameFrom, MapLocation end) {
		LinkedList<MapLocation> path = new LinkedList<MapLocation>();
		while(cameFrom.containsKey(end)) {
			path.add(end);
			end = cameFrom.get(end);
		}
		Collections.reverse(path);
		return path;
	}

	private static MapLocation getMinEstimate(HashSet<MapLocation> openSet, HashMap<MapLocation, Integer> fScore) {
		int minScore = Integer.MAX_VALUE;
		MapLocation minLoc = null;
		for(MapLocation loc: openSet) {
			if(fScore.get(loc) < minScore) {
				minScore = fScore.get(loc);
				minLoc = loc;
			}
		}
		return minLoc;
	}
}

class CandidateLocation implements Comparable<Object> {
	public int f;
	public MapLocation loc;
	public CandidateLocation(MapLocation loc, int f) {
		this.f = f;
		this.loc = loc;
	}
	@Override
	public int compareTo(Object arg0) {
		if (arg0 instanceof CandidateLocation) {
			CandidateLocation other = (CandidateLocation) (arg0);
			return this.f - other.f;
		}
		return -1;
	}
}
