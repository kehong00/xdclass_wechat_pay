package cn.codewoo.vo.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

/**
 * @ClassName AlipayPayReqVO
 * @Description 支付宝支付，商品信息VO
 * @Author kehong
 * @Date 2020/11/16 7:37 下午
 * @Version 1.0
 **/
public class AlipayPayReqVO {



    @ApiModelProperty("订单编号")
    @JsonProperty("out_trade_no")
    private String out_trade_no;

    @ApiModelProperty("支付金额")
    @JsonProperty("total_amount")
    private Double total_amount;

    @ApiModelProperty("订单标题")
    @JsonProperty("subject")
    private String subject;

    @JsonProperty("body")
    @ApiModelProperty("商品描述")
    private String body;

    @JsonProperty("product_code")
    @ApiModelProperty("销售产品码")
    private String product_code;

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public Double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(Double total_amount) {
        this.total_amount = total_amount;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }

    @Override
    public String toString() {
        return "AlipayPayReqVO{" +
                "out_trade_no='" + out_trade_no + '\'' +
                ", total_amount=" + total_amount +
                ", subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                ", product_code='" + product_code + '\'' +
                '}';
    }
}
