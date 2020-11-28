package cn.codewoo.service.impl;

import cn.codewoo.entity.Video;
import cn.codewoo.mapper.VideoMapper;
import cn.codewoo.service.IVideoService;
import cn.codewoo.vo.req.VideoPageReqVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName VideoServiceImpl
 * @Description TODO
 * @Author kehong
 * @Date 2020/11/24 2:36 下午
 * @Version 1.0
 **/
@Service
public class VideoServiceImpl implements IVideoService {
    @Autowired(required = false)
    private VideoMapper videoMapper;
    @Override
    public List<Video> selectAllVideo() {
        return videoMapper.selectAll();
    }

    @Override
    public PageInfo<Video> selectVideoPage(VideoPageReqVO vo) {
        PageHelper.startPage(vo.getPage(),vo.getSize());
        List<Video> videos = videoMapper.selectAll();
        PageInfo<Video> pageInfo = new PageInfo<>(videos);
        return pageInfo;
    }

    @Override
    public Video selectVideoDetails(int videoId) {
        return videoMapper.selectVideoDetailsById(videoId);
    }

    @Override
    public Video selectVideoById(int videoId) {
        return videoMapper.selectByPrimaryKey(videoId);
    }
}
