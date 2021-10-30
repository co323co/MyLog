package com.mylog.dto.postfile;

import lombok.*;

import javax.persistence.Column;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SelectPostFileMetaOutput {
    private int fileId;
    private String fileOriginName;
    private String fileSaveName;
    private Long fileSize;
    private String fileType;
}
