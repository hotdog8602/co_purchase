package car_order;

import javax.persistence.*;

import car_order.external.Delivery;
import org.springframework.beans.BeanUtils;
import java.util.List;

@Entity
@Table(name="Purchase_table")
public class Purchase {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Long orderId;
    private Long qty;
    private String status;

    @PostPersist
    public void onPostPersist(){
        Bought bought = new Bought();
        BeanUtils.copyProperties(this, bought);

        bought.publishAfterCommit();

        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.


        car_order.external.Delivery delivery =  new car_order.external.Delivery();


        //동기 호출의 중요한 방식.
        delivery.setOrderId(this.getOrderId());
        delivery.setStatus("shipped");
        delivery.setPurchaseId(this.getId());


        // mappings goes here
        PurchaseApplication.applicationContext.getBean(car_order.external.DeliveryService.class)
            .ship(delivery);


    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }




}
