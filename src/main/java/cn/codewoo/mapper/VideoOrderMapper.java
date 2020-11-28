package cn.codewoo.mapper;

import cn.codewoo.entity.Video;
import cn.codewoo.entity.VideoOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideoOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(VideoOrder record);

    int insertSelective(VideoOrder record);

    VideoOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(VideoOrder record);

    int updateByPrimaryKey(VideoOrder record);

    int updateByOutTradeNo(VideoOrder videoOrder);

    VideoOrder selectOrderByOutTradeNo(String no);

    List<VideoOrder> selectOrderByUserId(int userId);

    VideoOrder selectOrderByUserIdAndVideoId(@Param(value = "userId") int userId, @Param(value = "videoId") int videoId);

    VideoOrder selectOrderStateByUserIdAndVideoId(@Param(value = "userId") int userId, @Param(value = "videoId") int videoId);

}