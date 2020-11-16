package com.quan.util;

import com.quan.constant.Constant;
import com.quan.entity.PageData;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;

/**
 * @author: xiexinquan520@163.com
 * User: XieXinQuan
 * DATE:2020/5/3
 */
public class PageUtil {
    public static PageData responseData(org.springframework.data.domain.Page data){
        return new PageData(data.getNumber() + 1, data.getSize(), data.getContent(),
                data.getTotalPages(), data.getTotalElements());
    }
    public static Pageable getSimplePageable(Integer pageIndex, Integer pageCount){

        return getSimplePageable(pageIndex, pageCount, Sort.unsorted());
    }
    public static Pageable getSimplePageable(Integer pageIndex, Integer pageCount, Sort sort){
        pageIndex = Optional.ofNullable(pageIndex).orElse(Constant.pageInitIndex);
        pageCount = Optional.ofNullable(pageCount).orElse(Constant.pageInitCount);

        return PageRequest.of(pageIndex - 1, pageCount, sort);
    }
}
