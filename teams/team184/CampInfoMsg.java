package team184;

public class CampInfoMsg {
	public final int secretCode;
	public final int x;
	public final int y;
	public final boolean isTargeted;
	public final boolean isTaken;
	public final boolean isEnemyNear;
	
	private static final int SECRET_CODE_START = 1;
	private static final int SECRET_CODE_END = SECRET_CODE_START + Channels.SECRET_CODE_SIZE;
	
	private static final int X_START = SECRET_CODE_END;
	private static final int X_END = X_START + 7; // Max map size is 70, so only 7 bits needed.
	
	private static final int Y_START = X_END;
	private static final int Y_END = Y_START + 7;
	
	private static final int IS_TARGETED_POS = Y_END + 1;
	private static final int IS_TAKEN_POS = IS_TARGETED_POS + 1;
	private static final int IS_ENEMY_NEAR_POS = IS_TAKEN_POS + 1;
	
	public CampInfoMsg(int secretCode, int x, int y, boolean isTargeted, boolean isTaken, 
			boolean isEnemyNear) {
		this.secretCode = secretCode;
		this.x = x;
		this.y = y;
		this.isTargeted = isTargeted;
		this.isTaken = isTaken;
		this.isEnemyNear = isEnemyNear;
	}

	public static CampInfoMsg decode(int code) {
        
        int secretCode = (code << SECRET_CODE_START) >>> (32 - Channels.SECRET_CODE_SIZE);
        int x = (code << X_START) >>> (32 - (X_END - X_START));
        int y = (code << Y_START) >>> (32 - (Y_END - Y_START));
        boolean isTargeted = ((code << IS_TARGETED_POS) >>> 31) == 1;
        boolean isTaken = ((code << IS_TAKEN_POS) >>> 31) == 1;
        boolean isEnemyNear = ((code << IS_ENEMY_NEAR_POS) >>> 31) == 1;
        
        return new CampInfoMsg(secretCode, x, y, isTargeted, isTaken, isEnemyNear);
	}
	
	public int intValue() {
	    int result = secretCode << 32 - SECRET_CODE_END;
	    int block = x << 32 - X_END;
	    result |= block;
	    
	    block = y << 32 - Y_END;
	    result |= block;
	    
	    block = (isTargeted ? 1 : 0) << 32 - 1 - IS_TARGETED_POS;
	    result |= block;

        block = (isTaken ? 1 : 0) << 32 - 1 - IS_TAKEN_POS;
        result |= block;
        
        block = (isEnemyNear ? 1 : 0) << 32 - 1 - IS_ENEMY_NEAR_POS;
        result |= block;
        
        return result;
	}
	
	public static int setIsTargeted(int code, boolean isTargeted) {
		return Utilities.setBit(code, IS_TARGETED_POS, true);
	}
	
    public static int setIsTaken(int code, boolean isTaken) {
         return Utilities.setBit(code, IS_TAKEN_POS, true);
    }
    
    public String toString() {
        return secretCode + "{(" + x + ", " + y + "), isTargeted: " + isTargeted + ", isTaken: " +
                isTaken + ", isEnemyNear: " + isEnemyNear + "}";
    }
}
