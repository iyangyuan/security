package org.yangyuan.security.core;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.yangyuan.security.config.ResourceManager;
import org.yangyuan.security.core.common.Session;
import org.yangyuan.security.core.common.Subject;

/**
 * 默认安全认证主题实现(不可变对象)
 * @author yangyuan
 * @date 2017年4月26日
 */
public final class DefaultSubject implements Subject<String, Object>{
    
    private final Session<String, Object> session;
    private final String principal;
    private final boolean valid;
    
    /**
     * 指定principal的构造方法
     * <p>valid false</p>
     * <p>session new</p>
     * @param principal 唯一标识
     */
    private DefaultSubject(String principal) {
        this.principal = principal;
        this.valid = false;
        this.session = new DefaultSession();
    }
    
    /**
     * 指定principal、session的构造方法
     * <p>valid true</p>
     * @param principal 唯一标识
     * @param session 会话，传null自动创建
     */
    private DefaultSubject(String principal, Session<String, Object> session) {
        this.principal = principal;
        this.valid = true;
        if(session == null){
            session = new DefaultSession();
        }
        this .session = session;
    }
    
    /**
     * 完全自定义的构造方法
     * @param principal 唯一标识
     * @param valid 是否有效
     * @param session 会话，传null自动创建
     */
    private DefaultSubject(String principal, boolean valid, Session<String, Object> session) {
        this.principal = principal;
        this.valid = valid;
        if(session == null){
            session = new DefaultSession();
        }
        this .session = session;
    }
    
    @Override
    public String getPrincipal() {
        return principal;
    }

    @Override
    public boolean isValid() {
        return valid;
    }
    
    @Override
    public Session<String, Object> getSession() {
        return session;
    }
    
    /**
     * 从请求对象中创建subject
     * @param request 请求对象
     * @return 如果请求中带有principal，则利用客户端指定的principal创建subject；如果找不到principal，则返回null
     */
    public static DefaultSubject of(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if(cookies == null){
            return null;
        }
        
        String clientPrincipal = null;
        for(Cookie cookie : cookies){
            if(ResourceManager.cookie().getName().equals(cookie.getName())){
                clientPrincipal = cookie.getValue();
                break;
            }
        }
        if(StringUtils.isBlank(clientPrincipal)){
            return null;
        }
        
        return new DefaultSubject(clientPrincipal);
    }
    
    /**
     * 标记subject状态为有效
     * @param subject
     * @return 如果传入的subject本身就是有效的，直接返回传入的subject；否则，会新建一个subject返回，新subject的principal、session与传入的subject一致
     */
    public static DefaultSubject valid(DefaultSubject subject){
        if(subject.isValid()){
            return subject;
        }
        
        return new DefaultSubject(subject.getPrincipal(), subject.getSession());
    }
    
    /**
     * 获取subject实例
     * @param principal 唯一标识
     * @param valid 是否有效
     * @param session 会话，传null自动创建
     * @return
     */
    public static DefaultSubject getInstance(String principal, boolean valid, Session<String, Object> session){
        return new DefaultSubject(principal, valid, session);
    }
    
}
