package cn.jeeweb.bbs.utils;

import cn.jeeweb.bbs.modules.sign.entity.SignInStatus;
import cn.jeeweb.bbs.modules.sign.service.ISignInService;
import cn.jeeweb.bbs.modules.sign.service.ISignInStatusService;
import cn.jeeweb.common.utils.SpringContextHolder;
import cn.jeeweb.common.utils.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import java.util.HashMap;
import java.util.Map;


public class SignInUtils {

	/**
	 *  获取
	 *
	 * @return
	 */
	public static Map<String,Object> getSignInInfo() {
		Map<String,Object> signInInfo= new HashMap<>();
		Subject subject = SecurityUtils.getSubject();
		String uid = UserUtils.getUser().getId();
		if (subject != null && !StringUtils.isEmpty(uid)) {
			ISignInService signInService= SpringContextHolder.getBean(ISignInService.class);
			ISignInStatusService signInStatusService= SpringContextHolder.getBean(ISignInStatusService.class);
			Integer isSign= signInService.countToDaySign(uid);
			signInInfo.put("isSign",isSign);
			if (isSign == 0) {
				Integer day = 0;
				if (signInService.countYesterdaySign(uid)==1){
					//运算经验
					SignInStatus signInStatus  = signInStatusService.selectLastSignInByUid(uid);
					if (signInStatus != null) {
						day = signInStatus.getSignDay();
					}
				}
				Integer experience = signInService.calculateExperience(day+1);
				signInInfo.put("signDay",day);
				signInInfo.put("experience", experience);
			}else{
				SignInStatus signInStatus  = signInStatusService.selectLastSignInByUid(uid);
				signInInfo.put("signDay",signInStatus.getSignDay());
				signInInfo.put("experience", signInStatus.getExperience());
			}
		}else{
			signInInfo.put("signDay","0");
			signInInfo.put("experience", 5);
			signInInfo.put("isSign",0);
		}
		return signInInfo;
	}
}
