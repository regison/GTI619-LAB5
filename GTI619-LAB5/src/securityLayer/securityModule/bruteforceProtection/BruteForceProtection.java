package securityLayer.securityModule.bruteforceProtection;

import javax.servlet.http.HttpSession;

import communication.DataObjects.Objects.PasswordLoginPolitic;
import communication.DataObjects.Objects.User;
import communication.DataMapping.DataProvider;

public class BruteForceProtection {
	
	public void manageLoginBruteForce(HttpSession session, long userFailedTriesCount, User attackedUser){
		DataProvider dp = new DataProvider(false);
		PasswordLoginPolitic lp = dp.getPasswordLoginPolitic();
		if(session.getAttribute("failedLoginCount") == null){
			session.setAttribute("failedLoginCount", userFailedTriesCount > 1 ? (userFailedTriesCount + "") : "1");
		}
		else{
			session.setAttribute("failedLoginCount", ((Integer.parseInt(session.getAttribute("failedLoginCount").toString()) + 1) > userFailedTriesCount ? (Integer.parseInt(session.getAttribute("failedLoginCount").toString()) + 1) : userFailedTriesCount) + "");
			System.out.println("failedLoginCount : " + (Integer.parseInt(session.getAttribute("failedLoginCount").toString()) + 1) + " " + (1000 * (Math.pow(((Integer.parseInt(session.getAttribute("failedLoginCount").toString()) + 1) - lp.maxTentative), lp.delais))));
			
			int count = 0;
			if((count = Integer.parseInt(session.getAttribute("failedLoginCount").toString())) > lp.maxTentative){
				try {
					Thread.sleep((long) (1000 * (Math.pow((count - lp.maxTentative), lp.delais)))); //Attente exponentielle
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(count >= lp.maxTentative * 2 && lp.bloquage2tentatives && attackedUser != null){
					dp.RemoveUser(dp.GetAllUsersFromAUserName(attackedUser.name).get(0).idUser);
				}
			}
		}
	}
}
