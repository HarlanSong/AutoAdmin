package cn.songhaiqing.autoadmin.base;

import cn.songhaiqing.autoadmin.constants.ErrorCode;
import cn.songhaiqing.autoadmin.model.SysUserViewModel;
import javax.servlet.http.HttpServletRequest;


public class BaseController {
    private final int SUCCESS = 0;

    public static final String KEY_ADMIN_USER = "user";

    protected BaseResponse success() {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setCode(SUCCESS);
        return baseResponse;
    }

    protected BaseResponse fail(String message) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setCode(ErrorCode.DEFAULT);
        baseResponse.setMsg(message);
        return baseResponse;
    }

    protected BaseResponseList failList(String message) {
        BaseResponseList baseResponseList = new BaseResponseList();
        baseResponseList.setCode(ErrorCode.DEFAULT);
        baseResponseList.setMsg(message);
        return baseResponseList;
    }

    protected BaseResponse fail(int code, String message) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setCode(code);
        baseResponse.setMsg(message);
        return baseResponse;
    }

    protected BaseResponse success(Object data) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setCode(SUCCESS);
        baseResponse.setData(data);
        return baseResponse;
    }

    protected void setAdminUserInfo(HttpServletRequest servletRequest, SysUserViewModel user) {
        servletRequest.getSession().setAttribute(KEY_ADMIN_USER, user);
    }

    protected SysUserViewModel getAdminUser(HttpServletRequest servletRequest) {
        return (SysUserViewModel) servletRequest.getSession().getAttribute(KEY_ADMIN_USER);
    }
}
