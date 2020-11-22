package cn.codewoo.mapper;

import cn.codewoo.entity.User;

public interface UserMapper {
    /**
     * 根据id删除用户
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 保存用户
     * @param record
     * @return
     */
    int insert(User record);

    /**
     * 按条件保存用户
     * @param record
     * @return
     */
    int insertSelective(User record);

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    User selectByPrimaryKey(Integer id);

    /**
     * 更新用户
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(User record);

    /**
     * 更新用户
     * @param record
     * @return
     */
    int updateByPrimaryKey(User record);

    /**
     * 根据openid查询用户
     * @param openid
     * @return
     */
    User selectUserByOpenid(String openid);
}