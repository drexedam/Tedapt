package at.fhv.tedapt.helper;

/**
 * 
 * @author Damian Drexel
 * @version 0.1
 *
 * Wrapper class for migration data entered in the migration dialog
 *
 */
public class MigrationData {

	private String _uName;
	private String _pw;
	private String _dBAdr;
	private String _dBName;
	private String _baseVersion;
	private String _maxVersion;
	private boolean _outOfOrder;
	
	public MigrationData(String uName, String pw, String dBAdr,
			String dBName, String baseVersion, String maxVersion,
			boolean outOfOrder) {
		_uName = uName;
		_pw = pw;
		_dBAdr = dBAdr;
		_dBName = dBName;
		_baseVersion = baseVersion;
		_maxVersion = maxVersion;
		_outOfOrder = outOfOrder;
	}

	public String getUName() { return _uName; }
	public String getPW() {return _pw; }
	public String getDBAdr() {return _dBAdr; }
	public String getDBName() {return _dBName; }
	public int getBaseVersion() {
		
		if(isPosNum(_baseVersion))
			return Integer.parseInt(_baseVersion); 
		
		return -1;
		
	}
	public int getMaxVersion() {
		
		if(isPosNum(_maxVersion))
			return Integer.parseInt(_maxVersion); 
		
		return -1;
		
	}
	
	public boolean outOfOrder() {return _outOfOrder; }
	
	private boolean isPosNum(String s) {
		if(s == null || s.length() == 0 || s.charAt(0) == '-') return false;
		
		for(int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if(c < '0' || c > '9') return false;
		}
		
		return true;
	}
}
