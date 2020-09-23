package com.lzy.springcloud.manage.service;


import com.lzy.springcloud.manage.bean.UmsMember;
import com.lzy.springcloud.manage.bean.UmsMemberReceiveAddress;

import java.util.List;

public interface UserService {

    List<UmsMember> getAllUser();

    List<UmsMemberReceiveAddress> getReceiveAddressByMemberId(String memberId);
}
