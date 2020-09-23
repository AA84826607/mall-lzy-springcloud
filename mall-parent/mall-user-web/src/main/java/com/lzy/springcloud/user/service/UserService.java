package com.lzy.springcloud.user.service;

import com.lzy.springcloud.manage.bean.UmsMember;
import com.lzy.springcloud.manage.bean.UmsMemberReceiveAddress;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Component
@FeignClient(value = "MALL-USER-SERVICE")
public interface UserService {
    @RequestMapping("/getReceiveAddressByMemberId")
    public List<UmsMemberReceiveAddress> getReceiveAddressByMemberId(String memberId);


    @RequestMapping("/getAllUser")
    @ResponseBody
    public List<UmsMember> getAllUser();

    @RequestMapping("/index")
    @ResponseBody
    public String index();



}
