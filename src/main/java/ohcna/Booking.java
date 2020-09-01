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
    private String status;

    @PostPersist
    public void onPostPersist(){

        // 이벤트 인스턴스 생성
        BookingCreated bookingCreated = new BookingCreated();
        bookingCreated.setStatus("BOOKED");

        // 속성값 할당
        BeanUtils.copyProperties(this, bookingCreated);
        bookingCreated.publishAfterCommit();

    }

    @PostUpdate
    public void onPostUpdate(){

        // 이벤트 인스턴스 생성
        BookingChanged bookingChanged = new BookingChanged();

        // 속성값 할당
        BeanUtils.copyProperties(this, bookingChanged);
        bookingChanged.publishAfterCommit();

    }

    @PreRemove
    public void onPreRemove(){

        // 이벤트 인스턴스 생성
        BookingCancelled bookingCancelled = new BookingCancelled();

        // 속성값 할당
        BeanUtils.copyProperties(this, bookingCancelled);
        bookingCancelled.publishAfterCommit();

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
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
