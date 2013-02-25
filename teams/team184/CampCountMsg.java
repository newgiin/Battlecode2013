package team184;

/**
 * A CampCountMsg contains the count of encampments on the field.
 * Contents of the 32 bits are:
 * <ul>
 * <li>[0] = Unused </li>
 * <li>[1 : 1 + SECRET_CODE_SIZE] = The secret code </li>
 * <li>[1 + SECRET_CODE_SIZE : ] = # of encampments on the field </li>
 * </ul>
 */
public class CampCountMsg {
	public final int secretCode;
	public final int campCount;
	
	private static final int SECRET_CODE_START = 1;
	private static final int SECRET_CODE_END = SECRET_CODE_START + Channels.SECRET_CODE_SIZE;
	
	private static final int CAMP_COUNT_START = SECRET_CODE_END;
	private static final int CAMP_COUNT_END = CAMP_COUNT_START + 13;
	        
	public CampCountMsg(int secretCode, int campCount) {
		this.secretCode = secretCode;
		this.campCount = campCount;
	}
	
	public static CampCountMsg decode(int x) {
        int secretCode = (x << SECRET_CODE_START) >>> (32 - Channels.SECRET_CODE_SIZE);
        int campCount = (x << SECRET_CODE_END) >>> (32 - (CAMP_COUNT_END - CAMP_COUNT_START));
        
        return new CampCountMsg(secretCode, campCount);
	}
	
	public int intValue() {
	    int result = secretCode << 32 - SECRET_CODE_END;
	    
	    int campCountBlock = campCount << 32 - CAMP_COUNT_END;
	    result |= campCountBlock;
	    
        return result;
	}
}
