package com.ssm.common.util;

import com.ssm.base.view.Result;
import com.ssm.base.view.TokenProccessor;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class CheckSubmitUtil {
    private static Logger logger = Logger.getLogger(CheckSubmitUtil.class);

    private final static String TOKEN_SIGN = "token";

    /**
     * 后端控制跳转至的页面，并向session里面添加token（session键为"token"）
     * @param request
     * @return
     */
    public static String addSubmitToken(HttpServletRequest request) {
        String token = TokenProccessor.getInstance().makeToken();//创建令牌
        logger.info(new Date()+ " 生成的token："+ token);
        request.getSession().setAttribute(TOKEN_SIGN, token);//在服务器使用session保存token(令牌)
        return  token;
    }

    /**
     * 处理完业务逻辑后，调用该方法清除session的token
     * @param request
     */
    public static void removeSubmitToken(HttpServletRequest request) {
        request.getSession().removeAttribute(TOKEN_SIGN);//移除session中的token
    }

    /**
     * 校样表单是否重复提交
     * @param request
     * @param client_token 客户端传入的token值，用于和服务端的校样
     * @return
     */
    public static Result<?> validitySubmitToken(HttpServletRequest request, String client_token) {
        boolean b = isRepeatSubmit(request, client_token);//判断用户是否是重复提交

        if(b == true){
            return new Result<>(Result.FAIL, "请不要重复提交", null, null);
        }
        return new Result<>(Result.SUCCESS, "有效的提交！", null, null);
    }

    /**
     * 判断客户端提交上来的令牌和服务器端生成的令牌是否一致
     * @param request
     * @param client_token
     * @return
     *         true 用户重复提交了表单
     *         false 用户没有重复提交表单
     */
    private static boolean isRepeatSubmit(HttpServletRequest request, String client_token) {
        String request_client_token = request.getParameter(TOKEN_SIGN);
        if(null != client_token){
            request_client_token = client_token;
        }
        //1、如果用户提交的表单数据中没有token，则用户是重复提交了表单
        if(request_client_token == null){
            return true;
        }
        //取出存储在Session中的token
        String server_token = (String) request.getSession().getAttribute(TOKEN_SIGN);
        //2、如果当前用户的Session中不存在Token(令牌)，则用户是重复提交了表单
        if(server_token==null){
            return true;
        }
        //3、存储在Session中的Token(令牌)与表单提交的Token(令牌)不同，则用户是重复提交了表单
        if(!request_client_token.equals(server_token)){
            return true;
        }

        return false;
    }
}