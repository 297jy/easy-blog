package com.zhuanyi.hexo.admin.service;

import com.zhuanyi.hexo.admin.obj.dto.SystemSettingDTO;
import com.zhuanyi.hexo.admin.obj.form.LoginForm;
import com.zhuanyi.hexo.admin.obj.vo.AdminInfoVO;
import com.zhuanyi.hexo.admin.obj.vo.SystemSettingVO;

public interface AdminService {

    String login(LoginForm loginForm);

    AdminInfoVO getAdminInfo();

    boolean logout();

    SystemSettingVO getSystemSetting();

    boolean updateSystemSetting(SystemSettingDTO systemSettingDTO);

}
