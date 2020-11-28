package cn.codewoo.vo.req;

import io.swagger.annotations.ApiModelProperty;

/**
 * @ClassName VideoPageReqVO
 * @Description 视频分页查询请求VO
 * @Author kehong
 * @Date 2020/11/24 2:37 下午
 * @Version 1.0
 **/
public class VideoPageReqVO {
    @ApiModelProperty("查询页数")
    private Integer page;
    @ApiModelProperty("每页显示条数")
    private Integer size;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "VideoPageReqVO{" +
                "page=" + page +
                ", size=" + size +
                '}';
    }
}
