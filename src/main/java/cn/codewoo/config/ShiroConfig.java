package cn.codewoo.config;

import cn.codewoo.shiro.CustomHashedCredentialsMather;
import cn.codewoo.shiro.CustomRealm;
import cn.codewoo.shiro.CustomTokenAccessFilter;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ClassName ShiroConfig
 * @Description shiro配置类
 * @Author kehong
 * @Date 2020/11/14 1:48
 * @Version 1.0
 **/
@Configuration
public class ShiroConfig {
    @Bean
    public CustomHashedCredentialsMather credentialsMather(){
        return new CustomHashedCredentialsMather();
    }

    @Bean
    @Autowired
    public CustomRealm realm(CustomHashedCredentialsMather mather){
        CustomRealm customRealm = new CustomRealm();
        customRealm.setCredentialsMatcher(mather);
        return customRealm;
    }

    @Bean
    @Autowired
    public SecurityManager securityManager(CustomRealm realm){
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(realm);
        return manager;
    }

    @Bean
    @Autowired
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager);
        Map<String, Filter> filterMap = new LinkedHashMap<>();
        filterMap.put("token", new CustomTokenAccessFilter());
        bean.setFilters(filterMap);
        Map<String,String> filterChain = new LinkedHashMap<>();
        filterChain.put("/swagger-ui.html","anon");
        filterChain.put("/webjars/**","anon");
        filterChain.put("/swagger-resources/**","anon");
        filterChain.put("/v2/**","anon");
        filterChain.put("/swagger-ui.html/**","anon");
        filterChain.put("/api/put/**","anon");
        filterChain.put("/**","anon");
        filterChain.put("/api/authc/**","token");
        bean.setFilterChainDefinitionMap(filterChain);
        return bean;
    }

}
