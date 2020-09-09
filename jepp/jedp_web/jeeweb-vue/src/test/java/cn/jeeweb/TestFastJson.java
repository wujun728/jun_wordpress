package cn.jeeweb;


import cn.jeeweb.common.http.Response;
import cn.jeeweb.entity.Organization;
import cn.jeeweb.entity.User;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * All rights Reserved, Designed By www.jeeweb.cn
 *
 * @version V1.0
 * @package PACKAGE_NAME
 * @title:
 * @description: 测试fastjson
 * @author: 王存见
 * @date: 2018/5/23 11:33
 * @copyright: 2017 www.jeeweb.cn Inc. All rights reserved.
 */
public class TestFastJson {
    public static void main(String[] args) {
        Organization organization=new Organization();
        organization.setName("贵州省科技厅");
        List<User> testList=new ArrayList<User>();
        User user=new User();
        user.setOrganization(organization);
        user.setUsername("王存见");
/*        FastjsonPropertyPreFilter fastjsonPropertyPreFilter = new FastjsonPropertyPreFilter(user.getClass(),"username,organization.name");
        FastjsonUnXssFilter fastjsonUnXssFilter = new FastjsonUnXssFilter();
        SerializeFilter[] filters={fastjsonPropertyPreFilter,fastjsonUnXssFilter};*/
        String objectStr= JSON.toJSONString(Response.ok().putObject(user,"username,organization.name"));
        System.out.println(objectStr);
    }
}
