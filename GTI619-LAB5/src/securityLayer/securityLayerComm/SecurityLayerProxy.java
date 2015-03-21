package securityLayer.securityLayerComm;

public class SecurityLayerProxy {

	private static SecurityLayerProxy instance;
	private SecurityLayerProxy() {
		// TODO Auto-generated constructor stub
		
	}
	
	public static SecurityLayerProxy getInstance()
	{
		if(instance == null)
			instance = new SecurityLayerProxy();
		return instance;
	}

}
