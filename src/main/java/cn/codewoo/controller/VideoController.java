package cn.codewoo.controller;

import cn.codewoo.entity.Video;
import cn.codewoo.service.IVideoService;
import cn.codewoo.utils.DataResult;
import cn.codewoo.vo.req.VideoPageReqVO;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName VideoController
 * @Description TODO
 * @Author kehong
 * @Date 2020/11/24 2:49 下午
 * @Version 1.0
 **/
@RestController
@RequestMapping("/api")
public class VideoController {
    @Autowired
    private IVideoService videoService;

    @GetMapping("/pub/video_list")
    @ApiOperation("获取全部视频列表")
    public DataResult getAllVideo(){
        List<Video> videos = videoService.selectAllVideo();
        return DataResult.success(videos);
    }

    @PostMapping("/pub/video_list")
    @ApiOperation("视频分页查询")
    public DataResult getVideoPage(@RequestBody VideoPageReqVO vo){
        PageInfo<Video> pageInfo = videoService.selectVideoPage(vo);
        return DataResult.success(pageInfo);
    }

    @GetMapping("/pub/video_details")
    @ApiOperation("根据视频id查询视频详情，包含章节信息")
    public DataResult getVideoDetails(@RequestParam(name = "video_id",required = true) Integer videoId){
        return DataResult.success(videoService.selectVideoDetails(videoId));
    }


    @GetMapping("/pub/video_base")
    @ApiOperation("获取视频基本信息")
    public DataResult getVideoBaseInfo(@RequestParam(name = "video_id", required = true) Integer videoId){
        return DataResult.success(videoService.selectVideoById(videoId));
    }



}
