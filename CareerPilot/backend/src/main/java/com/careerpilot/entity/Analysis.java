package com.careerpilot.entity;
import jakarta.persistence.*; import java.time.LocalDateTime;
@Entity @Table(name="analyses")
public class Analysis {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false) private Long userId;
 @Column(columnDefinition="LONGTEXT") private String resumeText;
 @Column(columnDefinition="LONGTEXT") private String result;
 private LocalDateTime createdAt=LocalDateTime.now();
 public Long getId(){return id;} public Long getUserId(){return userId;} public void setUserId(Long v){userId=v;} public String getResumeText(){return resumeText;} public void setResumeText(String v){resumeText=v;} public String getResult(){return result;} public void setResult(String v){result=v;} public LocalDateTime getCreatedAt(){return createdAt;}
}
