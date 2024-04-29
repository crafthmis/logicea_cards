package com.logicea.cards.dtos;

import lombok.*;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PaginationRequest {

    private Integer pageNo;
    private Integer pageSize;
    private String sortBy;
    private String direction;

}
