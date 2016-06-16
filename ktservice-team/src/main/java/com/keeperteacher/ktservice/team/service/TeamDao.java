package com.keeperteacher.ktservice.team.service;

import com.keeperteacher.ktservice.service.BaseDao;
import com.keeperteacher.ktservice.team.model.Team;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class TeamDao extends BaseDao<Team> {
}
