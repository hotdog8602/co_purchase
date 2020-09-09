package car_order;

import car_order.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PolicyHandler{
    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    @Autowired
    PurchaseRepository purchaseRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverOrdered_Buy(@Payload Ordered ordered){

        if(ordered.isMe()){
            System.out.println("##### listener ordered : " + ordered.toJson());

            Purchase purchase = new Purchase();
            purchase.setOrderId(ordered.getId());
            purchase.setQty(ordered.getQty());
            purchase.setStatus("Purchased.");

            purchaseRepository.save(purchase);


        }
    }

}
