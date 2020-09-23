package com.lzy.springcloud.user.controller;


import com.lzy.springcloud.user.service.UserService;
import com.lzy.springcloud.manage.bean.UmsMember;
import com.lzy.springcloud.manage.bean.UmsMemberReceiveAddress;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@Api(tags = "用户web")
@CrossOrigin
public class UserController {
    @Resource
     private UserService userService;

    @ApiOperation("getReceiveAddressByMemberId")
    @RequestMapping("getReceiveAddressByMemberId")
    @ResponseBody
    public List<UmsMemberReceiveAddress> getReceiveAddressByMemberId(@RequestParam("memberId") String memberId){

        List<UmsMemberReceiveAddress> umsMemberReceiveAddresses = userService.getReceiveAddressByMemberId(memberId);

        return umsMemberReceiveAddresses;
    }


    @ApiOperation("获取所有用户")
    @RequestMapping("getAllUser")
    @ResponseBody
    public List<UmsMember> getAllUser(){

        List<UmsMember> umsMembers = userService.getAllUser();
        return umsMembers;
    }

    @ApiOperation("第一个测试接口")
    @RequestMapping("index")
    @ResponseBody
    public String index(){
        return userService.index();
    }


}
