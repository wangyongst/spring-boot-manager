package com.spring.boot.manager.service.impl;

import com.spring.boot.manager.model.AdminParameter;
import com.spring.boot.manager.repository.*;
import com.spring.boot.manager.service.AdminThreeService;
import com.spring.boot.manager.utils.result.Result;
import com.spring.boot.manager.utils.result.ResultUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
    private RequestRepository requestRepository;

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
    public Result requestList(AdminParameter adminParameter, HttpSession httpSession) {
        return ResultUtil.okWithData(requestRepository.findAll());
    }

    @Override
    public Result requestSud(AdminParameter adminParameter, HttpSession httpSession) {
        return null;
    }

    @Override
    public Result request(AdminParameter adminParameter, HttpSession httpSession) {
        return null;
    }
}
