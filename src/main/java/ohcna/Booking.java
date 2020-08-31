package ohcna;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;

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
        BookingCreated bookingCreated = new BookingCreated();
        BeanUtils.copyProperties(this, bookingCreated);
        bookingCreated.publishAfterCommit();


    }

    @PostUpdate
    public void onPostUpdate(){
        BookingChanged bookingChanged = new BookingChanged();
        BeanUtils.copyProperties(this, bookingChanged);
        bookingChanged.publishAfterCommit();


    }

    @PreRemove
    public void onPreRemove(){
        BookingCancelled bookingCancelled = new BookingCancelled();
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




}
