package securityLayer.securityModule.bruteforceProtection;

import javax.servlet.http.HttpSession;

import communication.DataObjects.Objects.User;

public class BruteForceProtection {
	
	public void manageLoginBruteForce(HttpSession session, long userFailedTriesCount){
		if(session.getAttribute("failedLoginCount") == null){
			session.setAttribute("failedLoginCount", userFailedTriesCount > 1 ? (userFailedTriesCount + "") : "1");
		}
		else{
			session.setAttribute("failedLoginCount", ((Integer.parseInt(session.getAttribute("failedLoginCount").toString()) + 1) > userFailedTriesCount ? (Integer.parseInt(session.getAttribute("failedLoginCount").toString()) + 1) : userFailedTriesCount) + "");
			System.out.println("failedLoginCount : " + (Integer.parseInt(session.getAttribute("failedLoginCount").toString()) + 1) + " " + (1000 * (Math.pow(((Integer.parseInt(session.getAttribute("failedLoginCount").toString()) + 1) - 3), 2))));
			
			int count = 0;
			if((count = Integer.parseInt(session.getAttribute("failedLoginCount").toString())) > 3){
				try {
					Thread.sleep((long) (1000 * (Math.pow((count - 3), 2)))); //Attente exponentielle
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
