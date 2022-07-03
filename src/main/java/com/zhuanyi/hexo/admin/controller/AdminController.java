package com.zhuanyi.hexo.admin.controller;

import com.zhuanyi.hexo.admin.obj.dto.SystemSettingDTO;
import com.zhuanyi.hexo.admin.obj.form.LoginForm;
import com.zhuanyi.hexo.admin.obj.form.SystemSettingForm;
import com.zhuanyi.hexo.admin.obj.vo.SystemSettingVO;
import com.zhuanyi.hexo.admin.service.AdminService;
import com.zhuanyi.hexo.admin.obj.vo.AdminInfoVO;
import com.zhuanyi.hexo.auth.annotation.Auth;
import com.zhuanyi.hexo.base.utils.ResultUtils;
import com.zhuanyi.hexo.common.dto.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Resource
    private AdminService defaultAdminService;

    /**
     * 登陆接口
     */
    @PostMapping("/login")
    public Result login(@RequestBody LoginForm dataForm) {
        String token = defaultAdminService.login(dataForm);
        Map<String, String> data = Collections.singletonMap("token", token);
        return new Result().success(data);
    }

    @Auth
    @GetMapping("/info")
    public Result info(String token) {
        AdminInfoVO adminInfoVO = defaultAdminService.getAdminInfo();
        return new Result().success(adminInfoVO);
    }

    @Auth
    @PostMapping("/logout")
    public Result logout() {
        boolean success = defaultAdminService.logout();
        return new Result().success(success ? "success" : "error");
    }

    @Auth
    @GetMapping("/system-setting")
    public Result getSystemSetting() {
        SystemSettingVO systemSettingVO = defaultAdminService.getSystemSetting();
        return new Result().success(systemSettingVO);
    }

    @Auth
    @PostMapping("/update-system-setting")
    public Result updateSystemSetting(@RequestBody SystemSettingForm systemSettingForm) {
        SystemSettingDTO systemSettingDTO = new SystemSettingDTO();
        BeanUtils.copyProperties(systemSettingForm, systemSettingDTO);
        boolean result = defaultAdminService.updateSystemSetting(systemSettingDTO);
        return result ? ResultUtils.success() : ResultUtils.error();
    }

}