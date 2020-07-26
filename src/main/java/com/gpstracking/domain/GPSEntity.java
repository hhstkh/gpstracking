package com.gpstracking.domain;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "gps")
public class GPSEntity implements Serializable {


    @Id
    @GeneratedValue
    private Integer id;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "gps_file_name")
    private String gpsFileName;
    @Column(name = "created_date")
    @CreationTimestamp
    private Date createdDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getGpsFileName() {
        return gpsFileName;
    }

    public void setGpsFileName(String gpsFileName) {
        this.gpsFileName = gpsFileName;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
