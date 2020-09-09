package cn.jeeweb.bbs.modules.sign.service.impl;

import cn.jeeweb.bbs.modules.sign.entity.SignInStatus;
import cn.jeeweb.bbs.modules.sign.service.ISignInStatusService;
import cn.jeeweb.bbs.modules.sys.entity.User;
import cn.jeeweb.bbs.modules.sys.service.IUserService;
import cn.jeeweb.bbs.utils.UserUtils;
import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.bbs.modules.sign.service.ISignInService;
import cn.jeeweb.bbs.modules.sign.entity.SignIn;
import cn.jeeweb.bbs.modules.sign.mapper.SignInMapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


/**
* All rights Reserved, Designed By www.jeeweb.cn
*
* @version V1.0
* @package cn.jeeweb.bbs.sign.service.impl
* @title: 签到服务实现
* @description: 签到服务实现
* @author: 王存见
* @date: 2018-09-05 16:03:35
* @copyright: 2018 www.jeeweb.cn Inc. All rights reserved.
*/
@Transactional
@Service("signinService")
public class SignInServiceImpl  extends CommonServiceImpl<SignInMapper,SignIn> implements  ISignInService {
    @Autowired
    private ISignInStatusService signInStatusService;
    @Autowired
    private IUserService userService;

    @Override
    public Integer countYesterdaySign(String userId) {
        return baseMapper.countYesterdaySign(userId);
    }

    @Override
    public Integer countToDaySign(String userId) {
        return baseMapper.countToDaySign(userId);
    }

    @Override
    public Page<SignIn> selectToDaySignPage(Page<SignIn> page) {
        page.setRecords(baseMapper.selectToDaySignList(page));
        return page;
    }

    @Override
    public Page<SignIn> selectLatestSignPage(Page<SignIn> page) {
        page.setRecords(baseMapper.selectLatestSignList(page));
        return page;
    }

    @Override
    public SignInStatus signIn() {
        String uid = UserUtils.getUser().getId();
        if (countToDaySign(uid)==0) {
            SignIn signIn = new SignIn();
            signIn.setSignTime(new Date());
            signIn.setSignUid(uid);
            Integer day = 0;
            SignInStatus signInStatus = null;
            if (countYesterdaySign(uid)==1){
                //运算经验
                signInStatus = signInStatusService.selectLastSignInByUid(uid);
                if (signInStatus != null) {
                    day = signInStatus.getSignDay();
                }
            }
            Integer experience = calculateExperience(day+1);
            signIn.setExperience(experience);
            insert(signIn);
            //保存连续签到记录
            if (signInStatus == null){
                signInStatus = new SignInStatus();
                signInStatus.setExperience(0);
                signInStatus.setSignDay(0);
            }
            signInStatus.setSignDay(day+1);
            signInStatus.setLastSignTime(new Date());
            signInStatus.setExperience(experience);
            signInStatus.setSignUid(uid);
            signInStatusService.insertOrUpdate(signInStatus);
            //把经验值加到用户中
            User user =  userService.selectById(UserUtils.getUser().getId());
            user.setExperience(user.getExperience()+experience);
            userService.insertOrUpdate(user);
            return signInStatus;
        }else{
            throw  new  RuntimeException("今天已经签到");
        }
    }

    public Integer calculateExperience(Integer day){
        if (day<5){
            return 5;
        }else if (5<=day&&day<15){
            return 10;
        }else if (15<=day&&day<30){
            return 15;
        }else if (30<=day&&day<100){
            return 	20;
        }else if (100<=day&&day<365){
            return 30;
        }else if (365<=day){
            return 50;
        }
        return 0;
    }
}