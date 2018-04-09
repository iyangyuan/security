package org.yangyuan.security.bean;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * 用户角色(不可变对象)
 * @author yangyuan
 * @date 2017年4月26日
 */
public class Role {
    
    /**
     * 小于标识符
     */
    public static final String LESS_THAN = "<";
    /**
     * 大于标识符
     */
    public static final String MORE_THAN = ">";
    
    /**
     * 角色名称
     */
    private final String name;
    /**
     * 角色级别
     * <br>
     * 级别从<b>1</b>开始，即最低<b>1</b>级
     * <br>
     * <b>0</b>表示无级别，即忽略级别
     */
    private final int level;
    /**
     * 角色操作标识符
     */
    private final String comparator;
    
    /**
     * 禁止外部实例化
     * @param name
     * @param level
     * @param comparator
     */
    private Role(String name, int level, String comparator){
        this.name = name;
        this.level = level;
        this.comparator = comparator;
    }
    
    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }
    
    public String getComparator() {
        return comparator;
    }
    
    /**
     * 角色匹配
     * <p>此方法自动识别角色表达式，可以通过表达式表达的含义匹配两个角色</p>
     * <p>匹配成功并不说明两个角色一定相同，也可能是两个角色满足角色表达式的限定，即泛匹配</p>
     * <p>如果两个角色都包含角色表达式，那么以调用matches方法的角色表达式为准</p>
     * @param role 匹配角色
     * @return
     */
    public boolean matches(Role role){
        
        /**
         * 角色名称不一致，肯定不匹配
         */
        if(!this.name.equals(role.getName())){
            return false;
        }
        
        /**
         * 语法:roles[a]，泛匹配所有级别，也适用于没有级别的场景
         */
        if(StringUtils.isBlank(this.comparator) && this.level == 0){
            return true;
        }
        
        /**
         * 语法:roles[a{1}]，精确匹配级别
         */
        if(StringUtils.isBlank(this.comparator) && this.level == role.getLevel()){
            return true;
        }
        
        /**
         * 语法:roles[<a{3}]，范围匹配，小于指定的级别
         */
        if(this.comparator.equals(LESS_THAN) && this.level > role.getLevel()){
            return true;
        }
        
        /**
         * 语法:roles[>a{3}]，范围匹配，大于指定的级别
         */
        if(this.comparator.equals(MORE_THAN) && this.level < role.getLevel()){
            return true;
        }
        
        /**
         * 不匹配的情况
         */
        return false;
    }
    
    /**
     * 角色对象列表转换成角色表达式，可以理解为序列化
     * <p><b>example:</b> roles[a, >b{2}, c{3}]</p>
     * @param roles 角色列表
     * @return 角色表达式
     */
    public static String toPermission(List<Role> roles){
        StringBuilder permissionBuilder = new StringBuilder(64);
        permissionBuilder.append("roles[");
        
        int i = 0;
        for(Role role : roles){
            i++;
            
            permissionBuilder.append(role.getComparator());
            permissionBuilder.append(role.getName());
            if(role.getLevel() != 0){
                permissionBuilder.append("{");
                permissionBuilder.append(role.getLevel());
                permissionBuilder.append("}");
            }
            if(i < roles.size()){
                permissionBuilder.append(",");
            }
        }
        
        permissionBuilder.append("]");
        
        return permissionBuilder.toString();
    }
    
    /**
     * 获取简写角色名称
     * <br>
     * <b>Example: </b>vip{1} -> vip
     * @param roleName 角色名称
     * @return 简写角色名称
     */
    public static String simpleRoleName(String roleName){
        char c;
        int i;
        for(i = 0; i < roleName.length(); i++){
            c = roleName.charAt(i);
            if(Character.isLetterOrDigit(c)){
               continue; 
            }
            break;
        }
        
        return roleName.substring(0, i);
    }
    
    /**
     * 角色表达式转换成角色对象列表，可以理解为反序列化
     * @param permission 角色表达式
     * @return 角色对象列表
     */
    public static List<Role> parseRole(String permission){
        List<Role> roleList = new ArrayList<Role>();
        /**
         * 格式规范
         */
        permission = permission.replace(" ", "");
        permission = permission.substring(6, permission.length() - 1);
        
        /**
         * 提取角色
         */
        String[] roles = permission.split(",");
        String name;
        int level;
        String comparator;
        String[] units;
        for(String role : roles){
            name = StringUtils.EMPTY;  //重置角色名
            level = 0;  //重置角色级别
            comparator = StringUtils.EMPTY;  //重置角色操作标识符
            
            /**
             * 解析操作标识符
             */
            if(role.startsWith(LESS_THAN)){
                comparator = LESS_THAN;
                role = role.substring(1);
            }
            if(role.startsWith(MORE_THAN)){
                comparator = MORE_THAN;
                role = role.substring(1);
            }
            
            /**
             * 解析角色级别
             */
            units = role.split("\\{");
            if(units.length > 1){
                level = Integer.parseInt(units[1].split("}")[0]);
            }
            
            /**
             * 解析角色名
             */
            name = units[0];
            
            /**
             * 表达式指定了操作标识符，但未指定级别的非法情况，例如：roles[>a]
             */
            if(level == 0 && !comparator.equals(StringUtils.EMPTY)){
                throw new RuntimeException("permission express role: " + role + ", you had declare comparator '" + comparator + "', but didn't declare level '{?}', the right express should be like this: roles[>vip{2}]");
            }
            
            roleList.add(new Role(name, level, comparator));
        }
        
        return roleList;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(32);
        
        if(StringUtils.isNotBlank(this.comparator)){
            builder.append(this.comparator);
        }
        
        builder.append(this.name);
        
        if(this.level != 0){
            builder.append("{");
            builder.append(this.level);
            builder.append("}");
        }
        
        return new String(builder);
    }
    
}
