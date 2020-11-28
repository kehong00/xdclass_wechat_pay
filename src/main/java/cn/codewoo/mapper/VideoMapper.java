package cn.codewoo.mapper;

import cn.codewoo.entity.Video;

import java.util.List;

public interface VideoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Video record);

    int insertSelective(Video record);

    Video selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Video record);

    int updateByPrimaryKey(Video record);

    List<Video> selectAll();

    /**
     * 根据视频id查询视频详情，包括章集信息
     * @param id
     * @return
     */
    Video selectVideoDetailsById(Integer id);
}