package securityLayer.securityModule.bruteforceProtection;

import javax.servlet.http.HttpSession;

import communication.DataObjects.Objects.User;

public class BruteForceProtection {
	
	public void manageLoginBruteForce(User user, HttpSession session, long userFailedTriesCount){
		if(user != null){
			System.out.println("userFailedTriesCount : " + userFailedTriesCount + " " + (1000 * (Math.pow((userFailedTriesCount - 3), 2))));
			if(userFailedTriesCount > 3){
				try {
					Thread.sleep((long) (1000 * (Math.pow((userFailedTriesCount - 3), 2)))); //Attente exponentielle
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		else if(session.getAttribute("failedLoginCount") == null){
			session.setAttribute("failedLoginCount", "1");
		}
		else{
			System.out.println("failedLoginCount : " + (Integer.parseInt(session.getAttribute("failedLoginCount").toString()) + 1) + " " + (1000 * (Math.pow(((Integer.parseInt(session.getAttribute("failedLoginCount").toString()) + 1) - 3), 2))));
			session.setAttribute("failedLoginCount", (Integer.parseInt(session.getAttribute("failedLoginCount").toString()) + 1) + "");
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
