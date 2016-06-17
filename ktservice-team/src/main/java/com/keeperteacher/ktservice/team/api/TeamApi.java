package com.keeperteacher.ktservice.team.api;

import com.keeperteacher.ktservice.model.KTError;
import com.keeperteacher.ktservice.team.model.Team;
import com.keeperteacher.ktservice.team.service.TeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/teams", produces = "application/json")
public class TeamApi {

    private static final Logger LOG = LoggerFactory.getLogger(TeamApi.class);
    @Autowired private TeamService teamService;

    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<Team> listTeams() {
        return teamService.list();
    }

    @RequestMapping(path = "", method = RequestMethod.POST)
    public Team createTeam(@Valid @RequestBody Team team) {
        return teamService.create(team);
    }

    @RequestMapping(path = "{teamId}", method = RequestMethod.GET)
    public Team readTeam(@PathVariable String teamId) throws KTError {
        LOG.info("teamId: " + teamId);
        return teamService.read(teamId);
    }

    @RequestMapping(path = "{teamId}", method = RequestMethod.PUT)
    public Team updateTeam(@PathVariable String teamId, @Valid @RequestBody Team team) {
        return teamService.update(teamId, team);
    }

    @RequestMapping(path = "{teamId}", method = RequestMethod.DELETE)
    public Team deleteTeam(@PathVariable String teamId) {
        return teamService.delete(teamId);
    }
}
