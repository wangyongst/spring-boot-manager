package com.spring.boot.manager.service.impl;

import com.spring.boot.manager.entity.*;
import com.spring.boot.manager.model.AdminParameter;
import com.spring.boot.manager.repository.*;
import com.spring.boot.manager.service.AdminThreeService;
import com.spring.boot.manager.service.AdminTwoService;
import com.spring.boot.manager.utils.db.TimeUtils;
import com.spring.boot.manager.utils.result.Result;
import com.spring.boot.manager.utils.result.ResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;


@Service
@SuppressWarnings("All")
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class, readOnly = false)
public class AdminThreeServiceImpl implements AdminThreeService {

    private static final Logger logger = LogManager.getLogger(AdminThreeServiceImpl.class);

    @Autowired
    private AskRepository askRepository;

    @Autowired
    private ApplyRepository applyRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private MaterialRepository materialRepository;

    @Override
    public Result askList(AdminParameter adminParameter, HttpSession httpSession) {
        return ResultUtil.okWithData(askRepository.findAll());
    }

    @Override
    public Result applyList(AdminParameter adminParameter, HttpSession httpSession) {
        return ResultUtil.okWithData(applyRepository.findAll());
    }

    @Override
    public Result apply(AdminParameter adminParameter, HttpSession httpSession) {
        return ResultUtil.okWithData(applyRepository.findById(adminParameter.getUserid()).get());
    }
}
