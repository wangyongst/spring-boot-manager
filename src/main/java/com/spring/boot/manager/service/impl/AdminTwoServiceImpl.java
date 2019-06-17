package com.spring.boot.manager.service.impl;

import com.spring.boot.manager.model.AdminParameter;
import com.spring.boot.manager.repository.*;
import com.spring.boot.manager.service.AdminTwoService;
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


@Service("AdminTwoService")
@SuppressWarnings("All")
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class, readOnly = false)
public class AdminTwoServiceImpl implements AdminTwoService {

    private static final Logger logger = LogManager.getLogger(AdminTwoServiceImpl.class);

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

    @Override
    public Result askList(AdminParameter adminParameter, HttpSession httpSession) {
        return ResultUtil.okWithData(askRepository.findAll());
    }

    @Override
    public Result applyList(AdminParameter adminParameter, HttpSession httpSession) {
        return ResultUtil.okWithData(applyRepository.findAll());
    }

    @Override
    public Result projectList(AdminParameter adminParameter, HttpSession httpSession) {
        return ResultUtil.okWithData(projectRepository.findAll());
    }

    @Override
    public Result project(AdminParameter adminParameter, HttpSession httpSession) {
        return ResultUtil.okWithData(projectRepository.findById(adminParameter.getProjectid()).get());
    }

    @Override
    public Result projectSud(AdminParameter adminParameter, HttpSession httpSession) {
        return null;
    }

    @Override
    public Result resourceList(AdminParameter adminParameter, HttpSession httpSession) {
        return ResultUtil.okWithData(resourceRepository.findAll());
    }

    @Override
    public Result resource(AdminParameter adminParameter, HttpSession httpSession) {
        return ResultUtil.okWithData(resourceRepository.findById(adminParameter.getResourceid()).get());
    }

    @Override
    public Result resourceSud(AdminParameter adminParameter, HttpSession httpSession) {
        return null;
    }

    @Override
    public Result supplierList(AdminParameter adminParameter, HttpSession httpSession) {
        return ResultUtil.okWithData(supplierRepository.findAll());
    }

    @Override
    public Result supplier(AdminParameter adminParameter, HttpSession httpSession) {
        return ResultUtil.okWithData(supplierRepository.findById(adminParameter.getSupplierid()).get());
    }

    @Override
    public Result supplierSud(AdminParameter adminParameter, HttpSession httpSession) {
        return null;
    }

    @Override
    public Result apply(AdminParameter adminParameter, HttpSession httpSession) {
        return ResultUtil.okWithData(applyRepository.findById(adminParameter.getUserid()).get());
    }
}
