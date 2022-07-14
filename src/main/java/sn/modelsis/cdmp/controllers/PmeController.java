package sn.modelsis.cdmp.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.modelsis.cdmp.entities.Pme;
import sn.modelsis.cdmp.services.PmeService;

import java.util.List;

@RestController
@RequestMapping("/pme")
public class PmeController {
    private PmeService pmeService;
    public PmeController(PmeService pmeService){
        this.pmeService = pmeService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Pme>> getAllPme () {
        List<Pme> PmeList = pmeService.findAllPme();
        return new ResponseEntity<>(PmeList, HttpStatus.OK);
    }

   /* @GetMapping("/find/{id}")
    public ResponseEntity<Pme> getPmeById (@PathVariable("id") Long id) {
        Pme pme = pmeService.findPmeById(id);
        return new ResponseEntity<>(pme, HttpStatus.OK);
    }*/

    @PostMapping("/add")
    public ResponseEntity<Pme> addPme(@RequestBody Pme pme) {
        Pme newPme = pmeService.addPme(pme);
        return new ResponseEntity<>(newPme, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Pme> updatePme(@RequestBody Pme pme,@PathVariable("id") Long pmeId) {
        Pme updatePme = pmeService.updatePme(pme, pmeId);
        return new ResponseEntity<>(updatePme, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePme(@PathVariable("id") Long pmeId) {
        pmeService.deletePme(pmeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
