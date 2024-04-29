package com.logicea.cards.utils;

import com.logicea.cards.dtos.CardSearchAndFilterRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class UtilService {

    public static Pageable getPageable(CardSearchAndFilterRequest searchRequest){
        String sortBy = searchRequest.getSortBy();
        String direction = searchRequest.getDirection();
        Sort.Direction sortDirection;
        if (searchRequest.getPageNo() == null || searchRequest.getPageSize() == null) {
            return null;
        }
        if(searchRequest.getSortBy() == null || searchRequest.getSortBy().isBlank()){
            sortBy = "card_id";
        }
        if(searchRequest.getDirection() == null || searchRequest.getDirection().isBlank()){
            direction = "DESC";
        }
        sortDirection = Sort.Direction.fromString(direction);
        return PageRequest.of(searchRequest.getPageNo(), searchRequest.getPageSize(), sortDirection, sortBy);
    }
}