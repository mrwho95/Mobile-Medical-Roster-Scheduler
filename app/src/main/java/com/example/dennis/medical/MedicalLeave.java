package com.example.dennis.medical;

import java.security.cert.Certificate;

public class MedicalLeave {

    private String leavestartdate, leaveenddate, leavereaseon;
    private String clinician_cover, leavetype, leaveduration;
    private String Total_Leave, Public_Holiday, Annual_Leave, Medical_Certificate;

    public MedicalLeave(){

    }



    public MedicalLeave(String leavestartdate, String leaveenddate, String leavereaseon,
                        String clinician_cover, String leavetype, String leaveduration,
                        String Total_Leave, String Public_Holiday, String Annual_Leave, String Medical_Certificate) {
        this.leavestartdate = leavestartdate;
        this.leaveenddate = leaveenddate;
        this.leavereaseon = leavereaseon;
        this.clinician_cover = clinician_cover;
        this.leaveduration = leaveduration;
        this.leavetype = leavetype;
    }

    public String getLeavestartdate() {
        return leavestartdate;
    }

    public String getLeaveenddate() {
        return leaveenddate;
    }

    public String getLeavereaseon() {
        return leavereaseon;
    }

    public String getClinician_cover() {
        return clinician_cover;
    }

    public String getLeavetype() {
        return leavetype;
    }

    public String getLeaveduration() {
        return leaveduration;
    }

    public String getTotal_Leave() {
        return Total_Leave;
    }

    public String getPublic_Holiday() {
        return Public_Holiday;
    }

    public String getAnnual_Leave() {
        return Annual_Leave;
    }

    public String getMedical_Certificate() {
        return Medical_Certificate;
    }
}
