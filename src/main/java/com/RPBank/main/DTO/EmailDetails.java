package com.RPBank.main.DTO;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailDetails {

    @NonNull
    private String recipient;

    @NonNull
    private String subject;

    @NonNull
    private String messageBody;

    private String attachment;

    @Override
    public String toString() {
        return "EmailDetails{" +
                "recipient='" + recipient + '\'' +
                ", subject='" + subject + '\'' +
                ", messageBody='" + messageBody + '\'' +
                ", attachment='" + attachment + '\'' +
                '}';
    }
}
