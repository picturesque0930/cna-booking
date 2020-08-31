package ohcna;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="BookingList_table")
public class BookingList {

        @Id
        @GeneratedValue(strategy=GenerationType.AUTO)
        private Long id;
        private Long bookingId;
        private Long roomId;
        private String useStartDtm;
        private String useEndDtm;
        private String confirmUserId;
        private String confirmStatus;
        private String confirmDtm;
        private String confirmUserName;
        private String bookingUserId;
        private Long confirmId;


        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
        public Long getBookingId() {
            return bookingId;
        }

        public void setBookingId(Long bookingId) {
            this.bookingId = bookingId;
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
        public String getConfirmUserId() {
            return confirmUserId;
        }

        public void setConfirmUserId(String confirmUserId) {
            this.confirmUserId = confirmUserId;
        }
        public String getConfirmStatus() {
            return confirmStatus;
        }

        public void setConfirmStatus(String confirmStatus) {
            this.confirmStatus = confirmStatus;
        }
        public String getConfirmDtm() {
            return confirmDtm;
        }

        public void setConfirmDtm(String confirmDtm) {
            this.confirmDtm = confirmDtm;
        }
        public String getConfirmUserName() {
            return confirmUserName;
        }

        public void setConfirmUserName(String confirmUserName) {
            this.confirmUserName = confirmUserName;
        }
        public String getBookingUserId() {
            return bookingUserId;
        }

        public void setBookingUserId(String bookingUserId) {
            this.bookingUserId = bookingUserId;
        }
        public Long getConfirmId() {
            return confirmId;
        }

        public void setConfirmId(Long confirmId) {
            this.confirmId = confirmId;
        }

}
