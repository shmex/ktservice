package com.keeperteacher.ktservice.team.service;

import com.keeperteacher.ktservice.core.service.BaseService;
import com.keeperteacher.ktservice.team.model.Team;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TeamService extends BaseService<Team> {
    private static final Logger LOG = LoggerFactory.getLogger(TeamService.class);
}
