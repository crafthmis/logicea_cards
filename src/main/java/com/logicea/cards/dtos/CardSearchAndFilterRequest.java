package com.logicea.cards.dtos;

import lombok.*;

import java.util.Date;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CardSearchAndFilterRequest extends PaginationRequest {

    // this is the lookup text for search
    private String searchText;
    private Date startDate;
    private Date endDate;

}
