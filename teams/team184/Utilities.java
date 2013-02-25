package team184;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;

import battlecode.common.Direction;
import battlecode.common.GameConstants;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;
import battlecode.common.Team;

public class Utilities {
	public static String padZeroes(String str, int desiredLen) {
	    int limit = desiredLen - str.length();
	    for (int i = 0; i < limit; i++) {
	        str = "0" + str;
	    }
	    return str;
	}
	/**
	 * @param pos The position of the bit to be set where 0 is the most significant bit, i.e
	 * the left most bit.
	 */
	public static int setBit(int x, int pos, boolean newValue) {
		x = clearBit(x, pos);
		if (!newValue)
			return x;
		return x | (1 << (32 - 1 - pos));
	}
	
	/**
	 * Replaces bits in <code>x</code> with those in <code>newValue</code> where the leading zeroes 
	 * of <code>newValue</code> are removed.
	 * @param start Where to start setting bits, where 0 is the most significant bit, i.e. the left
	 * most bit.
	 */
	public static int setBits(int x, int start, int newValue) {
		x = clearBits(x, start, log2(newValue) + 1);
		return x | (newValue << 32 - 1 - start);
	}

	/**
	 * @param pos The position of the bit to be set where 0 is the most significant bit, i.e
	 * the left most bit.
	 */
	public static int clearBit(int x, int pos) {
		return x & ~(1 << 32 - 1 - pos);
	}

	/**
	 * @param start Where to start setting bits, where 0 is the most significant bit, i.e. the left
	 * most bit.
	 */
	public static int clearBits(int x, int start, int blockSize) {
		int filter = ((int)Math.pow(blockSize, 2) - 1) - 32 - blockSize - start;
		return x ^ ~filter;
	}
	
    public static Comparator<MapLocation> getDistanceComparator(final MapLocation origin) {
    	return new Comparator<MapLocation>() {
			@Override
			public int compare(MapLocation o1, MapLocation o2) {
				return o1.distanceSquaredTo(origin) - o2.distanceSquaredTo(origin);
			}
    	};
    }
	
	private static int log2(int x) {
	    if (x <= 0) 
	    	throw new IllegalArgumentException();
	    return 31 - Integer.numberOfLeadingZeros(x);
	}

}