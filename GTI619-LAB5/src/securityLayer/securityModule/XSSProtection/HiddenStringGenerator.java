package securityLayer.securityModule.XSSProtection;

import java.math.BigInteger;
import java.security.SecureRandom;

public class HiddenStringGenerator {
	
	private SecureRandom random = new SecureRandom();
	
	public String generateRandomString(){
		return new BigInteger(512, random).toString(32);
	}
}
