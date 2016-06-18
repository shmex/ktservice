package com.keeperteacher.ktservice.drill.api;

import com.keeperteacher.ktservice.drill.model.Drill;
import com.keeperteacher.ktservice.drill.service.DrillService;
import com.keeperteacher.ktservice.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/drills", produces = "application/json")
public class DrillApi {

    @Autowired private DrillService drillService;

    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<Drill> listDrills() {
        return drillService.list();
    }

    @RequestMapping(path = "", method = RequestMethod.POST)
    public Drill create(@Valid @RequestBody Drill drill) {
        return drillService.create(drill);
    }

    @RequestMapping(path = "{drillId}", method = RequestMethod.GET)
    public Drill read(@PathVariable String drillId) throws ResourceNotFoundException {
        return drillService.read(drillId);
    }

    @RequestMapping(path = "{drillId}", method = RequestMethod.PUT)
    public Drill update(@PathVariable String drillId, @Valid @RequestBody Drill drill) {
        return drillService.update(drillId, drill);
    }

    @RequestMapping(path = "{drillId}", method = RequestMethod.DELETE)
    public Drill delete(@PathVariable String drillId) {
        return drillService.delete(drillId);
    }
}
