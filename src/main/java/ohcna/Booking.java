package ohcna;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ohcna.config.kafka.KafkaProcessor;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.MimeTypeUtils;

@Entity
@Table(name="Booking_table")
public class Booking {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Long roomId;
    private String useStartDtm;
    private String useEndDtm;
    private String bookingUserId;

    @PostPersist
    public void onPostPersist(){

        // 이벤트 인스턴스 생성
        BookingCreated bookingCreated = new BookingCreated();

        // 속성값 할당
        BeanUtils.copyProperties(this, bookingCreated);
        bookingCreated.publishAfterCommit();

        // 이벤트를 JSON 문자열로 변경
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;

        try {
            json = objectMapper.writeValueAsString(bookingCreated);
        }
        catch(JsonProcessingException e) {
            throw new RuntimeException("JSON format exception", e);
        }

        // 카프카로 메시지 Publish
        KafkaProcessor processor = BookingApplication.applicationContext.getBean(KafkaProcessor.class);
        MessageChannel outputChannel = processor.outboundTopic();

        outputChannel.send(MessageBuilder
                .withPayload(json)
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                .build());


    }

    @PostUpdate
    public void onPostUpdate(){

        // 이벤트 인스턴스 생성
        BookingChanged bookingChanged = new BookingChanged();

        // 속성값 할당
        BeanUtils.copyProperties(this, bookingChanged);
        bookingChanged.publishAfterCommit();

        // 이벤트를 JSON 문자열로 변경
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;

        try {
            json = objectMapper.writeValueAsString(bookingChanged);
        }
        catch(JsonProcessingException e) {
            throw new RuntimeException("JSON format exception", e);
        }

        // 카프카로 메시지 Publish
        KafkaProcessor processor = BookingApplication.applicationContext.getBean(KafkaProcessor.class);
        MessageChannel outputChannel = processor.outboundTopic();

        outputChannel.send(MessageBuilder
                .withPayload(json)
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                .build());
    }

    @PreRemove
    public void onPreRemove(){

        // 이벤트 인스턴스 생성
        BookingCancelled bookingCancelled = new BookingCancelled();

        // 속성값 할당
        BeanUtils.copyProperties(this, bookingCancelled);
        bookingCancelled.publishAfterCommit();

        // 이벤트를 JSON 문자열로 변경
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;

        try {
            json = objectMapper.writeValueAsString(bookingCancelled);
        }
        catch(JsonProcessingException e) {
            throw new RuntimeException("JSON format exception", e);
        }

        // 카프카로 메시지 Publish
        KafkaProcessor processor = BookingApplication.applicationContext.getBean(KafkaProcessor.class);
        MessageChannel outputChannel = processor.outboundTopic();

        outputChannel.send(MessageBuilder
                .withPayload(json)
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                .build());


    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }
    public String getUseStartDtm() {
        return useStartDtm;
    }

    public void setUseStartDtm(String useStartDtm) {
        this.useStartDtm = useStartDtm;
    }
    public String getUseEndDtm() {
        return useEndDtm;
    }

    public void setUseEndDtm(String useEndDtm) {
        this.useEndDtm = useEndDtm;
    }
    public String getBookingUserId() {
        return bookingUserId;
    }

    public void setBookingUserId(String bookingUserId) {
        this.bookingUserId = bookingUserId;
    }




}
