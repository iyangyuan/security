package org.yangyuan.security.realm.remote;

import org.apache.commons.lang3.StringUtils;
import org.yangyuan.security.bean.User;
import org.yangyuan.security.config.ResourceManager;
import org.yangyuan.security.core.RemoteToken;
import org.yangyuan.security.core.common.SecurityToken;
import org.yangyuan.security.exception.AuthRemoteFailException;
import org.yangyuan.security.http.client.HttpClient;
import org.yangyuan.security.http.response.SimpleResponse;
import org.yangyuan.security.realm.bean.UserAdaptor;
import org.yangyuan.security.realm.common.AbstractRealm;
import org.yangyuan.security.realm.common.Realm;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 第三方数据源实现
 * @author yangyuan
 * @date 2018年3月15日
 */
public class RemoteRealm extends AbstractRealm{
    
    /**
     * 第三方数据源具体实现列表
     * 
     * 1 qq， 2 微信， 3 微博
     * 
     */
    private static final Realm[] REMOTE_REALMS = new Realm[]{
            null,
            new QQRemoteRealm(),
            new WxRemoteRealm(),
            new WbRemoteRealm()
    };
    
    /**
     * qq平台授权登陆
     * @author yangyuan
     * @date 2018年3月15日
     */
    private static class QQRemoteRealm extends AbstractRealm{
        
        @Override
        public User getUser(SecurityToken token) {
            RemoteToken remoteToken = (RemoteToken) token;
            
            try {
                /**
                 * 获取openid
                 */
                String openidApi = "https://graph.qq.com/oauth2.0/me?access_token=" + remoteToken.getAccessToken() + "&unionid=1";
                SimpleResponse response = HttpClient.getClient().get(openidApi);
                if(response.getCode() != 200){
                    throw new AuthRemoteFailException("无法获取openid，授权失败");
                }
                String body = response.getStringBody();
                if(StringUtils.isBlank(body)){
                    throw new AuthRemoteFailException("无法获取openid，授权失败");
                }
                body = body.replaceAll("^\\s*callback\\s*\\(\\s*", "");
                body = body.replaceAll("\\s*\\)\\s*;\\s*$", "");
                JSONObject bodyJson = JSON.parseObject(body);
                if(bodyJson.containsKey("error")){
                    throw new AuthRemoteFailException(bodyJson.getString("error_description") + "，授权失败");
                }
                String unionid = bodyJson.getString("unionid");
                String openid = bodyJson.getString("openid");
                String appid = bodyJson.getString("client_id");
                
                /**
                 * 获取用户信息
                 */
                String infoApi = "https://graph.qq.com/user/get_user_info?access_token=" 
                                    + remoteToken.getAccessToken() + "&oauth_consumer_key=" 
                                    + appid + "&openid=" + openid;
                response = HttpClient.getClient().get(infoApi);
                if(response.getCode() != 200){
                    throw new AuthRemoteFailException("无法获取用户信息，授权失败");
                }
                body = response.getStringBody();
                if(StringUtils.isBlank(body)){
                    throw new AuthRemoteFailException("无法获取用户信息，授权失败");
                }
                bodyJson = JSON.parseObject(body);
                if(bodyJson.getIntValue("ret") != 0){
                    throw new AuthRemoteFailException("无法获取用户信息，授权失败");
                }
                UserAdaptor userAdaptor = new UserAdaptor();
                userAdaptor.setOpenid(unionid);
                userAdaptor.setNickname(bodyJson.getString("nickname"));
                if(StringUtils.isNoneBlank(bodyJson.getString("figureurl_qq_1"))){
                    userAdaptor.setPortrait(bodyJson.getString("figureurl_qq_1"));
                }
                if(StringUtils.isNoneBlank(bodyJson.getString("figureurl_qq_2"))){
                    userAdaptor.setPortrait(bodyJson.getString("figureurl_qq_2"));
                }
                
                /**
                 * 访问适配器
                 */
                userAdaptor = ResourceManager.dao().getRemoteRealmAdaptor().selectByUser(userAdaptor);
                
                return getUser(userAdaptor.getUnionid(), userAdaptor.getRoles());
            } catch (Exception e) {
                throw new AuthRemoteFailException(e.getMessage() + "，授权失败");
            }
        }
    }
    
    /**
     * 微信平台授权登陆
     * @author yangyuan
     * @date 2018年3月15日
     */
    private static class WxRemoteRealm extends AbstractRealm {
        
        @Override
        public User getUser(SecurityToken token) {
            RemoteToken remoteToken = (RemoteToken) token;
            
            try {
                String infoApi = "https://api.weixin.qq.com/sns/userinfo?access_token=" 
                                    + remoteToken.getAccessToken() + "&openid=" 
                                    + remoteToken.getOpenid();
                SimpleResponse response = HttpClient.getClient().get(infoApi);
                if(response.getCode() != 200){
                    throw new AuthRemoteFailException("无法获取用户信息，授权失败");
                }
                String body = response.getStringBody();
                if(StringUtils.isBlank(body)){
                    throw new AuthRemoteFailException("无法获取用户信息，授权失败");
                }
                JSONObject bodyJson = JSON.parseObject(body);
                if(bodyJson.containsKey("errcode")){
                    throw new AuthRemoteFailException(bodyJson.getString("errmsg") + "，授权失败");
                }
                UserAdaptor userAdaptor = new UserAdaptor();
                userAdaptor.setOpenid(bodyJson.getString("unionid"));
                userAdaptor.setNickname(bodyJson.getString("nickname"));
                userAdaptor.setPortrait(bodyJson.getString("headimgurl").replace("\\", ""));
                
                /**
                 * 访问适配器
                 */
                userAdaptor = ResourceManager.dao().getRemoteRealmAdaptor().selectByUser(userAdaptor);
                
                return getUser(userAdaptor.getUnionid(), userAdaptor.getRoles());
            } catch (Exception e) {
                throw new AuthRemoteFailException(e.getMessage() + "，授权失败");
            }
        }
        
    }
    
    /**
     * 微博平台授权登陆
     * @author yangyuan
     * @date 2018年3月15日
     */
    private static class WbRemoteRealm extends AbstractRealm {

        @Override
        public User getUser(SecurityToken token) {
            RemoteToken remoteToken = (RemoteToken) token;
            
            try {
                String infoApi = "https://api.weibo.com/2/users/show.json?access_token=" + remoteToken.getAccessToken();
                SimpleResponse response = HttpClient.getClient().get(infoApi);
                if(response.getCode() != 200){
                    throw new AuthRemoteFailException("无法获取用户信息，授权失败");
                }
                String body = response.getStringBody();
                if(StringUtils.isBlank(body)){
                    throw new AuthRemoteFailException("无法获取用户信息，授权失败");
                }
                JSONObject bodyJson = JSON.parseObject(body);
                if(bodyJson.containsKey("error_code")){
                    throw new AuthRemoteFailException(bodyJson.getString("error") + "，授权失败");
                }
                UserAdaptor userAdaptor = new UserAdaptor();
                userAdaptor.setOpenid(String.valueOf(bodyJson.getLongValue("id")));
                userAdaptor.setNickname(bodyJson.getString("screen_name"));
                userAdaptor.setPortrait(bodyJson.getString("avatar_large"));
                
                /**
                 * 访问适配器
                 */
                userAdaptor = ResourceManager.dao().getRemoteRealmAdaptor().selectByUser(userAdaptor);
                
                return getUser(userAdaptor.getUnionid(), userAdaptor.getRoles());
            } catch (Exception e) {
                throw new AuthRemoteFailException(e.getMessage() + "，授权失败");
            }
        }
        
    }
    
    @Override
    public User getUser(SecurityToken token) {
        RemoteToken remoteToken = (RemoteToken) token;
        
        return REMOTE_REALMS[remoteToken.getPlanform()].getUser(token);
    }
    
}
