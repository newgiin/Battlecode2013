package team184;

/**
 * Specifies channels to use for various purposes. Possible to be modified 
 * in case collision with an enemy channel.
 */
public interface Channels {
	public static final int SECRET_CODE = 14;
	/**
	 * The number of bits the secret code should be stored in, i.e. some number
	 * such that SECRET_CODE < 2**SECRET_CODE_SIZE.
	 */
	public static final int SECRET_CODE_SIZE = 4;
	
    /**
     * Channel storing information on the # of encampments on the field.
     * There are n channels that follow this one which contain information
     * on each specific encampment. The channels are sorted in order of distance
     * to HQ, e.g. channel ENCAMPMENTS_COVERED + 1 contains the information
     * on the closest encampment to HQ.
     */
    public static int ENCAMPMENTS_COVERED = 4750;
}
