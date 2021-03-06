package cn.codewoo.entity;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * 视频集实体类
 * @author kehong
 */
public class Episode implements Serializable {
    @ApiModelProperty("id")
    private Integer id;
    @ApiModelProperty("集标题")
    private String title;
    @ApiModelProperty("第几集")
    private Integer num;
    @ApiModelProperty("时长，单位-分")
    private String duration;
    @ApiModelProperty("封面图")
    private String coverImg;
    @ApiModelProperty("所属视频id")
    private Integer videoId;
    @ApiModelProperty("集概述")
    private String summary;
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("所属章节id")
    private Integer chapterId;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration == null ? null : duration.trim();
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg == null ? null : coverImg.trim();
    }

    public Integer getVideoId() {
        return videoId;
    }

    public void setVideoId(Integer videoId) {
        this.videoId = videoId;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getChapterId() {
        return chapterId;
    }

    public void setChapterId(Integer chapterId) {
        this.chapterId = chapterId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", title=").append(title);
        sb.append(", num=").append(num);
        sb.append(", duration=").append(duration);
        sb.append(", coverImg=").append(coverImg);
        sb.append(", videoId=").append(videoId);
        sb.append(", summary=").append(summary);
        sb.append(", createTime=").append(createTime);
        sb.append(", chapterId=").append(chapterId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}