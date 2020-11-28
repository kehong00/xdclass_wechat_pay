package cn.codewoo.service;

import cn.codewoo.entity.Video;
import cn.codewoo.vo.req.VideoPageReqVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 视频服务接口
 * @author kehong
 */
public interface IVideoService {
    /**
     * 查询所有视频
     * @return
     */
    List<Video> selectAllVideo();

    /**
     * 视频分页查询
     * @return
     * @param vo
     */
    PageInfo<Video> selectVideoPage(VideoPageReqVO vo);

    /**
     * 查询视频详细信息
     * @param videoId
     * @return
     */
    Video selectVideoDetails(int videoId);

    /**
     * 根据视频id查询视频信息
     * @param videoId
     * @return
     */
    Video selectVideoById(int videoId);
}
