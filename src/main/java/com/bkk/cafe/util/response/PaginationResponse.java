/*
 * @Author : Thant Htoo Aung
 * @Date : 09/09/2024
 * @Time : 12:47 PM
 * @Project_Name : cafe
 */
package com.bkk.cafe.util.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PaginationResponse<T> {
    private List<T> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;

}
