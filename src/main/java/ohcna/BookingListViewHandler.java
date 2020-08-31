package ohcna;

import ohcna.config.kafka.KafkaProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class BookingListViewHandler {


    @Autowired
    private BookingListRepository bookingListRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenBookingCreated_then_CREATE_1 (@Payload BookingCreated bookingCreated) {
        try {
            if (bookingCreated.isMe()) {
                // view 객체 생성
                BookingList bookingList = new BookingList();
                // view 객체에 이벤트의 Value 를 set 함
                bookingList.setBookingId(bookingCreated.getId());
                bookingList.setRoomId(bookingCreated.getRoomId());
                bookingList.setUseStartDtm(bookingCreated.getUseStartDtm());
                bookingList.setUseEndDtm(bookingCreated.getUseEndDtm());
                bookingList.setBookingUserId(bookingCreated.getBookingUserId());
                // view 레파지 토리에 save
                bookingListRepository.save(bookingList);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whenConfirmRequested_then_UPDATE_1(@Payload ConfirmRequested confirmRequested) {
        try {
            if (confirmRequested.isMe()) {
                // view 객체 조회
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}