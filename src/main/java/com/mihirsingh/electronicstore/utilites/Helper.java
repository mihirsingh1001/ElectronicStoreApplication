package com.mihirsingh.electronicstore.utilites;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import com.mihirsingh.electronicstore.dtos.PageableResponse;
import com.mihirsingh.electronicstore.dtos.UserDto;
import com.mihirsingh.electronicstore.entities.User;

public class Helper {

    public static <U,V> PageableResponse<V> getPageableResponse(Page<U> page, Class<V> type){
        List<U> users = page.getContent();
        List<V> userDtolist = users.stream().map(object -> new ModelMapper().map(object,type)).collect(Collectors.toList());

        PageableResponse<V> response = new PageableResponse<>();
        response.setContent(userDtolist);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setLastPage(page.isLast());
        return response;     
    }
}
