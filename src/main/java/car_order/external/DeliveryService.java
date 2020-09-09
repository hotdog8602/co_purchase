
package car_order.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

//@FeignClient(name="Delivery", url="http://Delivery:8080")
@FeignClient(name="Delivery", url="${api.url.delivery}")
public interface DeliveryService {

    @RequestMapping(method= RequestMethod.GET, path="/deliveries")
    public void ship(@RequestBody Delivery delivery);

}