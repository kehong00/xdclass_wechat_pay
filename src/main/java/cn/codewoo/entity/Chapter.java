package cn.codewoo.entity;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * 章节实体类
 * @author kehong
 */
public class Chapter implements Serializable {
    @ApiModelProperty("章节id")
    private Integer id;
    @ApiModelProperty("视频id")
    private Integer videoId;
    @ApiModelProperty("视频标题")
    private String title;
    @ApiModelProperty("章节顺序")
    private Integer ordered;
    @ApiModelProperty("创建时间")
    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVideoId() {
        return videoId;
    }

    public void setVideoId(Integer videoId) {
        this.videoId = videoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getOrdered() {
        return ordered;
    }

    public void setOrdered(Integer ordered) {
        this.ordered = ordered;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", videoId=").append(videoId);
        sb.append(", title=").append(title);
        sb.append(", ordered=").append(ordered);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Chapter chapter = (Chapter) o;
        return Objects.equals(id, chapter.id) &&
                Objects.equals(videoId, chapter.videoId) &&
                Objects.equals(title, chapter.title) &&
                Objects.equals(ordered, chapter.ordered) &&
                Objects.equals(createTime, chapter.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, videoId, title, ordered, createTime);
    }
}