package cn.codewoo.shiro;

import cn.codewoo.utils.jwt.JwtUtils;
import io.jsonwebtoken.Claims;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.springframework.util.StringUtils;

/**
 * @ClassName CustomHashedCreatdantilMather
 * @Description TODO
 * @Author kehong
 * @Date 2020/11/13 8:33 下午
 * @Version 1.0
 **/
public class CustomHashedCredentialsMather extends HashedCredentialsMatcher {
    /**
     * 重新校验方法，使用jwt校验
     */

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String jwtToken = (String)token.getPrincipal();
        if (StringUtils.isEmpty(jwtToken)){
            return false;
        }
        //使用jwt工具类校验token是否合法
        Claims claims = JwtUtils.checkJWT(jwtToken);
        if (claims != null){
            return true;
        }
        return false;
    }
}
