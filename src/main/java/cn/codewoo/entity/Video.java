package cn.codewoo.entity;


import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * 视频实体类
 * @author kehong
 */
public class Video implements Serializable {
    @ApiModelProperty("视频id")
    private Integer id;
    @ApiModelProperty("视频标题")
    private String title;
    @ApiModelProperty("视频详情")
    private String summary;
    @ApiModelProperty("视频封面图")
    private String coverImg;
    @ApiModelProperty("观看数量")
    private Integer viewNum;
    @ApiModelProperty("价格-单位分")
    private Integer price;
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("是否上线，1上线，2未上线")
    private Integer online;
    @ApiModelProperty("评分")
    private Double point;
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
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public Integer getViewNum() {
        return viewNum;
    }

    public void setViewNum(Integer viewNum) {
        this.viewNum = viewNum;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getOnline() {
        return online;
    }

    public void setOnline(Integer online) {
        this.online = online;
    }

    public Double getPoint() {
        return point;
    }

    public void setPoint(Double point) {
        this.point = point;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @Override
    public String toString() {
        return "Video{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", coverImg='" + coverImg + '\'' +
                ", viewNum=" + viewNum +
                ", price=" + price +
                ", createTime=" + createTime +
                ", online=" + online +
                ", point=" + point +
                '}';
    }
}