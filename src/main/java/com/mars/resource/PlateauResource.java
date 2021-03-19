package com.mars.resource;

import com.mars.model.Plateau;
import com.mars.model.PlateauField;
import com.mars.service.PlateauServiceImpl;
import org.springframework.web.bind.annotation.*;

import static com.mars.decoder.PlateauDecoder.decodePlateauCreate;
import static com.mars.decoder.PlateauDecoder.decodePlateauFieldRead;

@RestController
@RequestMapping(path = "/plateau")
public class PlateauResource {
    private final PlateauServiceImpl plateauService;

    public PlateauResource(PlateauServiceImpl plateauService) {
        this.plateauService = plateauService;
    }

    @RequestMapping(value = "/create",
            method = RequestMethod.POST,
            consumes = { "text/plain" }, produces = { "application/json" })
    public Plateau createPlateau(@RequestBody String command) throws Exception {
        Integer[] decoded = decodePlateauCreate(command);
        return plateauService.createPlateau(decoded[0], decoded[1]);
    }

    @RequestMapping(value = "/read",
            method = RequestMethod.GET,
            produces = { "application/json" })
    public Plateau readPlateau() {
        return plateauService.readPlateau();
    }

    @RequestMapping(value = "/print",
            method = RequestMethod.GET,
            produces = { "text/plain" })
    public String printPlateau() {
        return plateauService.printPlateau();
    }

    @RequestMapping(value = "/field/read",
            method = RequestMethod.POST,
            consumes = { "text/plain" },
            produces = { "application/json" })
    public PlateauField readPlateauField(@RequestBody String command) throws Exception {
        Integer[] decoded = decodePlateauFieldRead(command);
        return plateauService.readPlateauField(decoded[0], decoded[1]);
    }
}
