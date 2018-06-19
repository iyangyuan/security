package org.yangyuan.security.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.yangyuan.security.bean.common.RoleAdaptor;

/**
 * 用户角色模型
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
    
    /**
     * 获取角色名称
     * @return 角色名称
     */
    public String getName() {
        return name;
    }
    
    /**
     * 获取角色级别
     * @return 角色级别
     */
    public int getLevel() {
        return level;
    }
    
    /**
     * 获取角色操作标识符
     * @return 角色操作标识符
     */
    public String getComparator() {
        return comparator;
    }
    
    /**
     * 获取角色字符串
     * @return 角色字符串
     */
    public String getRole() {
        StringBuilder builder = new StringBuilder(32);
        
        builder.append(this.getComparator());
        builder.append(this.getName());
        if(this.getLevel() != 0){
            builder.append("{");
            builder.append(this.getLevel());
            builder.append("}");
        }
        
        return new String(builder);
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
     * 添加角色到已有的角色列表
     * <p>详见：{@link Role#addRoles(List, List)}</p>
     * @param roleAdaptors 已有角色列表
     * @param addingRoleAdaptor 新增的角色
     * @return 合并之后的角色列表
     */
    public static <T extends RoleAdaptor> List<T> addRole(List<T> roleAdaptors, T addingRoleAdaptor){
        List<T> addingRoleAdaptors = new ArrayList<T>();
        addingRoleAdaptors.add(addingRoleAdaptor);
        
        return addRoles(roleAdaptors, addingRoleAdaptors);
    }
    
    /**
     * 添加角色列表到已有的角色列表
     * <p>此方法可以处理相同角色不同等级的场景</p>
     * <p>例如roleAdaptors中包含vip{1}，addingRoleAdaptors中包含vip{2}，那么合并之后的roleAdaptors中仅存在一个vip{2}，原来的vip{1}会被覆盖</p>
     * @param roleAdaptors 已有角色列表
     * @param addingRoleAdaptors 新增的角色列表
     * @return 合并之后的角色列表
     */
    public static <T extends RoleAdaptor> List<T> addRoles(List<T> roleAdaptors, List<T> addingRoleAdaptors){
        List<T> mergeRoleAdaptors = new ArrayList<T>();
        Map<String, T> mergeMap = new HashMap<String, T>();
        
        for(T roleAdaptor : roleAdaptors){
            mergeMap.put(Role.simpleRoleName(roleAdaptor.getRole()), roleAdaptor);
        }
        for(T roleAdaptor : addingRoleAdaptors){
            mergeMap.put(Role.simpleRoleName(roleAdaptor.getRole()), roleAdaptor);
        }
        
        Set<Entry<String, T>> entrySet = mergeMap.entrySet();
        for(Entry<String, T> entry : entrySet){
            mergeRoleAdaptors.add(entry.getValue());
        }
        
        return mergeRoleAdaptors;
    }
    
    /**
     * 从已有的角色列表中移除指定的角色
     * <p>详见：{@link Role#removeRoles(List, List)}</p>
     * @param roleAdaptors 已有角色列表
     * @param removingRoleAdaptor 移除的角色
     * @return 合并之后的角色列表
     */
    public static <T extends RoleAdaptor> List<T> removeRole(List<T> roleAdaptors, T removingRoleAdaptor){
        List<T> removingRoleAdaptors = new ArrayList<T>();
        removingRoleAdaptors.add(removingRoleAdaptor);
        
        return removeRoles(roleAdaptors, removingRoleAdaptors);
    }
    
    /**
     * 从已有的角色列表中移除指定的角色列表
     * <p>此方法可以处理相同角色不同等级的场景</p>
     * <p>例如roleAdaptors中包含vip{1}，removingRoleAdaptors中包含vip，那么合并之后的roleAdaptors中不再包含任意等级的vip角色</p>
     * @param roleAdaptors 已有角色列表
     * @param removingRoleAdaptors 移除的角色列表
     * @return 合并之后的角色列表
     */
    public static <T extends RoleAdaptor> List<T> removeRoles(List<T> roleAdaptors, List<T> removingRoleAdaptors){
        List<T> mergeRoleAdaptors = new ArrayList<T>();
        Map<String, T> mergeMap = new HashMap<String, T>();
        
        for(T roleAdaptor : roleAdaptors){
            mergeMap.put(Role.simpleRoleName(roleAdaptor.getRole()), roleAdaptor);
        }
        for(T roleAdaptor : removingRoleAdaptors){
            mergeMap.remove(Role.simpleRoleName(roleAdaptor.getRole()));
        }
        
        Set<Entry<String, T>> entrySet = mergeMap.entrySet();
        for(Entry<String, T> entry : entrySet){
            mergeRoleAdaptors.add(entry.getValue());
        }
        
        return mergeRoleAdaptors;
    }
    
    /**
     * 角色对象列表转换成角色表达式，可以理解为序列化
     * <p><b>example:</b> a, >b{2}, c{3}</p>
     * @param roles 角色列表
     * @return 角色表达式
     */
    public static String getRoles(List<Role> roles){
        StringBuilder permissionBuilder = new StringBuilder(128);
        
        int i = 0;
        for(Role role : roles){
            i++;
            
            permissionBuilder.append(role.getRole());
            
            if(i < roles.size()){
                permissionBuilder.append(",");
            }
        }
        
        return permissionBuilder.toString();
    }
    
    /**
     * 获取简写角色名称
     * <br>
     * <b>Example: </b>vip{1} -> vip
     * @param role 角色名称
     * @return 简写角色名称
     */
    public static String simpleRoleName(String role){
        return parseRole(role).getName();
    }
    
    /**
     * 角色表达式转换成角色对象，可以理解为反序列化
     * @param role 角色表达式
     * @return 角色对象
     */
    public static Role parseRole(String role){
        return parseRoles(role).get(0);
    }
    
    /**
     * 业务角色模型转换成安全框架角色模型列表
     * <p>利用此方法可以快速将业务角色转换成安全框架角色，只需要业务角色模型实现{@link RoleAdaptor}接口即可</p>
     * @param roleAdaptors 业务角色模型列表
     * @return 安全框架角色模型列表
     */
    public static <T extends RoleAdaptor> List<Role> parseRoles(List<T> roleAdaptors){
        StringBuilder builder = new StringBuilder(64);
        
        for(T roleAdaptor : roleAdaptors){
            builder.append(roleAdaptor.getRole());
            builder.append(",");
        }
        
        String roles = new String(builder);
        roles = roles.substring(0, roles.length() - 1);
        
        return Role.parseRoles(roles);
    }
    
    /**
     * 角色表达式转换成角色对象列表，可以理解为反序列化
     * @param roles 角色表达式
     * @return 角色对象列表
     */
    public static List<Role> parseRoles(String roles){
        List<Role> roleList = new ArrayList<Role>();
        
        /**
         * 提取角色
         */
        String[] roleArray = roles.split(",");
        String name;
        int level;
        String comparator;
        String[] units;
        for(String role : roleArray){
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
             * 表达式指定了操作标识符，但未指定级别的非法情况，例如：>a
             */
            if(level == 0 && !comparator.equals(StringUtils.EMPTY)){
                throw new SecurityException("permission express role: " + role + ", you had declare comparator '" + comparator + "', but didn't declare level '{?}', the right express should be like this: >vip{2}");
            }
            
            roleList.add(new Role(name, level, comparator));
        }
        
        return roleList;
    }
    
    @Override
    public String toString() {
        return this.getRole();
    }
    
}
