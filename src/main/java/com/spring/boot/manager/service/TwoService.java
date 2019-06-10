package com.spring.boot.manager.service;


import com.spring.boot.manager.model.TwoParameter;
import com.spring.boot.manager.utils.result.Result;
import org.springframework.data.domain.Pageable;

public interface TwoService {

    public Result seek(TwoParameter twoParameter);

    public Result user(TwoParameter twoParameter, Pageable pageable);

    public Result index(TwoParameter twoParameter, Pageable pageable);

    public Result search(TwoParameter twoParameter, Pageable pageable);

    public Result mine(TwoParameter twoParameter, Pageable pageable);

    public Result searchingUser(TwoParameter twoParameter, Pageable pageable);

    public Result info(TwoParameter twoParameter);

    public Result click(TwoParameter twoParameter);

    public Result buy(TwoParameter twoParameter);

    public Result searchingClear(TwoParameter twoParameter);

    public Result searchingClearId(TwoParameter twoParameter);

    public Result report(TwoParameter twoParameter);

    public Result hidden(TwoParameter twoParameter);

    public Result advert(TwoParameter twoParameter, Pageable pageable);

    public Result advertUser(TwoParameter twoParameter, Pageable pageable);

    public Result delete(TwoParameter twoParameter, String helpids);
}
