package com.keeperteacher.ktservice.team.service;

import com.keeperteacher.ktservice.service.BaseService;
import com.keeperteacher.ktservice.team.model.Team;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;

@Service
public class TeamService extends BaseService<Team> {
    private static final Logger LOG = LoggerFactory.getLogger(TeamService.class);
}
