package com.digitopia.caseStudy.entity;

import com.digitopia.caseStudy.status.InvitationStatus;
import jakarta.persistence.*;

import java.util.Date;


@Table(name="INVITATION_TABLE")
@Entity


public class InvitationEntity extends BaseEntity {

    private int userId;
    private String text;
    private InvitationStatus status;

}
