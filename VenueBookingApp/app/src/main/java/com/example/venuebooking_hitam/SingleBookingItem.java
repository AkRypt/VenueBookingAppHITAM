package com.example.venuebooking_hitam;

public class SingleBookingItem {

    private String bookerName, bookerRole, bookerNumber, bookerDept, bookerPurpose, bookerDate, bookerTime1, bookerTime2;

    SingleBookingItem(String n, String d, String r, String ph, String p, String da, String t1, String t2) {
        bookerName = n;
        bookerRole = r;
        bookerNumber = ph;
        bookerDept = d;
        bookerPurpose = p;
        bookerDate = da;
        bookerTime1 = t1;
        bookerTime2 = t2;
    }

    public String getBookerName() {
        return bookerName;
    }

    public void setBookerName(String bookerName) {
        this.bookerName = bookerName;
    }

    public String getBookerRole() {
        return bookerRole;
    }

    public void setBookerRole(String bookerRole) {
        this.bookerRole = bookerRole;
    }

    public String getBookerNumber() {
        return bookerNumber;
    }

    public void setBookerNumber(String bookerNumber) {
        this.bookerNumber = bookerNumber;
    }

    public String getBookerDept() {
        return bookerDept;
    }

    public void setBookerDept(String bookerDept) {
        this.bookerDept = bookerDept;
    }

    public String getBookerPurpose() {
        return bookerPurpose;
    }

    public void setBookerPurpose(String bookerPurpose) {
        this.bookerPurpose = bookerPurpose;
    }

    public String getBookerDate() {
        return bookerDate;
    }

    public void setBookerDate(String bookerDate) {
        this.bookerDate = bookerDate;
    }

    public String getBookerTime1() {
        return bookerTime1;
    }

    public void setBookerTime1(String bookerTime1) {
        this.bookerTime1 = bookerTime1;
    }

    public String getBookerTime2() {
        return bookerTime2;
    }

    public void setBookerTime2(String bookerTime2) {
        this.bookerTime2 = bookerTime2;
    }
}
