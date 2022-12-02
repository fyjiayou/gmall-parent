package model.order;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import model.activity.ActivityRule;

import java.io.Serializable;
import java.util.List;

@Data
public class OrderDetailVo implements Serializable {

   private static final long serialVersionUID = 1L;

   // 对应一组规则的订单明细
   @ApiModelProperty(value = "订单明细")
   private List<OrderDetail> orderDetailList;

   @ApiModelProperty(value = "活动规则")
   private ActivityRule activityRule;

}
