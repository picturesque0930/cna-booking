package ohcna;

import ohcna.config.kafka.KafkaProcessor;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{

    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

        // 메시지 큐 들어간 메시지 확인
        System.out.println("Listened: " + eventString);
    }


}
