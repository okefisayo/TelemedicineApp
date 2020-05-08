package xyz.cglzwz.chatroom.service;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.session.Session;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import xyz.cglzwz.chatroom.dao.UserMapper;
import xyz.cglzwz.chatroom.domain.SysUser;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * 实现SpringSecurity内的UserDetailsService接口来完成自定义查询用户的逻辑
 *
 * @author chgl16
 * @version 2.0
 */

@Service
public class CustomUserService implements UserDetailsService {
    private static final Log log = LogFactory.getLog(CustomUserService.class);

    @Resource
    private UserMapper userMapper;

    /**
     * 重写 loadUserByUsername 方法获取 userDetails 类型用户
     * SysUser 已经实现 UserDetails
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = userMapper.findByUsername(username);
        if (user != null) {
            log.info("username存在");
            log.info("打印: " + user);
            // 用户权限
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            //用于添加用户的权限。只要把用户权限添加到authorities[本项目固定是用户角色]
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            //just try...fuck me
            ServletRequestAttributes attr = (ServletRequestAttributes)
                    RequestContextHolder.currentRequestAttributes();
            HttpSession session= attr.getRequest().getSession(true);
            session.setAttribute("username", username);
            //
            return new org.springframework.security.core.userdetails.User(user.getUsername(),
                    user.getPassword(), authorities);
        } else {
            throw new UsernameNotFoundException("admin: " + username + " do not exist!");
        }
    }
}
