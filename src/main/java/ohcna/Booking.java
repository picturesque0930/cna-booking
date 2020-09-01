package ohcna;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;

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

        BookingCreated bookingCreated = new BookingCreated(); // 이벤트 인스턴스 생성
        BeanUtils.copyProperties(this, bookingCreated); // 속성값 할당
        bookingCreated.publishAfterCommit(); // Kafka 메시지 publish

    }

    @PostUpdate
    public void onPostUpdate(){

        BookingChanged bookingChanged = new BookingChanged(); // 이벤트 인스턴스 생성
        BeanUtils.copyProperties(this, bookingChanged); // 속성값 할당
        bookingChanged.publishAfterCommit(); // Kafka 메시지 publish

    }

    @PostRemove
    public void onPostRemove(){

        BookingCancelled bookingCancelled = new BookingCancelled(); // 이벤트 인스턴스 생성
        BeanUtils.copyProperties(this, bookingCancelled); // 속성값 할당
        bookingCancelled.publishAfterCommit(); // Kafka 메시지 publish

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
