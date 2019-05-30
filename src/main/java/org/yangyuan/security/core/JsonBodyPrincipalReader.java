package org.yangyuan.security.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.yangyuan.security.config.ResourceManager;
import org.yangyuan.security.core.common.AbstractPrincipalReader;
import org.yangyuan.security.servlet.http.SecurityHttpServletRequestWrapper;
import org.yangyuan.security.util.SecurityHttpUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * 从JSON格式的请求体中读取Principal
 * @Auther: yangyuan
 * @Date: 2019/5/29 17:00
 */
public class JsonBodyPrincipalReader extends AbstractPrincipalReader {
    private String keyName;

    public JsonBodyPrincipalReader(String keyName){
        this.keyName = keyName;
    }

    @Override
    public String read(HttpServletRequest request) {
        try {
            byte[] bytes = null;
            if(request instanceof SecurityHttpServletRequestWrapper){
                bytes = ((SecurityHttpServletRequestWrapper) request).getBody();
            }
            if(bytes == null){
                bytes = SecurityHttpUtil.readBytes(request);
            }

            String charset = request.getCharacterEncoding() == null ? ResourceManager.core().getCharset() : request.getCharacterEncoding();
            String body = new String(bytes, charset);

            try {
                JSONObject json = JSON.parseObject(body);
                String[] keys = keyName.split("\\.");
                int limit = keys.length - 1;
                for(int i = 0; i < keys.length; i++){
                    if(i == limit){
                        return json.getString(keys[i]);
                    }
                    json = json.getJSONObject(keys[i]);
                }
            }catch (Exception e){
                return null;
            }

            return null;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
