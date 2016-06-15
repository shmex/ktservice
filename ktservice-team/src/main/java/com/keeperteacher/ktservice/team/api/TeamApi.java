package com.keeperteacher.ktservice.team.api;

import com.keeperteacher.ktservice.team.model.Team;
import com.keeperteacher.ktservice.team.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/teams")
public class TeamApi {

    @Autowired private TeamService teamService;

    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<Team> listTeams() {
        return teamService.list();
    }

    @RequestMapping(path = "", method = RequestMethod.POST, produces = {"application/json"})
    public Team createTeam(@Valid @RequestBody Team team) {
        System.out.println("got post request: " + team);
        return teamService.create(team);
    }
}
