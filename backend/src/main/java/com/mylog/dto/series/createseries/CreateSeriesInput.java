package com.mylog.dto.series.createseries;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CreateSeriesInput {
    private String name; //시리즈 이름
}
