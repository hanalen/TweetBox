package com.tweetbox.progress.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Progress {
    @Id
    @GeneratedValue
    public int id;

    @Column(nullable = true)
    public Long user_id;
    @Column(nullable = true)
    public ProgressStatus progressStatus;
}
