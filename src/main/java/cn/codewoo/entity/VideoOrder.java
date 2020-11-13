package cn.codewoo.entity;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * 视频订单实体类
 * @author kehong
 */
public class VideoOrder implements Serializable {
    @ApiModelProperty("订单id")
    private Integer id;
    @ApiModelProperty("用户标识")
    private String openid;
    @ApiModelProperty("订单唯一标识")
    private String outTradeNo;
    @ApiModelProperty("订单状态，0未支付，1已支付")
    private Integer state;
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("支付回调时间")
    private Date notifyTime;
    @ApiModelProperty("支付金额")
    private Integer totalFee;
    @ApiModelProperty("微信昵称")
    private String nickname;
    @ApiModelProperty("头像")
    private String headImg;
    @ApiModelProperty("所属视频id")
    private Integer videoId;
    @ApiModelProperty("所属视频标题")
    private String videoTitle;
    @ApiModelProperty("视频封面")
    private String videoImg;
    @ApiModelProperty("所属用户id")
    private Integer userId;
    @ApiModelProperty("用户ip地址")
    private String ip;
    @ApiModelProperty("是否删除，0已删除，1未删除")
    private Integer del;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo == null ? null : outTradeNo.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getNotifyTime() {
        return notifyTime;
    }

    public void setNotifyTime(Date notifyTime) {
        this.notifyTime = notifyTime;
    }

    public Integer getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Integer totalFee) {
        this.totalFee = totalFee;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg == null ? null : headImg.trim();
    }

    public Integer getVideoId() {
        return videoId;
    }

    public void setVideoId(Integer videoId) {
        this.videoId = videoId;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle == null ? null : videoTitle.trim();
    }

    public String getVideoImg() {
        return videoImg;
    }

    public void setVideoImg(String videoImg) {
        this.videoImg = videoImg == null ? null : videoImg.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public Integer getDel() {
        return del;
    }

    public void setDel(Integer del) {
        this.del = del;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", openid=").append(openid);
        sb.append(", outTradeNo=").append(outTradeNo);
        sb.append(", state=").append(state);
        sb.append(", createTime=").append(createTime);
        sb.append(", notifyTime=").append(notifyTime);
        sb.append(", totalFee=").append(totalFee);
        sb.append(", nickname=").append(nickname);
        sb.append(", headImg=").append(headImg);
        sb.append(", videoId=").append(videoId);
        sb.append(", videoTitle=").append(videoTitle);
        sb.append(", videoImg=").append(videoImg);
        sb.append(", userId=").append(userId);
        sb.append(", ip=").append(ip);
        sb.append(", del=").append(del);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}